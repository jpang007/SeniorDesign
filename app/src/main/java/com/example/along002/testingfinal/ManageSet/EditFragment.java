package com.example.along002.testingfinal.ManageSet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.MakeSet.MakeSetActivity;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.MakeSetRecyclerViewAdapter;
import com.example.along002.testingfinal.Utils.RecyclerItemTouchHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
    private ArrayList<String> mTermList = new ArrayList<>();
    private ArrayList<String> mDefList = new ArrayList<>();
    private EditText setTitle, setTag;
    private TextView updateTextView;
    private RadioButton radioButtonPublic, radioButtonPrivate, radioButton;
    private RadioGroup radioGroup;
    private  MakeSetRecyclerViewAdapter adapter;

//    public EditFragment() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_edit, container, false);
        final ManageSetActivity ManageSetActivity = (ManageSetActivity)getActivity();
        final FlashcardInfo flashcardInfo = ManageSetActivity.getFlashcardInfo();//get chosen flashcard set

        mTermList = flashcardInfo.getTermList();
        mDefList = flashcardInfo.getDefinitionList();

        updateTextView = view.findViewById(R.id.updateTextView);
        setTitle = view.findViewById(R.id.setTitle);
        setTag = view.findViewById(R.id.set_Tag);
        radioGroup = view.findViewById(R.id.radioGroup);
        radioButtonPrivate = view.findViewById(R.id.privateRadioButton);
        radioButtonPublic = view.findViewById(R.id.publicRadioButton);

        setTitle.setText(flashcardInfo.getName()); //sets tag from flashcard
        String tag = "";
        for (int i = 0; i < flashcardInfo.getTagList().size(); i++){
            if( i == 0){
                tag = flashcardInfo.getTagList().get(0);
            }
            else {
                tag = tag + " " + flashcardInfo.getTagList().get(i);
            }
        }
        setTag.setText(tag);

        if (flashcardInfo.getPrivacy().equals("Public")){ //setting the private/public status
            radioButtonPublic.setChecked(true);
            radioButtonPrivate.setChecked(false);
        }
        else{
            radioButtonPublic.setChecked(false);
            radioButtonPrivate.setChecked(true);
        }

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_View);
        adapter = new MakeSetRecyclerViewAdapter(getActivity().getApplicationContext(),mTermList,mDefList);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        // adding item touch helper
        // only ItemTouchHelper.LEFT added to detect Right to Left swipe
        // if you want both Right -> Left and Left -> Right
        // add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        ImageView addCardImageView = (ImageView) view.findViewById(R.id.addCardImageView);
        addCardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDefList.add("");
                mTermList.add("");
                adapter.notifyItemInserted(mTermList.size() - 1);
            }
        });
        updateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlashcardInfo updatedFlashcard = new FlashcardInfo();

                updatedFlashcard.setName(setTitle.getText().toString());
                updatedFlashcard.setTermList(adapter.getmTermList());
                updatedFlashcard.setDefinitionList(adapter.getmDefList());
                updatedFlashcard.setAuthor(flashcardInfo.getAuthor());
                updatedFlashcard.setId(flashcardInfo.getId());
                updatedFlashcard.setCreator(flashcardInfo.getCreator());
                updatedFlashcard.setSize(Integer.toString(adapter.getmDefList().size()));

                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(selectedId);
                updatedFlashcard.setPrivacy(radioButton.getText().toString());

                ArrayList<String> mTagList = new ArrayList<>();
                mTagList.add(setTag.getText().toString());
                updatedFlashcard.setTagList(mTagList); // todo change this



                if (isValid(updatedFlashcard) == true){
                    DatabaseReference mUpdate = FirebaseDatabase.getInstance().getReference();
                    mUpdate.child("Flashcards").child(updatedFlashcard.getId())
                            .setValue(updatedFlashcard); //setting the set into the database

                    setTags(updatedFlashcard); //deleting old reference and setting new one for tags branch

                    Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                    ManageSetActivity ManageSetActivity = (com.example.along002.testingfinal.ManageSet.ManageSetActivity) getActivity();
                    ManageSetActivity.setupViewPager();
                    ManageSetActivity.setViewPager(1);
                }
            }
        });
        return view;
    }

    private void setTags(FlashcardInfo newFlashcardInfo){//deletes old reference of tags and setting new ones
        ManageSetActivity ManageSetActivity = (com.example.along002.testingfinal.ManageSet.ManageSetActivity) getActivity();
        FlashcardInfo oldFlashcardInfo = ManageSetActivity.getFlashcardInfo();
        DatabaseReference mTagRef = FirebaseDatabase.getInstance().getReference()
                .child("tags");
        for(int i = 0; i < oldFlashcardInfo.getTagList().size(); i++) {
            mTagRef.child(oldFlashcardInfo.getTagList().get(i)).child(oldFlashcardInfo.getId()).removeValue();
        }
        for (int i = 0; i < newFlashcardInfo.getTagList().size(); i++) {
            mTagRef.child(newFlashcardInfo.getTagList().get(i)).child(newFlashcardInfo.getId()).child("FlashId").setValue(newFlashcardInfo.getId());
        }

    }

    private boolean isValid(FlashcardInfo FlashcardInfo){ //checks if set is valid for update
        if (FlashcardInfo.getName().equals("")){
            Toast.makeText(getActivity().getApplicationContext(), "Title can not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (FlashcardInfo.getTagList().size() == 0){
            Toast.makeText(getActivity().getApplicationContext(), "Set needs at least one tag", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (FlashcardInfo.getDefinitionList().size() == 0){
            Toast.makeText(getActivity().getApplicationContext(), "Set needs at least one card", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (int i = 0; i < FlashcardInfo.getDefinitionList().size(); i++){
            if (FlashcardInfo.getTermList().get(i).equals("")){
                Toast.makeText(getActivity().getApplicationContext(), "A term is empty", Toast.LENGTH_SHORT).show();
                return false;
            }
            if (FlashcardInfo.getDefinitionList().get(i).equals("")){
                Toast.makeText(getActivity().getApplicationContext(), "A definition is empty", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        for (int i = 0; i < FlashcardInfo.getTagList().size(); i++){
            if (FlashcardInfo.getTagList().get(i).equals("")){
                Toast.makeText(getActivity().getApplicationContext(), "Error with Tags List, empty String should not get this msg", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        // remove the item from recycler view
        adapter.removeItem(viewHolder.getAdapterPosition());
    }
}
