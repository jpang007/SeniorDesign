package com.example.along002.testingfinal.Home;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.along002.testingfinal.CardGames.SpeedRoundActivity;
import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.ManageSet.CustomSettingDialog;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Search.SearchCustomSettingDialog;
import com.example.along002.testingfinal.Utils.BottomNavigationViewHelper;
import com.example.along002.testingfinal.Utils.SectionPagerAdapter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Calendar;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements LatestSetsFragment.HomeOnItemSelect {
    private static final String TAG = "HomeActivity";
    private final int ACTIVITY_NUM = 0;
    private ViewPager viewPager;
    private FlashcardInfo FlashcardInfo = new FlashcardInfo();
    private int direction = 0;
    private SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());


    //disable screen transition
    @Override
    public void onPause() {
        super.onPause();
        if(direction == 0){
            overridePendingTransition(0, 0);
            //disable screen transition
        }
        else{
            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
            direction = 0;
        }
    }
    public void setScreenTransitionUp(){
        this.direction = 1;
    }
    public void setUpDialog(){
        HomeCustomSettingDialog dialog = new HomeCustomSettingDialog();
        dialog.show(getFragmentManager(), "HomeCustomSettingDialog");
    }
    public void setFlashcardInfo(FlashcardInfo FlashcardInfo){
        this.FlashcardInfo = FlashcardInfo;
    }
    public FlashcardInfo getFlashcardInfo(){
        return this.FlashcardInfo;
    }

    public void goToSpeedRound(String testChoice, int timerCnt, boolean isRandomized){//goes to speend round Activity
        Intent intent = new Intent(this, SpeedRoundActivity.class);
        intent.putExtra("timerCnt", timerCnt);
        intent.putExtra("testChoice",testChoice);
        intent.putExtra("isRandomized",isRandomized);
        intent.putStringArrayListExtra("termList",FlashcardInfo.getTermList());
        intent.putStringArrayListExtra("defList",FlashcardInfo.getDefinitionList());
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager = (ViewPager) findViewById(R.id.container);


        setupBottomNavigationView();
        setupViewPager();
    }

    //Sets up the 3 tabs on top
    private void setupViewPager(){

        adapter.addFragment(new LatestSetsFragment());//index at 0
        adapter.addFragment(new HomeFragment());//index at 1
        adapter.addFragment(new HomePreviewFragment());//index at 2


        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addTab(tabLayout.newTab().setText("All Sets"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorites"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                setViewPager(position);
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
//        tabLayout.getTabAt(0).setText("Latest Sets");
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_camera);
//        tabLayout.getTabAt(1).setIcon(R.drawable.ic_android);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_search);

    }

    public void setViewPager(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }

    // BottomNavigationView setup
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(HomeActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    @Override
    public void itemSelected() {
        SectionPagerAdapter fragmentAdapter = (SectionPagerAdapter)viewPager.getAdapter();
        HomePreviewFragment HomePreviewFragment = (HomePreviewFragment) fragmentAdapter.getItem(2);
//        HomePreviewFragment.startPreview();
//        HomePreviewFragment.test();
        setViewPager(1);
        HomePreviewFragment.startPreview();
        setViewPager(2);
    }
}
