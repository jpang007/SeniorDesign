package com.example.along002.testingfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserMenu extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu);

        Button addNewFlashcard = (Button) findViewById(R.id.addNewFlashcard);
        Button searchFlashcards = (Button) findViewById(R.id.searchFlashcards);
        addNewFlashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserMenu.this, FlashcardInitSettings.class));
            }
        });
//
//        searchFlashcards.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(UserMenu.this, searchFlashcards.class));
//            }
//        });
    }
}
