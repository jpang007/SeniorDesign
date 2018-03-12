package com.example.along002.testingfinal.Search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.KeyEvent;
import android.support.v4.view.ViewPager;
import android.view.inputmethod.EditorInfo;
import android.content.Intent;
import android.widget.Toast;

import com.example.along002.testingfinal.Utils.CustomViewPager;
import com.example.along002.testingfinal.Utils.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.example.along002.testingfinal.Utils.SectionPagerAdapter;
import com.example.along002.testingfinal.CardGames.SpeedRoundActivity;

import java.util.HashMap;


/**
 * Created by along002 on 2/1/2018.
 */

public class SearchActivity extends AppCompatActivity implements SearchSetListFragment.OnItemSelect{
    private static final String TAG = "SearchActivity";
    private final int ACTIVITY_NUM = 3;
    private FirebaseAuth mAuth;
    private EditText searchEditText;
    private CustomViewPager mViewPager;
    private String searchTerm;
    private ImageView backArrowView;
    private FlashcardInfo FlashcardInfo;
    private SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
    private int direction = 0;
    private HashMap<String, Boolean> favMap = new HashMap<>();



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
    public ImageView getBackArrowView(){
        return this.backArrowView;
    }
    public void setSearchTerm(String searchTerm){
        this.searchTerm = searchTerm;
    }
    public String getSearchTerm(){
        return this.searchTerm;
    }
    public void setFlashcardInfo(FlashcardInfo FlashcardInfo){//sets Flashcard Info for other Fragments
        this.FlashcardInfo = FlashcardInfo;
    }
    public FlashcardInfo getFlashcardInfo(){//getter for other fragments
        return this.FlashcardInfo;
    }
    public void setScreenTransitionUp(){
        this.direction = 1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupBottomNavigationView();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(currentUser.getUid());

        backArrowView = findViewById(R.id.backArrowView);
        mViewPager = findViewById(R.id.container);
        searchEditText = findViewById(R.id.searchEditText);
        mViewPager.disableScroll(true);

        TextView.OnEditorActionListener searchListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    example_confirm();//match this behavior to your 'Send' (or Confirm) button
                    setSearchTerm(searchEditText.getText().toString().toLowerCase());
                    setupInitialViewPager(mViewPager);
                }
                return true;
            }
        };
        setUpFavoriteList();
        searchEditText.setOnEditorActionListener(searchListener);
    }

    private void setupInitialViewPager(ViewPager viewPager){//initial first screen
        adapter.addFragment(new SearchSetListFragment());//index at 0
        adapter.addFragment(new SearchPreviewFragment());//index at 1
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
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

    public void setUpDialog(){
        SearchCustomSettingDialog dialog = new SearchCustomSettingDialog();
        dialog.show(getFragmentManager(), "SearchCustomSettingDialog");
    }
    public void goToSpeedRound(String testChoice, int timerCnt, boolean isRandomized){ //goes to speend round Activity
        Intent intent = new Intent(this, SpeedRoundActivity.class);
        intent.putExtra("timerCnt", timerCnt);
        intent.putExtra("testChoice",testChoice);
        intent.putExtra("isRandomized", isRandomized);
        intent.putStringArrayListExtra("termList",FlashcardInfo.getTermList());
        intent.putStringArrayListExtra("defList",FlashcardInfo.getDefinitionList());
        startActivity(intent);
    }

    public void toast_Error(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.layout_wrong_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        TextView text = (TextView) layout.findViewById(R.id.toastTextView);
        text.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 120);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    @Override
    public void itemSelected() {
        SectionPagerAdapter fragmentAdapter = (SectionPagerAdapter)mViewPager.getAdapter();
        SearchPreviewFragment SearchPreviewFragment = (SearchPreviewFragment) fragmentAdapter.getItem(1);

        SearchPreviewFragment.startPreview();
        setViewPager(1);
    }

    // BottomNavigationView setup
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(SearchActivity.this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

}
