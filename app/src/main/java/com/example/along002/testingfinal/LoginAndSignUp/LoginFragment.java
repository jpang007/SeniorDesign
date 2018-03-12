package com.example.along002.testingfinal.LoginAndSignUp;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.Home.HomeActivity;
import com.example.along002.testingfinal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private FirebaseAuth mAuth;
    EditText emailEditText, passwordEditText;
    TextView TextViewSignUp;
    TextView loginBtn;
    ProgressBar progressBar;
    private LoginCreateAccountActivity LoginCreateAccountActivity;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        emailEditText = view.findViewById(R.id.emailTxt);
        passwordEditText = view.findViewById(R.id.passwordTxt);
        progressBar = view.findViewById(R.id.progressBar);

        LoginCreateAccountActivity = (LoginCreateAccountActivity)getActivity();

        loginBtn = view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
        TextViewSignUp = view.findViewById(R.id.TextViewSignUp);
        TextViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginCreateAccountActivity.setViewPager(1);
            }
        });

        Drawable passwordImageView = getResources().getDrawable(R.drawable.ic_password);
        passwordImageView.setBounds(0,0,60,60);
        passwordImageView.setAlpha(150);

        Drawable emailImageView = getResources().getDrawable(R.drawable.ic_email);
        emailImageView.setBounds(0,0,60,60);
        emailImageView.setAlpha(150);

        emailEditText.setCompoundDrawables(emailImageView,null,null,null);
        passwordEditText.setCompoundDrawables(passwordImageView,null,null,null);

        return view;
    }
    private void loginUser(){
        progressBar.setVisibility(View.VISIBLE);
        loginBtn.setVisibility(View.GONE);

        String eMail = emailEditText.getText().toString().trim();
        String pass = passwordEditText.getText().toString().trim();

        //HARDCODED ACCOUNT CUZ IM LAZY
//        eMail = "1@gmail.com";
//        pass = "123456";
        if (eMail.equals("") || pass.equals("")){
            LoginCreateAccountActivity.toast_Error("Unsuccessful Login");
            progressBar.setVisibility(View.GONE);
            loginBtn.setVisibility(View.VISIBLE);
        }
        else {
            mAuth.signInWithEmailAndPassword(eMail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getActivity().getApplicationContext(), HomeActivity.class));

                    }
                    else{
                        progressBar.setVisibility(View.GONE);
                        loginBtn.setVisibility(View.VISIBLE);
                        LoginCreateAccountActivity.toast_Error("Unsuccessful Login");
                    }
                }
            });
        }

    }
}
