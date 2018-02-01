package com.example.along002.testingfinal;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.along002.testingfinal.MainActivity;
import com.example.along002.testingfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail, editTextPass, nameTextView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    /**
     *disable screen transition
     */
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @IgnoreExtraProperties
    public class User {

        public String username;
        public String email;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String username, String email) {
            this.username = username;
            this.email = email;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        nameTextView = findViewById(R.id.nameTextView);
        findViewById(R.id.textViewLogin).setOnClickListener(this);
        findViewById(R.id.signUpBtn).setOnClickListener(this);
        findViewById(R.id.testArr).setOnClickListener(this);
        setTitle("Sign-Up");
    }


    private void registerUser() {
        final String eMail = editTextEmail.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();
        final String name = nameTextView.getText().toString().trim();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //EMAIL AND PASSWORD PARSING GO HERE TO MAKE SURE THEY ARE VALID EMAILS AND PASS
        mAuth.createUserWithEmailAndPassword(eMail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Account Successfully Made", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    String currUID = user.getUid();
                    User newuser = new User(name, eMail);
                    mDatabase.child("users").child(currUID).setValue(newuser);
                } else {
                    Toast.makeText(getApplicationContext(), "Account Unsuccessfully Made", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.textViewLogin:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.signUpBtn:
                registerUser();
                break;
            case R.id.testArr:
                Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
