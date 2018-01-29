package com.example.along002.testingfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Printer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

public class ViewFlashCards extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mTempDatabase;
    private TextView testTextView, textView3,textViewPrivacy,textViewName,textViewCreator;
    private int flashSize;
    private static final String TAG = "UserPage";
    private int currCard = 0;
    private int flashCardSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_flash_cards);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user1 = mAuth.getCurrentUser();
        String userUID = user1.getUid();

        setTitle("View FlashCard");
        testTextView = findViewById(R.id.testTextView);
        textView3 = findViewById(R.id.textView3);
        textViewCreator = findViewById(R.id.textViewCreator);
        textViewName = findViewById(R.id.textViewName);
        textViewPrivacy = findViewById(R.id.textViewPrivacy);
        mTempDatabase = FirebaseDatabase.getInstance().getReference()
                .child("Flashcards").child("Test FlashCard");

        final ValueEventListener flashcardInfo = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                FlashcardInfo tempFlashInfo = dataSnapshot.getValue(FlashcardInfo.class);
                textViewCreator.setText("Creator: " + tempFlashInfo.getCreator());
                textViewName.setText("Flashcard Name: " + tempFlashInfo.getName());
                textViewPrivacy.setText("Privacy: " + tempFlashInfo.getPrivacy());
//                Toast.makeText(getApplication(),tempFlashInfo.getSize(),Toast.LENGTH_SHORT).show();
                flashCardSize = Integer.parseInt(tempFlashInfo.getSize());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mTempDatabase.addListenerForSingleValueEvent(flashcardInfo);


//        mTempDatabase = testListner;
//        mDatabase.removeEventListener(mTempDatabase);

        Button test = (Button) findViewById(R.id.testBtn);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDatabase = FirebaseDatabase.getInstance().getReference()
                        .child("Flashcards").child("Test FlashCard").child(String.valueOf(currCard));
                ValueEventListener testListner = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                flashCardSize = Integer.parseInt(testing.getSize());
//                mTempDatabase = FirebaseDatabase.getInstance().getReference()
//                                .child("Flashcards").child("Test FlashCard").child(String.valueOf(currCard));
                  FlashCard tempFlash = dataSnapshot.getValue(FlashCard.class);
//                for(int i = 0; i < flashCardSize; ++i){
//                    mDatabase = FirebaseDatabase.getInstance().getReference()
//                            .child("Flashcards").child("Test FlashCard").child(String.valueOf(i));
//                }
                    testTextView.setText(tempFlash.getDefinition());
                        textView3.setText(tempFlash.getTerm());
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                mDatabase.addListenerForSingleValueEvent(testListner);
                currCard++;
                if(currCard > flashCardSize){
                    currCard = 0;
                }
            }
        });
    }
}
