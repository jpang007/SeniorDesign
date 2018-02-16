package com.example.along002.testingfinal.MakeSet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.CardRecyclerViewAdapter;
import com.example.along002.testingfinal.Utils.MakeSetRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MakeASetFragment extends Fragment {
    private ArrayList<String> mTermList = new ArrayList<>();
    private ArrayList<String> mDefList = new ArrayList<>();
    private ArrayList<String> mCardNum = new ArrayList<>();
    private EditText setTitle;
    private EditText setTag;
    private int mSetSize;
    private TextView publishTextView;
    private  MakeSetRecyclerViewAdapter adapter;

//    public MakeASetFragment() {
//        // Required empty public constructor
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_aset,container,false);

        mTermList.add("");
        mDefList.add("");
        mCardNum.add("0");
        final RecyclerView recyclerView = view.findViewById(R.id.recycler_View);
        adapter = new MakeSetRecyclerViewAdapter(getActivity().getApplicationContext(),mTermList,mDefList, mCardNum);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        ImageView addCardImageView = (ImageView) view.findViewById(R.id.addCardImageView);

        addCardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MakeSetRecyclerViewAdapter adapter = new MakeSetRecyclerViewAdapter(getActivity().getApplicationContext(), mTermList, mDefList);
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                mDefList.add("");
                mTermList.add("");
                mCardNum.add(Integer.toString(mCardNum.size()));
                adapter.notifyItemInserted(mCardNum.size() - 1);
            }
        });



        return view;
    }

}
