package com.example.along002.testingfinal.Search;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Search1.SearchActivity1;
import com.example.along002.testingfinal.Utils.SetPreviewRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchSetListFragment extends Fragment implements SetPreviewRecyclerAdapter.OnItemClick{
    private static final String TAG = "SearchSetListFragment";

    private OnItemSelect mCallback;
    private ArrayList<FlashcardInfo> mSetInfoList = new ArrayList<>();
    private RecyclerView recyclerView;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();

    public SearchSetListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_set_list, container, false);

        final SearchActivity SearchActivity = (SearchActivity)getActivity();
        recyclerView = view.findViewById(R.id.recyclerView);

        mCallback = (OnItemSelect) getActivity();

        String mSearchTerm = SearchActivity.getSearchTerm();

        Query mTagSearh = myRef.child("tags").child(mSearchTerm)
                .orderByChild("FlashId").startAt("");

        mTagSearh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> flashIdList = new ArrayList<>(); // a list of all flash id with the search tag
                DatabaseReference mFlashSearch = FirebaseDatabase.getInstance().getReference().child("Flashcards");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { //iterate through all FlashId
                    String flashId = (String) snapshot.child("FlashId").getValue();
                    flashIdList.add(flashId);
                }
                if(flashIdList.size() == 0){
                    Toast.makeText(SearchActivity, "No Results", Toast.LENGTH_SHORT).show();
                }
                for (int i = 0; i < flashIdList.size(); i++){
                    DatabaseReference mFlashcard = mFlashSearch.child(flashIdList.get(i));
                    mFlashcard.addListenerForSingleValueEvent(new ValueEventListener() { //get info from individual set
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            FlashcardInfo tempFlashcardInfo = dataSnapshot.getValue(FlashcardInfo.class);
                            mSetInfoList.add(tempFlashcardInfo);

                            SetPreviewRecyclerAdapter adapter = new SetPreviewRecyclerAdapter(getActivity().getApplicationContext(), mSetInfoList,SearchSetListFragment.this);
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

    public interface OnItemSelect{
        void itemSelected();
    }

    @Override
    public void onClick(int position) {
        SearchActivity SearchActivity = (SearchActivity)getActivity();
        SearchActivity.setFlashcardInfo(mSetInfoList.get(position));
        SearchActivity.setViewPager(1);
        mCallback.itemSelected();

    }
}
