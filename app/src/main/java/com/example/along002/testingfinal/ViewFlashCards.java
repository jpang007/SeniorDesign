package com.example.along002.testingfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewFlashCards extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mTempDatabase;
    private TextView defTextView, termTextView,textViewPrivacy,textViewName,textViewCreator,textViewTags;
    private static final String TAG = "UserPage";
    private int flashCardSize;
    private String flashId;
    private ImageView backArrow;
    private int currCard;
    private FlashcardInfo FlashcardInfo;

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
        defTextView = findViewById(R.id.testTextView);
        termTextView = findViewById(R.id.textView3);
        textViewCreator = findViewById(R.id.textViewCreator);
        textViewName = findViewById(R.id.textViewName);
        textViewPrivacy = findViewById(R.id.textViewPrivacy);
        textViewTags = findViewById(R.id.textViewTags);
        final Button nextCard = (Button) findViewById(R.id.testBtn);
        final Button deleteBtn = (Button) findViewById(R.id.deleteBtn);
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);

        flashId = getIntent().getStringExtra("flashId");

        mTempDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Flashcards").child(flashId);

        final ValueEventListener flashcardInfo = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FlashcardInfo = dataSnapshot.getValue(FlashcardInfo.class);

                textViewCreator.setText("Creator: " + FlashcardInfo.getCreator());
                textViewName.setText("Flashcard Name: " + FlashcardInfo.getName());
                textViewPrivacy.setText("Privacy: " + FlashcardInfo.getPrivacy());
                flashCardSize = Integer.parseInt(FlashcardInfo.getSize());
                defTextView.setText(FlashcardInfo.getDefinitionList().get(currCard));
                termTextView.setText(FlashcardInfo.getTermList().get(currCard));

                nextCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(currCard + 1 == flashCardSize){
                            currCard = 0;
                            defTextView.setText(FlashcardInfo.getDefinitionList().get(currCard));
                            termTextView.setText(FlashcardInfo.getTermList().get(currCard));
                        }
                        else{
                            currCard++;
                            defTextView.setText(FlashcardInfo.getDefinitionList().get(currCard));
                            termTextView.setText(FlashcardInfo.getTermList().get(currCard));
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
                        DatabaseReference removeTagReference = FirebaseDatabase.getInstance().getReference()
                                .child("tags");
                        for(int i = 0; i < FlashcardInfo.getTagList().size(); i++){
                            removeTagReference.child(FlashcardInfo.getTagList().get(i)).removeValue();
                        }
                        removeFlashId.removeValue();
                        removeUIDtoFlashId.removeValue();
                        Intent intent = new Intent(ViewFlashCards.this,chooseAFlashcard.class);
                        startActivity(intent);

                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mTempDatabase.addListenerForSingleValueEvent(flashcardInfo);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//go back to the previous activity
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });
    }
}
