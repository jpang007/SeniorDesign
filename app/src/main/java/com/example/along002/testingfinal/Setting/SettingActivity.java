package com.example.along002.testingfinal.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.along002.testingfinal.LoginAndSignUp.LoginActivity;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by along002 on 2/1/2018.
 */

public class SettingActivity extends AppCompatActivity {
    private static final String TAG = "SettingActivity";
    private FirebaseAuth mAuth;
    private final int ACTIVITY_NUM = 4;
    //disable screen transition
    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setupBottomNavigationView();
        mAuth = FirebaseAuth.getInstance();
        TextView logoutTextView = (TextView) findViewById(R.id.logoutTextView);

        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent = new Intent(SettingActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    // BottomNavigationView setup
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(SettingActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
