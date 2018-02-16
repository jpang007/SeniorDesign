package com.example.along002.testingfinal.ManageSet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;
import com.example.along002.testingfinal.Utils.CardRecyclerViewAdapter;

import java.util.ArrayList;

/**
 * Created by along002 on 2/2/2018.
 */

public class PreviewSetFragment extends Fragment{
    private static final String TAG = "PreviewSetFragment";
    private FlashcardInfo flash;
    ArrayList<String> termList = new ArrayList<>();
    ArrayList<String> defList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_preview_set,container,false);

        ManageSetActivity ManageSetActivity = (ManageSetActivity)getActivity();
        final FlashcardInfo flashcardInfo = ManageSetActivity.getFlashcardInfo();//get chosen flashcard set
        defList = flashcardInfo.getDefinitionList();//def list
        termList = flashcardInfo.getTermList();//term list

//        initRecyclerView();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_View);
        CardRecyclerViewAdapter adapter = new CardRecyclerViewAdapter(getActivity().getApplicationContext(), termList,defList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        return view;
    }
    private void initRecyclerView(){
        RecyclerView recyclerView = getView().findViewById(R.id.recycler_View);
        CardRecyclerViewAdapter adapter = new CardRecyclerViewAdapter(getActivity().getApplicationContext(), termList,defList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }
}
























