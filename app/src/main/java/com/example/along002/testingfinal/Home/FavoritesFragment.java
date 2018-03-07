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
import com.example.along002.testingfinal.Utils.SetPreviewRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by along002 on 1/31/2018.
 */

public class FavoritesFragment extends Fragment implements SetPreviewRecyclerAdapter.OnItemClick{
    private static final String TAG = "FavoritesFragment";
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private HomeOnItemSelect mCallback;
    private HashMap<String, Boolean> favMap = new HashMap<>();
    private ArrayList<FlashcardInfo> favoritesSetsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites,container,false);

        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mFavoritesSetRef = FirebaseDatabase.getInstance().getReference()
                                    .child("favorites").child(mAuth.getCurrentUser().getUid());
        Query mFavoritesSet = mFavoritesSetRef.orderByChild("FlashId").startAt("");

        HomeActivity HomeActivity = (HomeActivity)getActivity();
        favMap = HomeActivity.getFavMap();
        mCallback = (HomeOnItemSelect) getActivity();
        recyclerView = view.findViewById(R.id.recyclerView);

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                favoritesSetsList.clear();
                ArrayList<String> flashIdList = new ArrayList<>(); // a list of all flash id in favorites
                DatabaseReference mFlashSearch = FirebaseDatabase.getInstance().getReference().child("Flashcards");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String flashId = (String) snapshot.child("FlashId").getValue();
                    flashIdList.add(flashId);
                }
                for (int i = 0; i < flashIdList.size(); i++){
                    DatabaseReference mFlashcard = mFlashSearch.child(flashIdList.get(i));
                    mFlashcard.addListenerForSingleValueEvent(new ValueEventListener() { //get info from individual set
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            FlashcardInfo tempFlashcardInfo = dataSnapshot.getValue(FlashcardInfo.class);
                            favoritesSetsList.add(tempFlashcardInfo);

                            SetPreviewRecyclerAdapter adapter = new SetPreviewRecyclerAdapter(getActivity().getApplicationContext(), favoritesSetsList, favMap,FavoritesFragment.this);
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
        };
        mFavoritesSet.addValueEventListener(eventListener);


        return view;
    }
    public interface HomeOnItemSelect {
        void itemSelected();
    }

    @Override
    public void onClick(int position) {
        FlashcardInfo selectedItem = favoritesSetsList.get(position);
        HomeActivity HomeActivity = (HomeActivity)getActivity();
        HomeActivity.setFlashcardInfo(selectedItem);
        mCallback.itemSelected();
    }
}
