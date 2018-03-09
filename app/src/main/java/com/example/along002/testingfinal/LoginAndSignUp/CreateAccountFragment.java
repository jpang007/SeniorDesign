package com.example.along002.testingfinal.LoginAndSignUp;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateAccountFragment extends Fragment {
    private static final String TAG = "CreateAccountFragment";
    private TextView loginTextView,signUpBtn;
    EditText emailEditText, passEditText, nameEditText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public CreateAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);

        final LoginCreateAccountActivity LoginCreateAccountActivity = (LoginCreateAccountActivity)getActivity();
        mAuth = FirebaseAuth.getInstance();
        emailEditText = view.findViewById(R.id.editTextEmail);
        passEditText = view.findViewById(R.id.editTextPass);
        nameEditText = view.findViewById(R.id.nameEditText);

        Drawable profileImageView = getResources().getDrawable(R.drawable.ic_white_profile);
        profileImageView.setBounds(0,0,60,60);
        profileImageView.setAlpha(150);

        Drawable passwordImageView = getResources().getDrawable(R.drawable.ic_password);
        passwordImageView.setBounds(0,0,60,60);
        passwordImageView.setAlpha(150);

        Drawable emailImageView = getResources().getDrawable(R.drawable.ic_email);
        emailImageView.setBounds(0,0,60,60);
        emailImageView.setAlpha(150);


        nameEditText.setCompoundDrawables(profileImageView,null,null,null);
        passEditText.setCompoundDrawables(passwordImageView,null,null,null);
        emailEditText.setCompoundDrawables(emailImageView,null,null,null);



        signUpBtn = view.findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        loginTextView = view.findViewById(R.id.loginTextView);
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginCreateAccountActivity.setViewPager(0);
            }
        });

        return view;
    }
    private void registerUser() {
        final String eMail = emailEditText.getText().toString().trim();
        String pass = passEditText.getText().toString().trim();
        final String name = nameEditText.getText().toString().trim();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        //EMAIL AND PASSWORD PARSING GO HERE TO MAKE SURE THEY ARE VALID EMAILS AND PASS
        mAuth.createUserWithEmailAndPassword(eMail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Account Successfully Made", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    String currUID = user.getUid();
                    User newUser = new User(eMail,name);
                    mDatabase.child("users").child(currUID).setValue(newUser);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Account Unsuccessfully Made", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
