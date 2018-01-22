package com.example.along002.testingfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

    EditText editTextEmail, editTextPass, nameTextView;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


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
    }

    private void registerUser() {
        String eMail = editTextEmail.getText().toString().trim();
        String pass = editTextPass.getText().toString().trim();
        String name = nameTextView.getText().toString().trim();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //EMAIL AND PASSWORD PARSING GO HERE TO MAKE SURE THEY ARE VALID EMAILS AND PASS
        mAuth.createUserWithEmailAndPassword(eMail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Account Successfully Made", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(),"Account Unsuccessfully Made", Toast.LENGTH_SHORT).show();
                }
            }
        });

        FirebaseUser user = mAuth.getCurrentUser();
        String currUID = user.getUid();
        mDatabase.child(currUID).child(name).setValue("True");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.textViewLogin:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.signUpBtn:
                registerUser();
                break;

        }
    }
}