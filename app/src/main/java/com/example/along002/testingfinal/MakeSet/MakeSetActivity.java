package com.example.along002.testingfinal.MakeSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInitSettings;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Search.SearchActivity;
import com.example.along002.testingfinal.Utils.BottomNavigationViewHelper;
import com.example.along002.testingfinal.chooseAFlashcard;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by along002 on 1/31/2018.
 */

public class MakeSetActivity extends AppCompatActivity{
    private static final String TAG = "MakeSetActivity";
    private final int ACTIVITY_NUM = 1;

    private int direction = 0;
     @Override
    public void onPause() {
        super.onPause();
         if(direction == 0){
             overridePendingTransition(0, 0);
         }
         else{
             overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
             direction = 0;
         }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_set);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationView();

        Button testMid = (Button) findViewById(R.id.testmid);
        Button viewFlashcardBtn = (Button) findViewById(R.id.viewFlashcardBtn);
        Button makeFlashcardBtn = (Button) findViewById(R.id.makeFlashcardBtn);
        ImageView search = (ImageView)findViewById(R.id.imageViewSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = 1;
                Intent intent = new Intent(MakeSetActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
        makeFlashcardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MakeSetActivity.this,FlashcardInitSettings.class);
                startActivity(intent);
                direction++;
            }
        });

        testMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"Test mid", Toast.LENGTH_SHORT).show();
            }
        });

        viewFlashcardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MakeSetActivity.this,chooseAFlashcard.class));
                direction++;
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
        BottomNavigationViewHelper.enableNavigation(MakeSetActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
