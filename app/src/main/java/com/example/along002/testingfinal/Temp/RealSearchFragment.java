package com.example.along002.testingfinal.Temp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.along002.testingfinal.R;

/**
 * Created by along002 on 2/2/2018.
 */

public class RealSearchFragment extends Fragment{
    private static final String TAG = "SearchFragment";
    private Button backBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_real_search,container,false);


        return view;
    }
}