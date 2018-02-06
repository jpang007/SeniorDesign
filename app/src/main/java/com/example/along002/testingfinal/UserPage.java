package com.example.along002.testingfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserPage extends AppCompatActivity {
    private static final String TAG = "UserPage";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private final Vector<String> termHolder = new Vector<String>();
    private final Vector<String> defHolder = new Vector<String>();
    private String authorName = "";
    Button logOutButton, addItemBtn, displayFoods;
    EditText itemTextView, itemTextView2;
    /**
     *screen transition
     */
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        mAuth = FirebaseAuth.getInstance();

        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        Button backButton = (Button) findViewById(R.id.backButton);
        Button addItemBtn = (Button) findViewById(R.id.addItemBtn);
        Button addSet = (Button) findViewById(R.id.addSet);
        final EditText itemTextView = (EditText) findViewById(R.id.itemTextView);
        final EditText itemTextView2 = (EditText) findViewById(R.id.itemTextView2);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String term = itemTextView.getText().toString();
            String definition = itemTextView2.getText().toString();
            termHolder.addElement(term);
            defHolder.addElement(definition);
            itemTextView.setText("");
            itemTextView2.setText("");

            Toast.makeText(getApplicationContext(),"Added Flashcard!", Toast.LENGTH_SHORT).show();
            }
        });
        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String flashcardSetName = getIntent().getStringExtra("flashcardSetName");
                final String privacySettings = getIntent().getStringExtra("privacySettings");
                final String flashcardSetTags = getIntent().getStringExtra("flashcardTags");
                FirebaseUser curruser = FirebaseAuth.getInstance().getCurrentUser();
                final String userUID = curruser.getUid();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                final String mFlashId = mDatabase.push().getKey(); // get a random key for set
                DatabaseReference mAuthorRef = FirebaseDatabase.getInstance().getReference().child("users").child(userUID);
                mAuthorRef.addValueEventListener(new ValueEventListener() { //getting Author Name from database
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        authorName = user.getUsername();

                        FlashcardInfo newFlashSet = new FlashcardInfo();
                        newFlashSet.setCreator(userUID);
                        newFlashSet.setName(flashcardSetName);
                        newFlashSet.setPrivacy(privacySettings);
                        newFlashSet.setSize(Integer.toString(termHolder.size()));
                        newFlashSet.setTags(flashcardSetTags);
                        newFlashSet.setId(mFlashId);
                        newFlashSet.setAuthor(authorName);

                        mDatabase.child("Flashcards").child(mFlashId).setValue(newFlashSet);
                        for(int i = 0;i < termHolder.size(); ++i ) {
                            mDatabase.child("Flashcards").child(mFlashId).child(Integer.toString(i)).child("Term").setValue(termHolder.get(i));
                            mDatabase.child("Flashcards").child(mFlashId).child(Integer.toString(i)).child("Definition").setValue(defHolder.get(i));
                        }

                        flashIdAndName flashIdAndName = new flashIdAndName();
                        flashIdAndName.setFlashId(mFlashId);
                        flashIdAndName.setFlashName(flashcardSetName);
                        mDatabase.child("usersFlash").child(userUID).child(mFlashId).setValue(flashIdAndName);


                        Toast.makeText(getApplicationContext(),"Added Set!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserPage.this, AboutUsActivity.class));
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String flashcardSetName = getIntent().getStringExtra("flashcardSetName");
                Intent intent = new Intent(UserPage.this, FlashcardInitSettings.class);
                intent.putExtra("passBackFlashcardSetName", flashcardSetName);

                String privacySettings = getIntent().getStringExtra("privacySettings");
                intent.putExtra("passBackPrivacySettings", privacySettings);

                startActivity(intent);
          //      Toast.makeText(getApplicationContext(),"Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

