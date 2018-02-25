package com.example.along002.testingfinal.Search;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.CardGames.CardFlipPreviewActivity;
import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.ManageSet.ManageSetActivity;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.CardRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchPreviewFragment extends Fragment{

    private FlashcardInfo flashcardInfo;
    private TextView cardSize,author,setName, tagsTextView;
    private Button cardsBtn, speedRoundBtn;
    ArrayList<String> termList = new ArrayList<>();
    ArrayList<String> defList = new ArrayList<>();
    View view;

    public SearchPreviewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewInit = inflater.inflate(R.layout.fragment_search_preview, container, false);

        view = viewInit;

        return viewInit;
    }

    public void startPreview(){
        final SearchActivity SearchActivity = (SearchActivity)getActivity();
        flashcardInfo = SearchActivity.getFlashcardInfo();

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
                SearchActivity.setScreenTransitionUp();
                startActivity(intent);
            }
        });

        speedRoundBtn = view.findViewById(R.id.speedRoundBtn);
        speedRoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.setUpDialog();
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
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_View);
        CardRecyclerViewAdapter adapter = new CardRecyclerViewAdapter(getActivity().getApplicationContext(), termList,defList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

}
