package com.example.along002.testingfinal.CardGames;

import android.content.Intent;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SpeedRoundActivity extends AppCompatActivity {

    private static final String TAG = "SpeedRoundActivity";
    private EditText inputEditText1, inputEditText2;
    private TextView testTextView1, testTextView2, cardNumTextView1, cardNumTextView2, timerTextView;
    private EasyFlipView easyFlipView;
    private ImageView cancelImageView, guessArrow1, guessArrow2;
    private String testChoice;
    private ArrayList<String> termList = new ArrayList<>();
    private ArrayList<String> defList = new ArrayList<>();
    private int cnt = 0;
    public int counter,timercnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_round);

        Intent i = getIntent();
        testChoice = i.getStringExtra("testChoice");
        termList = i.getStringArrayListExtra("termList");
        defList = i.getStringArrayListExtra("defList");
        timercnt = i.getIntExtra("timerCnt",30000);
        timercnt = timercnt * 1000;
        cancelImageView = findViewById(R.id.cancelImageView);
        easyFlipView = findViewById(R.id.easyFlipView);
        inputEditText1 = findViewById(R.id.inputEditText1);
        inputEditText2 = findViewById(R.id.inputEditText2);
        testTextView1 = findViewById(R.id.testTextView1);
        testTextView2 = findViewById(R.id.testTextView2);
        guessArrow1 = findViewById(R.id.guessArrow1);
        guessArrow2 = findViewById(R.id.guessArrow2);
        cardNumTextView1 = findViewById(R.id.cardNumTextView1);
        cardNumTextView2 = findViewById(R.id.cardNumTextView2);
        timerTextView = findViewById(R.id.timerTextView);
        new CountDownTimer(timercnt, 1000){
            @Override
            public void onTick(long millisUntilFinished) {

                timerTextView.setText(String.valueOf(((timercnt)/1000) - counter));
                counter++;
            }

            @Override
            public void onFinish() {
                timerTextView.setText("Donzos");

            }
        }.start();

        easyFlipView.setFlipDuration(1000);
        easyFlipView.setFlipOnTouch(false);


        if (testChoice.equals("Terms")){
            testTextView2.setText(termList.get(cnt));
        }
        else{
            testTextView2.setText(defList.get(cnt));
        }
        cnt = cnt + 1;
        cardNumTextView2.setText(Integer.toString(cnt)+ "/" + Integer.toString(defList.size()));
        cancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        easyFlipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView easyFlipView, EasyFlipView.FlipState newCurrentSide) {
                cnt = cnt + 1;//add on to the nxt cnt when a flip has occured
            }
        });

        guessArrow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String guessString = inputEditText1.getText().toString();

                if (guessString.equals(defList.get(cnt-1))) {
                    if (cnt == defList.size()){ //reached the end of the set list
                        finish();
                    }
                    else {
                        if (testChoice.equals("Terms")) {//setting up nxt card
                            testTextView2.setText(termList.get(cnt));
                        } else {
                            testTextView2.setText(defList.get(cnt));
                        }
                        easyFlipView.flipTheView();
                        inputEditText2.setText("");
                        cardNumTextView2.setText(Integer.toString(cnt+1)+ "/" + Integer.toString(defList.size()));
                    }
                }
                else{
                    Toast.makeText(SpeedRoundActivity.this, "Place Holder, Wrong Guess", Toast.LENGTH_SHORT).show();
                }
            }
        });

        guessArrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String guessString = inputEditText2.getText().toString();

                if (guessString.equals(defList.get(cnt-1))) {
                    if (cnt == defList.size()){ //reached the end of the set list
                        finish();
                    }
                    else {
                        if (testChoice.equals("Terms")) {//setting up nxt card
                            testTextView1.setText(termList.get(cnt));
                        } else {
                            testTextView1.setText(defList.get(cnt));
                        }
                        easyFlipView.flipTheView();
                        inputEditText1.setText("");
                        cardNumTextView1.setText(Integer.toString(cnt+1)+ "/" + Integer.toString(defList.size()));
                    }
                }
                else{
                    Toast.makeText(SpeedRoundActivity.this, "Place Holder, Wrong Guess", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
