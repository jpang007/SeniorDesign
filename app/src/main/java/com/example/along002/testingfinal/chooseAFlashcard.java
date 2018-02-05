package com.example.along002.testingfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.along002.testingfinal.Utils.BottomNavigationViewHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class chooseAFlashcard extends AppCompatActivity {
    private static final String TAG = "chooseAFlashcard";

    private ListView listView;
    private FirebaseAuth mAuth;
    ArrayList<String> flashName = new ArrayList<>();
    ArrayList<String> flashId = new ArrayList<>();
    /**
     *disable screen transition
     */
    @Override
    public void onPause() {
        super.onPause();
//        overridePendingTransition(0, 0);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_aflashcard);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser curruser = FirebaseAuth.getInstance().getCurrentUser();
        String userUID = curruser.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("usersFlash").child(userUID);
        listView = findViewById(R.id.flashcardList);
        ImageView backArrow = (ImageView) findViewById(R.id.backArrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
            }
        });
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, flashName);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    flashIdAndName flashInfo = snapshot.getValue(flashIdAndName.class);
                    flashName.add(flashInfo.getFlashName());
                    flashId.add(flashInfo.getFlashId());
                }
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        // ListView Clicked item index
                        int itemPosition = position;
                        // ListView Clicked item value
//                        String itemValue = (String) listView.getItemAtPosition(position);

                        Intent intent = new Intent(chooseAFlashcard.this,ViewFlashCards.class);
                        intent.putExtra("flashId", flashId.get(itemPosition));
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


