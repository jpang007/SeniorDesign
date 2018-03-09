package com.example.along002.testingfinal.LoginAndSignUp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.along002.testingfinal.Home.HomeActivity;
import com.example.along002.testingfinal.ManageSet.EditFragment;
import com.example.along002.testingfinal.ManageSet.PreviewSetFragment;
import com.example.along002.testingfinal.ManageSet.SetListFragment;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.SectionPagerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Random;

public class LoginCreateAccountActivity extends AppCompatActivity {
    private static final String TAG = "LoginCreateAccountActivity";
    private ImageView bgImage;
    private int prevIndex = 0;
    private int currIndex = 1;
    private ViewSwitcher viewSwitcher;
    private ImageView firstImageView,secondImageView;
    private ViewPager mViewPager;


    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
    }

    //Set the radius of the Blur. Supported range 0 < radius <= 25
    private static final float BLUR_RADIUS = 5f;

    public Bitmap blur(Bitmap image) {
        if (null == image) return null;

        Bitmap outputBitmap = Bitmap.createBitmap(image);
        final RenderScript renderScript = RenderScript.create(this);
        Allocation tmpIn = Allocation.createFromBitmap(renderScript, image);
        Allocation tmpOut = Allocation.createFromBitmap(renderScript, outputBitmap);

        //Intrinsic Gausian blur filter
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);
        return outputBitmap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        viewSwitcher = findViewById(R.id.viewSwitcher);
        firstImageView = findViewById(R.id.firstImageView);
        secondImageView = findViewById(R.id.secondImageView);
        setUpBGImages();

        mViewPager = findViewById(R.id.container);
        setupViewPager();

    }

    public void setupViewPager(){
        SectionPagerAdapter adapter = new SectionPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());//index at 0
        adapter.addFragment(new CreateAccountFragment());//index at 1
        mViewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        mViewPager.setCurrentItem(fragmentNumber);
    }

    private void setUpBGImages() {

//        final int[] imageArray = {R.drawable.image1,R.drawable.image5,R.drawable.image6};
        final int[] imageArray = {R.drawable.image1,R.drawable.image2,R.drawable.image3,
                                R.drawable.image4,R.drawable.image5,R.drawable.image6,
                                R.drawable.image7,R.drawable.image8,R.drawable.image9,
                                R.drawable.image10};

        final Handler handler = new Handler();
        Runnable bgImageRunnable = new Runnable() {
            @Override
            public void run() {
                Random initRand = new Random();
                currIndex = initRand.nextInt(imageArray.length);

                while(prevIndex == currIndex){
                    Random rand = new Random();
                    currIndex = rand.nextInt(imageArray.length);
                }
                prevIndex = currIndex;

                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageArray[currIndex]);
                Bitmap blurredBitmap = blur(bitmap);

                if (viewSwitcher.getDisplayedChild() == 0) {
                    secondImageView.setImageBitmap(blurredBitmap);
                    viewSwitcher.showNext();
                } else {
                    firstImageView.setImageBitmap(blurredBitmap);
                    viewSwitcher.showPrevious();
                }
                handler.postDelayed(this,5000);
            }
        };
        handler.postDelayed(bgImageRunnable, 5000);
    }

}