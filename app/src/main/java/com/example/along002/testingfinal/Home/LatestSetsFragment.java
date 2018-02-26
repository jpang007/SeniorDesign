package com.example.along002.testingfinal.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Search.SearchSetListFragment;
import com.example.along002.testingfinal.Utils.SectionPagerAdapter;
import com.example.along002.testingfinal.Utils.SetPreviewRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by along002 on 1/31/2018.
 */

public class LatestSetsFragment extends Fragment implements SetPreviewRecyclerAdapter.OnItemClick{
    private static final String TAG = "LatestSetsFragment";
    private RecyclerView recyclerView;
    private ArrayList<FlashcardInfo> LatestSetsList = new ArrayList<>();
    private int i = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_latest_set,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        DatabaseReference mLatestSetsRef = FirebaseDatabase.getInstance().getReference().child("Flashcards");
        Query mLatestSets = mLatestSetsRef.orderByChild("name").startAt("").limitToFirst(20);


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    FlashcardInfo temp = (FlashcardInfo) snapshot.getValue(FlashcardInfo.class);
                    LatestSetsList.add(temp);
                }
                SetPreviewRecyclerAdapter adapter = new SetPreviewRecyclerAdapter(getActivity().getApplicationContext(), LatestSetsList,LatestSetsFragment.this);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        mLatestSets.addListenerForSingleValueEvent(eventListener);

        return view;
    }


    @Override
    public void onClick(int value) {

    }
}
