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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText emailTxt, passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        findViewById(R.id.TextViewSignUp).setOnClickListener(this);
        findViewById(R.id.loginBtn).setOnClickListener(this);
        findViewById(R.id.logoutBtn).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
    }

    private void loginUser(){
        String eMail = emailTxt.getText().toString().trim();
        String pass = passwordTxt.getText().toString().trim();

        //EMAIL AND PASSWORD PARSING GO HERE TO MAKE SURE THEY ARE VALID EMAILS AND PASS
        mAuth.signInWithEmailAndPassword(eMail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Unsuccessful Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TextViewSignUp:
                startActivity(new Intent(this,SignUp.class));
                break;
            case R.id.loginBtn:
                loginUser();
                startActivity(new Intent(this,UserPage.class));
                break;
            case R.id.logoutBtn:
                mAuth.signOut();
                break;
            case R.id.button3:
                FirebaseUser user1 = mAuth.getCurrentUser();
                String userUID = user1.getUid();
                Toast.makeText(getApplicationContext(),userUID,Toast.LENGTH_SHORT).show();

        }
    }
}