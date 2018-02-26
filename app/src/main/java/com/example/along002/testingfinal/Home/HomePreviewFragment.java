package com.example.along002.testingfinal.Home;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.CardGames.CardFlipPreviewActivity;
import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Search.SearchActivity;
import com.example.along002.testingfinal.Utils.CardRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by along002 on 1/31/2018.
 */

public class HomePreviewFragment extends Fragment{
    private static final String TAG = "PreviewSetFragment";
    private FlashcardInfo flashcardInfo;
    private TextView cardSize,author,setName, tagsTextView;
    private Button cardsBtn, speedRoundBtn,matchBtn;
    ArrayList<String> termList = new ArrayList<>();
    ArrayList<String> defList = new ArrayList<>();
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewInit = inflater.inflate(R.layout.fragment_search_preview,container,false);

        view = viewInit;

        return viewInit;
    }

    public void startPreview(){
        final HomeActivity HomeActivity = (com.example.along002.testingfinal.Home.HomeActivity)getActivity();
        flashcardInfo = HomeActivity.getFlashcardInfo();

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
                HomeActivity.setScreenTransitionUp();
                startActivity(intent);
            }
        });
        speedRoundBtn = view.findViewById(R.id.speedRoundBtn);
        speedRoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.setUpDialog();
            }
        });

//        matchBtn = view.findViewById(R.id.matchBtn);
//        matchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity().getApplicationContext(),)
//            }
//        });

        cardSize = view.findViewById(R.id.cardSize);
        cardSize.setText(flashcardInfo.getSize() + " cards");
        author = view.findViewById(R.id.author);
        author.setText(flashcardInfo.getAuthor());

        setName = view.findViewById(R.id.setName);
        setName.setText(flashcardInfo.getName());

        initRecyclerView();

    }

    public void test(){
        Toast.makeText(getActivity().getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_View);
        CardRecyclerViewAdapter adapter = new CardRecyclerViewAdapter(getActivity().getApplicationContext(), termList,defList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }
}
