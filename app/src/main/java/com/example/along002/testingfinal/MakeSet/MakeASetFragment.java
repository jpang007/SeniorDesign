package com.example.along002.testingfinal.MakeSet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.User;
import com.example.along002.testingfinal.Utils.CardRecyclerViewAdapter;
import com.example.along002.testingfinal.Utils.MakeSetRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakeASetFragment extends Fragment {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private ArrayList<String> mTermList = new ArrayList<>();
    private ArrayList<String> mDefList = new ArrayList<>();
    private ArrayList<String> mCardNum = new ArrayList<>();
    private EditText setTitle;
    private EditText setTag;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    private int mSetSize;
    private TextView publishTextView;
    private  MakeSetRecyclerViewAdapter adapter;

//    public MakeASetFragment() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_make_aset,container,false);

        mTermList.add("");
        mDefList.add("");
        mCardNum.add("0");
        publishTextView = view.findViewById(R.id.publishTextView);
        setTitle = view.findViewById(R.id.setTitle);
        setTag = view.findViewById(R.id.set_Tag);
        radioGroup = view.findViewById(R.id.radioGroup);


        final RecyclerView recyclerView = view.findViewById(R.id.recycler_View);
        adapter = new MakeSetRecyclerViewAdapter(getActivity().getApplicationContext(),mTermList,mDefList, mCardNum);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        ImageView addCardImageView = (ImageView) view.findViewById(R.id.addCardImageView);

        addCardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDefList.add("");
                mTermList.add("");
                mCardNum.add(Integer.toString(mCardNum.size()));
                adapter.notifyItemInserted(mCardNum.size() - 1);
            }
        });

        publishTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FlashcardInfo FlashcardInfo = new FlashcardInfo();
                FlashcardInfo.setDefinitionList(adapter.getmDefList());
                FlashcardInfo.setTermList(adapter.getmTermList());
                FlashcardInfo.setSize(Integer.toString(adapter.getmDefList().size()));
                FlashcardInfo.setName(setTitle.getText().toString());

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(selectedId);
                FlashcardInfo.setPrivacy(radioButton.getText().toString()); //todo change this later

                mAuth = FirebaseAuth.getInstance();
                final FirebaseUser currUser = mAuth.getCurrentUser();
                FlashcardInfo.setCreator(currUser.getUid());

                final ArrayList<String> mTagList = new ArrayList<>();
                mTagList.add(setTag.getText().toString());
                FlashcardInfo.setTagList(mTagList); // todo change this

                mDatabase = FirebaseDatabase.getInstance().getReference();
                final String mFlashId = mDatabase.push().getKey(); // get a random key for set
                FlashcardInfo.setId(mFlashId);

                DatabaseReference mPublish = FirebaseDatabase.getInstance().getReference().child("users").child(currUser.getUid());
                mPublish.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        FlashcardInfo.setAuthor(user.getUsername());
                        mDatabase.child("Flashcards").child(mFlashId).setValue(FlashcardInfo);//placing actual set in Flashcard branch

                        DatabaseReference mTagRef = FirebaseDatabase.getInstance().getReference().child("tags");//Placing set reference in tags branch
                        for(int i = 0; i < mTagList.size(); i++){
                            mTagRef.child(mTagList.get(i)).child(mFlashId).child("FlashId").setValue(mFlashId);
                        }
                        mDatabase.child("usersFlash").child(currUser.getUid()).child(mFlashId).child("flashId").setValue(mFlashId);//Placing set reference in user to flash branch
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
                Toast.makeText(getActivity().getApplicationContext(), "Set is Published", Toast.LENGTH_SHORT).show();
                MakeSetActivity MakeSetActivity = (com.example.along002.testingfinal.MakeSet.MakeSetActivity) getActivity();
                MakeSetActivity.restartActivity();
            }
        });

        return view;
    }

}
