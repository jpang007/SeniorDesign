package com.example.along002.testingfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.along002.testingfinal.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import junit.framework.Test;

/**
 * Created by along002 on 1/31/2018.
 */

public class AboutUsActivity extends AppCompatActivity{
    private static final String TAG = "AboutUsActivity";
    private final int ACTIVITY_NUM = 1;
    /**
    *disable screen transition
    */
     @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationView();

        Button testtop = (Button) findViewById(R.id.testtop);
        Button testmid = (Button) findViewById(R.id.testmid);
        Button viewFlashcardBtn = (Button) findViewById(R.id.viewFlashcardBtn);
        Button makeFlashcardBtn = (Button) findViewById(R.id.makeFlashcardBtn);

        makeFlashcardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutUsActivity.this,FlashcardInitSettings.class);
                startActivity(intent);
            }
        });

        testtop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"Test top", Toast.LENGTH_SHORT).show();
            }
        });
        testmid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"Test mid", Toast.LENGTH_SHORT).show();
            }
        });

        viewFlashcardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this,chooseAFlashcard.class));
            }
        });

    }
    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(AboutUsActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
