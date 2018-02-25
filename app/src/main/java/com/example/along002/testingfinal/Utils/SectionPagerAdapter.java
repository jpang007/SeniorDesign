package com.example.along002.testingfinal.Utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by along002 on 1/31/2018.
 * stores fragments for tabs
 */

public class SectionPagerAdapter extends FragmentPagerAdapter{
    private static final String TAG = "SectionPagerAdapter";
    private HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    private final List<Fragment> mFragmentList = new ArrayList<>();

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
//        if (fragmentHashMap.get(position) != null){
//            return fragmentHashMap.get(position)
//        }
//
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    public void addFragment(Fragment fragment){
        mFragmentList.add(fragment);
//        fragmentHashMap.put(mFragmentList.size()-1, fragment);
    }
}
