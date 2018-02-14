package com.example.along002.testingfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.along002.testingfinal.Home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    EditText emailTxt, passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        emailTxt = findViewById(R.id.emailTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        findViewById(R.id.TextViewSignUp).setOnClickListener(this);
        findViewById(R.id.loginBtn).setOnClickListener(this);
        findViewById(R.id.logoutBtn).setOnClickListener(this);
        findViewById(R.id.testingBtn).setOnClickListener(this);
    }

    private void loginUser(){
        String eMail = emailTxt.getText().toString().trim();
        String pass = passwordTxt.getText().toString().trim();

        //HARDCODED ACCOUNT CUZ IM LAZY
        eMail = "1@gmail.com";
        pass = "123456";

        //EMAIL AND PASSWORD PARSING GO HERE TO MAKE SURE THEY ARE VALID EMAILS AND PASS
        mAuth.signInWithEmailAndPassword(eMail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

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
                overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                break;
            case R.id.loginBtn:
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                loginUser();
                break;
            case R.id.logoutBtn:
                mAuth.signOut();
                break;
            case R.id.testingBtn:
                startActivity(new Intent(this,TestingActivity.class));
                break;
        }
    }
}