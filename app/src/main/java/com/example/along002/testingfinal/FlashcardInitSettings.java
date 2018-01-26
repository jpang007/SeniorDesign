package com.example.along002.testingfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FlashcardInitSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_init_settings);

        final EditText flashcardSetName = (EditText) findViewById(R.id.flashcardSetName);
        Button backButton = (Button) findViewById(R.id.backButton);
        Button continueButton = (Button) findViewById(R.id.continueButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String setName = flashcardSetName.getText().toString();
                Intent intent = new Intent(FlashcardInitSettings.this, UserPage.class);
                intent.putExtra("flashcardSetName", setName);
                startActivity(intent);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FlashcardInitSettings.this, UserMenu.class));
            }
        });
    }
}
