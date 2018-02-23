package com.example.along002.testingfinal.CardGames;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.CardFlipRecyclerAdapter;
import com.example.along002.testingfinal.Utils.CardRecyclerViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CardFlipPreviewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageView cancelImageView;
    private CardFlipRecyclerAdapter adapter;
    private ArrayList<String> termList = new ArrayList<>();
    private ArrayList<String> defList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip_preview);

        Intent i = getIntent();
        termList = i.getStringArrayListExtra("termList");
        defList = i.getStringArrayListExtra("defList");

        cancelImageView = findViewById(R.id.cancelImageView);
        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new CardFlipRecyclerAdapter(CardFlipPreviewActivity.this, termList, defList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CardFlipPreviewActivity.this));




    }
}
