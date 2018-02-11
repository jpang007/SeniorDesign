package com.example.along002.testingfinal;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.Utils.FlashCard;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewFlashCards extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mTempDatabase;
    private TextView testTextView, textView3,textViewPrivacy,textViewName,textViewCreator,textViewTags;
    private static final String TAG = "UserPage";
    private int flashCardSize;
    private String flashId;
    private ImageView backArrow;
    private int currCard;

    @Override
    public void onPause() {
        super.onPause();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flash_cards);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth.getCurrentUser();
        final String userUID = user1.getUid();
        currCard = 0;
        setTitle("View FlashCard");
        testTextView = findViewById(R.id.testTextView);
        textView3 = findViewById(R.id.textView3);
        textViewCreator = findViewById(R.id.textViewCreator);
        textViewName = findViewById(R.id.textViewName);
        textViewPrivacy = findViewById(R.id.textViewPrivacy);
        textViewTags = findViewById(R.id.textViewTags);
        Button test = (Button) findViewById(R.id.testBtn);
        Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);

        flashId = getIntent().getStringExtra("flashId");

        mTempDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Flashcards").child(flashId);

        final ValueEventListener flashcardInfo = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FlashcardInfo tempFlashInfo = dataSnapshot.getValue(FlashcardInfo.class);
                textViewCreator.setText("Creator: " + tempFlashInfo.getCreator());
                textViewName.setText("Flashcard Name: " + tempFlashInfo.getName());
                textViewPrivacy.setText("Privacy: " + tempFlashInfo.getPrivacy());
//                textViewTags.setText("Tags: " + tempFlashInfo.getTags());
                flashCardSize = Integer.parseInt(tempFlashInfo.getSize());
                Toast.makeText(getApplication(),Integer.toString(tempFlashInfo.getTag().size()),Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mTempDatabase.addListenerForSingleValueEvent(flashcardInfo);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference()
                        .child("Flashcards").child(flashId).child(Integer.toString(currCard));
                ValueEventListener testListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                  FlashCard tempFlash = dataSnapshot.getValue(FlashCard.class);
                    testTextView.setText(tempFlash.getDefinition());
                        textView3.setText(tempFlash.getTerm());
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                };
                mDatabase.addListenerForSingleValueEvent(testListener);
                currCard++;
                if(currCard >= flashCardSize){
                    currCard = 0;
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //deletes the set in the flashcard branch and the usersFlash branch
                DatabaseReference removeFlashId = FirebaseDatabase.getInstance().getReference()
                                                    .child("Flashcards").child(flashId);
                DatabaseReference removeUIDtoFlashId = FirebaseDatabase.getInstance().getReference()
                                                    .child("usersFlash").child(userUID).child(flashId);
                removeFlashId.removeValue();
                removeUIDtoFlashId.removeValue();
                Intent intent = new Intent(ViewFlashCards.this,chooseAFlashcard.class);
                startActivity(intent);

            }
        });
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//go back to the previous activity
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });
    }
}
