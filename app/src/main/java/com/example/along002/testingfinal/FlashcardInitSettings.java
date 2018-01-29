package com.example.along002.testingfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class FlashcardInitSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_init_settings);

        final EditText flashcardSetName = (EditText) findViewById(R.id.flashcardSetName);
        final EditText flashcardTags = (EditText) findViewById(R.id.flashcardTags); //static variables
        final Button backButton = (Button) findViewById(R.id.backButton);
        Button continueButton = (Button) findViewById(R.id.continueButton);
        final RadioGroup radioPrivacyGroup = (RadioGroup) findViewById(R.id.radioGroup);

        String passBackFlashcardSetName = getIntent().getStringExtra("passBackFlashcardSetName");
        flashcardSetName.setText(passBackFlashcardSetName, TextView.BufferType.EDITABLE);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(flashcardSetName.getText())) {
                    flashcardSetName.setError("Please name your flashcard set!");
                } else {
                    String setName = flashcardSetName.getText().toString();
                    String setTag = flashcardTags.getText().toString(); //change to string
                    Intent intent = new Intent(FlashcardInitSettings.this, UserPage.class);
                    intent.putExtra("flashcardSetName", setName);

                    int selectedButton = radioPrivacyGroup.getCheckedRadioButtonId();
                    RadioButton radioPrivacyButton = (RadioButton) findViewById(selectedButton);
                    intent.putExtra("privacySettings", radioPrivacyButton.getText());
                    intent.putExtra("flashcardTags", setTag);
                    startActivity(intent);
                }
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