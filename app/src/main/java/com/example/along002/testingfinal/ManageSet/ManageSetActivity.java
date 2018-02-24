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
import android.widget.Toast;

import com.example.along002.testingfinal.CardGames.SpeedRoundActivity;
import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.Search.SearchActivity;
import com.example.along002.testingfinal.Utils.SectionPagerAdapter;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.BottomNavigationViewHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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


    public void goToSpeedRound(String testChoice, int timerCnt){
        Intent intent = new Intent(this, SpeedRoundActivity.class);
        intent.putExtra("timerCnt", timerCnt);
        intent.putExtra("testChoice",testChoice);
        intent.putStringArrayListExtra("termList",FlashcardInfo.getTermList());
        intent.putStringArrayListExtra("defList",FlashcardInfo.getDefinitionList());
        startActivity(intent);
    }
    public void setUpDialog(){
        CustomSettingDialog dialog = new CustomSettingDialog();
        dialog.show(getFragmentManager(), "CustomSettingDialog");
    }
    public void setScreenTransitionUp(){
        this.direction = 1;
    }

    public FlashcardInfo getFlashcardInfo(){ //getter for other fragments to get FlashInfo
        return this.FlashcardInfo;
    }

    public void setFlashcardInfo(FlashcardInfo FlashcardInfo){ //setter for other fragments to set FlashInfo
        this.FlashcardInfo = FlashcardInfo;
    }

    public void deleteSet(){
        FlashcardInfo deletedFlashCard = this.FlashcardInfo;

        DatabaseReference removeFlashId = FirebaseDatabase.getInstance().getReference()
                .child("Flashcards").child(deletedFlashCard.getId());
        DatabaseReference removeUIDtoFlashId = FirebaseDatabase.getInstance().getReference()
                .child("usersFlash").child(deletedFlashCard.getCreator()).child(deletedFlashCard.getId());
        DatabaseReference removeTagReference = FirebaseDatabase.getInstance().getReference()
                .child("tags");
        for(int i = 0; i < FlashcardInfo.getTagList().size(); i++){
            removeTagReference.child(FlashcardInfo.getTagList().get(i)).child(FlashcardInfo.getId()).removeValue();
        }
        removeFlashId.removeValue();
        removeUIDtoFlashId.removeValue();
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupInitialViewPager(mViewPager);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_set);
        setupBottomNavigationView();

        mSectionStatePagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mParentViewPager = (ViewPager) findViewById(R.id.parent_container);

        setupInitialViewPager(mViewPager);


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

    private void setupInitialViewPager(ViewPager viewPager){//initial first screen
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SetListFragment());//index at 0
        viewPager.setAdapter(adapter);
    }

    public void setupViewPager(){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SetListFragment());//index at 0
        adapter.addFragment(new PreviewSetFragment());//index at 1
        adapter.addFragment(new EditFragment());//index at 2
        mViewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
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
