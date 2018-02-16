package com.example.along002.testingfinal.MakeSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.FlashcardInitSettings;
import com.example.along002.testingfinal.ManageSet.ManageSetActivity;
import com.example.along002.testingfinal.ManageSet.PreviewSetFragment;
import com.example.along002.testingfinal.ManageSet.SetListFragment;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Search.SearchActivity;
import com.example.along002.testingfinal.Utils.BottomNavigationViewHelper;
import com.example.along002.testingfinal.Utils.SectionPagerAdapter;
import com.example.along002.testingfinal.chooseAFlashcard;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

/**
 * Created by along002 on 1/31/2018.
 */

public class MakeSetActivity extends AppCompatActivity{
    private static final String TAG = "MakeSetActivity";
    private final int ACTIVITY_NUM = 1;
    private SectionPagerAdapter mSectionStatePagerAdapter;
    private ViewPager mViewPager;
    private FlashcardInfo FlashcardInfo = new FlashcardInfo();
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
    public void setFlashcard(ArrayList<String> mTermList, ArrayList<String> mDefList){
         this.FlashcardInfo.setDefinitionList(mDefList);
         this.FlashcardInfo.setTermList(mTermList);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_set);
        Log.d(TAG, "onCreate: started");
        setupBottomNavigationView();

        mSectionStatePagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        setupViewPager(mViewPager);

        ImageView search = (ImageView)findViewById(R.id.imageViewSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = 1;
                Intent intent = new Intent(MakeSetActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public void restartActivity(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
//        this.recreate();
    }
    private void setupViewPager(ViewPager viewPager) {//initial first screen
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MakeASetFragment());//index at 0
        adapter.addFragment(new OptionFragment());//index at 1
        viewPager.setAdapter(adapter);
    }
    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
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
