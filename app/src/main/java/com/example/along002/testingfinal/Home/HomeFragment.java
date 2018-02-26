package com.example.along002.testingfinal.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.along002.testingfinal.R;

/**
 * Created by along002 on 1/31/2018.
 */

public class HomeFragment extends Fragment{
    private static final String TAG = "HomeFragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

//        Toast.makeText(getActivity().getApplicationContext(), "", Toast.LENGTH_SHORT).show();

        return view;
    }
}
