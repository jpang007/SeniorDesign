package com.example.along002.testingfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FlashcardInitSettings extends AppCompatActivity {

    private String flashTagString = "";
    private ArrayList<String> tagList = new ArrayList<>();
    private TextView tagsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard_init_settings);


        final EditText flashcardSetName = (EditText) findViewById(R.id.flashcardSetName);
        final EditText flashcardTags = (EditText) findViewById(R.id.flashcardTags); //static variables
        final Button backButton = (Button) findViewById(R.id.backButton);
        Button continueButton = (Button) findViewById(R.id.continueButton);
        tagsTextView = (TextView) findViewById(R.id.tagsTextView);
        final RadioGroup radioPrivacyGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        Button addTagBtn = (Button) findViewById(R.id.addTagBtn);

        String passBackFlashcardSetName = getIntent().getStringExtra("passBackFlashcardSetName");
        flashcardSetName.setText(passBackFlashcardSetName, TextView.BufferType.EDITABLE);


        addTagBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tempTag = flashcardTags.getText().toString().toLowerCase();
                tagList.add(tempTag);
                flashcardTags.setText("");
                if(tagsTextView.getText().toString() == ""){
                    tagsTextView.setText(tempTag);
                }
                else{
                    tagsTextView.setText(tagsTextView.getText().toString() + ", " + tempTag);
                }

            }
        });

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(flashcardSetName.getText())) {
                    flashcardSetName.setError("Please name your flashcard set!");
                } else {
                    String setName = flashcardSetName.getText().toString();
//                    String setTag = flashcardTags.getText().toString(); //change to string
                    Intent intent = new Intent(FlashcardInitSettings.this, UserPage.class);
                    intent.putExtra("flashcardSetName", setName);

                    int selectedButton = radioPrivacyGroup.getCheckedRadioButtonId();
                    RadioButton radioPrivacyButton = (RadioButton) findViewById(selectedButton);
                    intent.putExtra("privacySettings", radioPrivacyButton.getText());
                    intent.putExtra("flashcardTags", flashTagString);
                    intent.putExtra("tagList",tagList);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                }
            }
    });
    backButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(FlashcardInitSettings.this, UserMenu.class));
        }
    });
    backArrow.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
        }
    });
}
}