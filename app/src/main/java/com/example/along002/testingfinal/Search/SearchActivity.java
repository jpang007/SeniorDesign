package com.example.along002.testingfinal.Search;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.TagsHelper;
import com.example.along002.testingfinal.ViewFlashCards;
import com.example.along002.testingfinal.chooseAFlashcard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<FlashcardInfo>  searchResults = new ArrayList<>();
    private ArrayList<String> searchResultsName = new ArrayList<>();
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

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, searchResultsName);
        searchFlashcardList.setAdapter(adapter);

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String searchText = searchEditText.getText().toString();
                Query mSearch = myRef.child("Flashcards")
                        .orderByChild("tags")
                        .startAt(searchText);
//                        .endAt(searchEditText+"\uf8ff");
//                        .once("value");
                mSearch.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            FlashcardInfo result = snapshot.getValue(FlashcardInfo.class);
                            searchResults.add(result);
                            searchResultsName.add(result.getName());
                        }
                        if(searchResults.size() == 0){
                            Toast.makeText(getApplication(),"No Results",Toast.LENGTH_SHORT).show();
                        }
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
