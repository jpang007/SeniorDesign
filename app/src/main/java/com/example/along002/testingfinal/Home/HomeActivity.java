package com.example.along002.testingfinal.Home;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.along002.testingfinal.CardGames.SpeedRoundActivity;
import com.example.along002.testingfinal.Utils.CustomViewPager;
import com.example.along002.testingfinal.Utils.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.BottomNavigationViewHelper;
import com.example.along002.testingfinal.Utils.SectionPagerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity implements LatestSetsFragment.HomeOnItemSelect, FavoritesFragment.HomeOnItemSelect {
    private static final String TAG = "HomeActivity";
    private final int ACTIVITY_NUM = 0;
    private CustomViewPager viewPager;
    private FirebaseAuth mAuth;
    private FlashcardInfo FlashcardInfo = new FlashcardInfo();
    private int direction = 0;
    private HashMap<String, Boolean> favMap = new HashMap<>();
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
    public void setFlashcardInfo(FlashcardInfo FlashcardInfo){//sets Flashcard Info for other Fragments
        this.FlashcardInfo = FlashcardInfo;
    }
    public FlashcardInfo getFlashcardInfo(){//getter for other fragments
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
        viewPager = findViewById(R.id.container);
        viewPager.disableScroll(true);

        setUpFavoriteList();
        setupBottomNavigationView();
        setupViewPager();
    }

    public HashMap<String,Boolean> getFavMap(){
        return favMap;
    }

    public void setUpFavoriteList(){
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mFavoritesSetRef = FirebaseDatabase.getInstance().getReference()
                .child("favorites").child(mAuth.getCurrentUser().getUid());
        Query mFavoritesSet = mFavoritesSetRef.orderByChild("FlashId").startAt("");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favMap.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String flashId = (String) snapshot.child("FlashId").getValue();
                    favMap.put(flashId,true);
                }

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mFavoritesSet.addValueEventListener(eventListener);
    }

    //Sets up the 2 tabs on top
    private void setupViewPager(){

        adapter.addFragment(new LatestSetsFragment());//index at 0
        adapter.addFragment(new FavoritesFragment());//index at 1
        adapter.addFragment(new HomePreviewFragment());//index at 2


        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Preview Sets"));
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
                int position = tab.getPosition();
                setViewPager(position);
            }
        });
    }

    public void setViewPager(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }

    @Override
    public void itemSelected() {// an item is selected from LatestSetsFragment
        SectionPagerAdapter fragmentAdapter = (SectionPagerAdapter)viewPager.getAdapter();
        HomePreviewFragment HomePreviewFragment = (HomePreviewFragment) fragmentAdapter.getItem(2);
        setViewPager(1);
        HomePreviewFragment.startPreview();
        setViewPager(2);
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

}
