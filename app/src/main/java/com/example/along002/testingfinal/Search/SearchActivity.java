package com.example.along002.testingfinal.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.FlashcardDisplayAdapter;
import com.example.along002.testingfinal.ViewFlashCards;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    private ArrayList<FlashcardInfo> searchResults = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        ImageView imageViewSearch = (ImageView) findViewById(R.id.imageViewSearch);
        final EditText searchEditText = (EditText) findViewById(R.id.searchEditText);
        final ListView searchFlashcardList = (ListView) findViewById(R.id.searchFlashcardList);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        final FlashcardDisplayAdapter adapter = new FlashcardDisplayAdapter(this,R.layout.adapter_flashcard_view_layout,searchResults);


        searchFlashcardList.setAdapter(adapter);

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchResults.clear();
                final String searchText = searchEditText.getText().toString();

                Query mTagSearh = myRef.child("tags").child(searchText)
                                    .orderByChild("FlashId").startAt("");
                mTagSearh.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList<String> flashIdList = new ArrayList<>();
                        DatabaseReference mFlashSearch = FirebaseDatabase.getInstance().getReference().child("Flashcards");
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String flashId = (String) snapshot.child("FlashId").getValue();
                            flashIdList.add(flashId);
                        }
                        if(flashIdList.size() == 0){
                            Toast.makeText(getApplication(),"No Results",Toast.LENGTH_SHORT).show();
                        }
                        for (int i = 0; i < flashIdList.size(); i++){
                            DatabaseReference mFlashcard = mFlashSearch.child(flashIdList.get(i));
                            mFlashcard.addListenerForSingleValueEvent(new ValueEventListener() { //get info from individual set
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    FlashcardInfo tempFlashcardInfo = dataSnapshot.getValue(FlashcardInfo.class);
                                    searchResults.add(tempFlashcardInfo);
                                    adapter.notifyDataSetChanged();
                                    searchFlashcardList.setAdapter(adapter);
                                    searchFlashcardList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            // ListView Clicked item index
                                            int itemPosition = position;

                                            Intent intent = new Intent(SearchActivity.this,ViewFlashCards.class);
                                            String flashId = searchResults.get(itemPosition).getId();
                                            intent.putExtra("flashId", flashId);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.enter_from_right, R.anim.exit_to_left);
                                        }
                                    });
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
            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });


    }
}
