package com.example.along002.testingfinal.CardGames;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.along002.testingfinal.Home.LatestSetsFragment;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.MakeSetRecyclerViewAdapter;
import com.example.along002.testingfinal.Utils.MatchRecyclerAdapter;
import com.example.along002.testingfinal.Utils.SetPreviewRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class MatchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> termList = new ArrayList<>();
    private ArrayList<String> defList = new ArrayList<>();
    private ImageView cancelImageView;
    private HashMap<String, Integer> termMap = new HashMap<>();
    private HashMap<String, Integer> defMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);

        Intent i = getIntent();
        termList = i.getStringArrayListExtra("termList");
        defList = i.getStringArrayListExtra("defList");
        recyclerView = findViewById(R.id.recyclerView);
        cancelImageView = findViewById(R.id.cancelImageView);
        cancelImageView.setOnClickListener(new View.OnClickListener() {//finishes Activity and return to prev screen
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ArrayList<String> shuffledCardsList = new ArrayList<>();

        for (int j = 0; j < termList.size(); j++){
            shuffledCardsList.add(termList.get(j));
            termMap.put(termList.get(j),j);
            defMap.put(defList.get(j),j);
        }

        for (int j = 0; j < termList.size(); j++){
            shuffledCardsList.add(defList.get(j));
        }

        Integer[] indexArr = new Integer[termList.size()*2];
        for (int k = 0; k < termList.size()*2; k++){
            indexArr[k] = k;
        }
        Collections.shuffle(Arrays.asList(indexArr));

        ArrayList<String> tempShuffledCards = new ArrayList<>();

        for (int x = 0; x < shuffledCardsList.size(); x++){
            tempShuffledCards.add(shuffledCardsList.get(indexArr[x]));

        }
        shuffledCardsList = tempShuffledCards;

        MatchRecyclerAdapter adapter = new MatchRecyclerAdapter(this, termList,defList,shuffledCardsList,indexArr,termMap,defMap);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }


}
