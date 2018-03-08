package com.example.along002.testingfinal.ManageSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.along002.testingfinal.CardGames.CardFlipPreviewActivity;
import com.example.along002.testingfinal.Utils.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.CardRecyclerViewAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by along002 on 2/2/2018.
 */

public class PreviewSetFragment extends Fragment{
    private static final String TAG = "PreviewSetFragment";
    private FirebaseAuth mAuth;
    private FlashcardInfo flashcardInfo;
    private TextView cardSize,author,setName, tagsTextView;
    private Button deleteSetBtn, editSetBtn, cardsBtn, speedRoundBtn;
    private ImageButton favoriteBtn;
    private FirebaseUser currentUser;
    private DatabaseReference mFavoriteDatabase;
    private HashMap<String, Boolean> favMap = new HashMap<>();
    ArrayList<String> termList = new ArrayList<>();
    ArrayList<String> defList = new ArrayList<>();
    View view;


    public void onToggleStar(View v){
        favoriteBtn.setSelected(!favoriteBtn.isSelected());
        if (favoriteBtn.isSelected() == true){
            mFavoriteDatabase.child(currentUser.getUid())
                    .child(flashcardInfo.getId()).child("FlashId")
                    .setValue(flashcardInfo.getId());
        }
        else {
            mFavoriteDatabase.child(currentUser.getUid())
                    .child(flashcardInfo.getId()).child("FlashId")
                    .removeValue();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View viewInit = inflater.inflate(R.layout.fragment_preview_set,container,false);

        view = viewInit;

        return viewInit;
    }

    public void startPreview(){
        final ManageSetActivity ManageSetActivity = (ManageSetActivity)getActivity();
        flashcardInfo = ManageSetActivity.getFlashcardInfo();//get chosen flashcard set
        favMap = ManageSetActivity.getFavMap();
        mFavoriteDatabase = FirebaseDatabase.getInstance().getReference()
                .child("favorites");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        defList = flashcardInfo.getDefinitionList();//def list
        termList = flashcardInfo.getTermList();//term list

        tagsTextView = view.findViewById(R.id.tagsTextView);
        for (int i = 0; i < flashcardInfo.getTagList().size(); i++){
            if (i == 0){
                tagsTextView.setText(flashcardInfo.getTagList().get(i));
            }
            else {
                tagsTextView.setText(tagsTextView.getText().toString() + ", " + flashcardInfo.getTagList().get(i));
            }
        }

        cardsBtn = view.findViewById(R.id.cardsBtn); //card flip preview
        cardsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CardFlipPreviewActivity.class);
                intent.putStringArrayListExtra("defList",defList);
                intent.putStringArrayListExtra("termList",termList);
                ManageSetActivity.setScreenTransitionUp();
                startActivity(intent);
            }
        });

        favoriteBtn = view.findViewById(R.id.favoriteBtn);
        if (favMap.containsKey(flashcardInfo.getId()) == true ){
            favoriteBtn.setSelected(true);
        }
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToggleStar(v);
            }
        });

        speedRoundBtn = view.findViewById(R.id.speedRoundBtn);
        speedRoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageSetActivity.setUpDialog();
            }
        });

        deleteSetBtn = view.findViewById(R.id.deleteSetBtn);
        deleteSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageSetActivity.showDeleteAlertDialog();
            }
        });

        editSetBtn = view.findViewById(R.id.editSetBtn);
        editSetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManageSetActivity.setViewPager(2);
            }
        });

        cardSize = view.findViewById(R.id.cardSize);
        cardSize.setText(flashcardInfo.getSize() + " cards");

        author = view.findViewById(R.id.author);
        author.setText(flashcardInfo.getAuthor());

        setName = view.findViewById(R.id.setName);
        setName.setText(flashcardInfo.getName());

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_View);
        CardRecyclerViewAdapter adapter = new CardRecyclerViewAdapter(getActivity().getApplicationContext(), termList,defList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }
}