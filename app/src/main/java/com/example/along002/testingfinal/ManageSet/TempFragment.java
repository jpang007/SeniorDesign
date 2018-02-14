package com.example.along002.testingfinal.ManageSet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;

import com.example.along002.testingfinal.R;

/**
 * Created by along002 on 2/2/2018.
 */

public class TempFragment extends Fragment{
    private static final String TAG = "TempFragment";
    private Button button3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_temp,container,false);

        button3 = (Button) view.findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Back",Toast.LENGTH_SHORT).show();
                ((ManageSetActivity)getActivity()).setViewPager(1);
            }
        });

        return view;
    }
}