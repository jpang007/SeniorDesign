package com.example.along002.testingfinal.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.R;

import java.util.ArrayList;

/**
 * Created by along002 on 2/15/2018.
 */

public class CardRecyclerViewAdapter extends RecyclerView.Adapter<CardRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "CardRecyclerViewAdapter";

    private ArrayList<String> mTermList = new ArrayList<>();
    private ArrayList<String> mDefList = new ArrayList<>();
    private Context mContext;

    public CardRecyclerViewAdapter(Context mContext, ArrayList<String> mTermList, ArrayList<String> mDefList) {
        this.mTermList = mTermList;
        this.mDefList = mDefList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_view, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.def.setText(mDefList.get(position));
        holder.term.setText(mTermList.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "Clicked on " + position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDefList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView term;
        TextView def;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            def = itemView.findViewById(R.id.card_Def);
            term = itemView.findViewById(R.id.card_Term);
            parentLayout = itemView.findViewById(R.id.parent_layout);

        }
    }
}









