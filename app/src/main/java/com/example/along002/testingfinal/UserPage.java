package com.example.along002.testingfinal;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    Button logOutButton, addItemBtn;
    EditText itemTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        mAuth = FirebaseAuth.getInstance();

//        final FirebaseUser curruser = FirebaseAuth.getInstance().getCurrentUser();
//        String userUID = curruser.getUid();
//        Toast.makeText(getApplication(),userUID,Toast.LENGTH_SHORT).show();

        Button logOutButton = (Button) findViewById(R.id.logoutButton);
        Button addItemBtn = (Button) findViewById(R.id.addItemBtn);
        final EditText itemTextView = (EditText) findViewById(R.id.itemTextView);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("message");
//        myRef.setValue("Hello, World!");

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
            String newFood = itemTextView.getText().toString();
            FirebaseUser curruser = FirebaseAuth.getInstance().getCurrentUser();
            String userUID = curruser.getUid();
            //myRef.child(userUID).child("Food").child("Favorite Food").child(newFood).setValue("True");
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child(userUID).child("Favorite Food").child(newFood).setValue("True");
            itemTextView.setText("");

            Toast.makeText(getApplicationContext(),"Added Item", Toast.LENGTH_SHORT).show();
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(UserPage.this, MainActivity.class));
                Toast.makeText(getApplicationContext(),"Logged Out", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
