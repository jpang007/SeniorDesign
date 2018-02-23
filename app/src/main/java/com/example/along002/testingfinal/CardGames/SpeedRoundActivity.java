package com.example.along002.testingfinal.CardGames;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SpeedRoundActivity extends AppCompatActivity {

    private TextView testTextView1, testTextView2;
    private EasyFlipView easyFlipView;
    private ImageView cancelImageView, guessArrow1, guessArrow2;
    private String testChoice;
    private ArrayList<String> termList = new ArrayList<>();
    private ArrayList<String> defList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_round);

        Intent i = getIntent();
        testChoice = i.getStringExtra("testChoice");
        termList = i.getStringArrayListExtra("termList");
        defList = i.getStringArrayListExtra("defList");
        cancelImageView = findViewById(R.id.cancelImageView);
        easyFlipView = findViewById(R.id.easyFlipView);

        testTextView1 = findViewById(R.id.testTextView1);
        testTextView2 = findViewById(R.id.testTextView2);
//        guessArrow1 = findViewById(R.id.guessArrow1);
//        guessArrow2 = findViewById(R.id.guessArrow2);

        int cnt = 0;
        easyFlipView.setFlipDuration(1000);

        testTextView2.setText(termList.get(cnt));

        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        guessArrow1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                easyFlipView.flipTheView();
//            }
//        });
//
//        guessArrow2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                easyFlipView.flipTheView();
//            }
//        });


    }
}
