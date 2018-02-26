package com.example.along002.testingfinal.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.along002.testingfinal.R;

/**
 * Created by along002 on 1/31/2018.
 */

public class HomePreviewFragment extends Fragment{
    private static final String TAG = "PreviewSetFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_preview,container,false);

        return view;
    }
}
