package com.example.along002.testingfinal.ManageSet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.FlashcardDisplayAdapter;
import com.example.along002.testingfinal.Utils.SetPreviewRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by along002 on 2/2/2018.
 */

public class SetListFragment extends Fragment implements SetPreviewRecyclerAdapter.OnItemClick{

    private static final String TAG = "SetListFragment";
    private ArrayList<FlashcardInfo> mSetInfoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private HashMap<String, Boolean> favMap = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_set_list,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);

        ManageSetActivity ManageSetActivity = (ManageSetActivity) getActivity();
        favMap = ManageSetActivity.getFavMap();

        String UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference mUserFlash = FirebaseDatabase.getInstance().getReference().child("usersFlash").child(UID);
        mUserFlash.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> flashIdList = new ArrayList<>();
                DatabaseReference mFlashSearch = FirebaseDatabase.getInstance().getReference().child("Flashcards");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String setId = (String) snapshot.child("flashId").getValue();
                    flashIdList.add(setId);//Add sets to a list for recycler view
                }

                for(int i = 0;i < flashIdList.size(); i++){ //iterate through arraylist to add to the adapter
                    DatabaseReference mFlashcard = mFlashSearch.child(flashIdList.get(i));
                    mFlashcard.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            FlashcardInfo tempFlashcardInfo = dataSnapshot.getValue(FlashcardInfo.class);
                            mSetInfoList.add(tempFlashcardInfo);
                            SetPreviewRecyclerAdapter adapter = new SetPreviewRecyclerAdapter(getActivity().getApplicationContext(), mSetInfoList, favMap,SetListFragment.this);
                            recyclerView.setAdapter(adapter);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return view;
    }

    @Override
    public void onClick(int position) {
        ManageSetActivity ManageSetActivity = (ManageSetActivity) getActivity();
        ManageSetActivity.setFlashcardInfo(mSetInfoList.get(position));

        ManageSetActivity.setupViewPager();
        ManageSetActivity.setViewPager(1);
    }
}