package com.example.along002.testingfinal.ManageSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Search.SearchActivity;
import com.example.along002.testingfinal.Utils.FlashcardDisplayAdapter;
import com.example.along002.testingfinal.ViewFlashCards;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by along002 on 2/2/2018.
 */

public class SetListFragment extends Fragment{
    private static final String TAG = "SetListFragment";
    private ArrayList<FlashcardInfo> flashcardInfoList = new ArrayList<>();
    private ListView setListView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_list,container,false);

        setListView = view.findViewById(R.id.setList);
        final FlashcardDisplayAdapter adapter = new FlashcardDisplayAdapter(getActivity().getApplicationContext(),R.layout.adapter_flashcard_view_layout,flashcardInfoList);
        setListView.setAdapter(adapter);

        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mUserFlash = FirebaseDatabase.getInstance().getReference().child("usersFlash").child(UID);
        Query mUserSet = mUserFlash.orderByChild("flashId").startAt("");
        mUserFlash.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> flashIdList = new ArrayList<>();
                DatabaseReference mFlashSearch = FirebaseDatabase.getInstance().getReference().child("Flashcards");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String setId = (String) snapshot.child("flashId").getValue();
                    flashIdList.add(setId);
                }
                for(int i = 0;i < flashIdList.size(); i++){
                    DatabaseReference mFlashcard = mFlashSearch.child(flashIdList.get(i));
                    mFlashcard.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            FlashcardInfo tempFlashcardInfo = dataSnapshot.getValue(FlashcardInfo.class);
                            flashcardInfoList.add(tempFlashcardInfo);
                            adapter.notifyDataSetChanged();
                            setListView.setAdapter(adapter);
                            setListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    // ListView Clicked item index
                                    int itemPosition = position;
                                    String flashId = flashcardInfoList.get(itemPosition).getId();
//                                    Intent intent = new Intent(SearchActivity.this,ViewFlashCards.class);
                                    Toast.makeText(getActivity().getApplicationContext(),flashId,Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
//      ((ManageSetActivity)getActivity()).setViewPager(1);

        return view;
    }
}