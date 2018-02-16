package com.example.along002.testingfinal.ManageSet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.Search.SearchActivity;
import com.example.along002.testingfinal.Utils.SectionPagerAdapter;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by along002 on 2/1/2018.
 */

public class ManageSetActivity extends AppCompatActivity {
    private static final String TAG = "ManageSetActivity";
    private final int ACTIVITY_NUM = 2;
    private SectionPagerAdapter mSectionStatePagerAdapter;
    private ViewPager mViewPager;
    private ViewPager mParentViewPager;
    private int direction = 0;
    private FlashcardInfo FlashcardInfo;
    //disable screen transition
    @Override
    public void onPause() {
        super.onPause();
        if(direction == 0){
            overridePendingTransition(0, 0);
        }
        else{
            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
            direction = 0;
        }
    }

    public FlashcardInfo getFlashcardInfo(){ //getter for other fragments to get FlashInfo
        return FlashcardInfo;
    }
    public void setFlashcardInfo(FlashcardInfo FlashcardInfo){ //setter for other fragments to set FlashInfo
        this.FlashcardInfo = FlashcardInfo;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_set);
        setupBottomNavigationView();

        mSectionStatePagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mParentViewPager = (ViewPager) findViewById(R.id.parent_container);

        setupViewPager(mViewPager);

        ImageView search = (ImageView)findViewById(R.id.imageViewSearch);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                direction = 1;
            Intent intent = new Intent(ManageSetActivity.this,SearchActivity.class);
            startActivity(intent);
            }
        });


    }

    private void setupViewPager(ViewPager viewPager){//initial first screen
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SetListFragment());//index at 0
//        adapter.addFragment(new PreviewSetFragment());//index at 1
        viewPager.setAdapter(adapter);
    }
    public void setViewPager(int fragmentNumber){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SetListFragment());//index at 0
        adapter.addFragment(new PreviewSetFragment());//index at 1
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(fragmentNumber);
    }


    // BottomNavigationView setup
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(ManageSetActivity.this,bottomNavigationViewEx);
        overridePendingTransition(0, 0);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }
}
