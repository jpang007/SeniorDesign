package com.example.along002.testingfinal.Utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
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
import java.util.HashMap;

/**
 * Created by along002 on 2/26/2018.
 */

public class MatchRecyclerAdapter extends RecyclerView.Adapter<MatchRecyclerAdapter.ViewHolder> {
    private static final String TAG = "MatchRecyclerAdapter";
    private RecyclerView mRecyclerView;
    private Context mContext;
    private ArrayList<String> mTermList;
    private ArrayList<String> mDefList;
    private ArrayList<String> mShuffledCards;
    private Integer[] mIndexArr;
    private HashMap<String, Integer> mTermMap = new HashMap<>();
    private HashMap<String, Integer> mDefMap = new HashMap<>();
    private int firstPosition = 0;
    private String firstString = "";
    private String secondString = "";
    private int prevPosition = 0;
    private ArrayList<Boolean> mSolved = new ArrayList<>();


    public MatchRecyclerAdapter(Context mContext, ArrayList<String> mTermList, ArrayList<String> mDefList,
                                ArrayList<String> mShuffledCards, Integer[] mIndexArr, HashMap<String, Integer> mTermMap,
                                HashMap<String, Integer> mDefMap, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mTermList = mTermList;
        this.mDefList = mDefList;
        this.mShuffledCards = mShuffledCards;
        this.mIndexArr = mIndexArr;
        this.mTermMap = mTermMap;
        this.mDefMap = mDefMap;
        this.mRecyclerView = mRecyclerView;
        for (int i = 0; i < mShuffledCards.size(); i++){
            mSolved.add(false);
        }

    }

    @Override
    public MatchRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_match_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MatchRecyclerAdapter.ViewHolder holder, final int position) {

        holder.matchTextView.setText(mShuffledCards.get(position));
        if (mSolved.get(holder.getAdapterPosition()) == true){
            holder.matchTextView.setText("");
//            holder.cardView.setBackgroundResource(R.drawable.black_border_card_view);
            Log.d(TAG, "onBindViewHolder: " + Integer.toString(position));
        }
        else {
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!firstString.equals("") && firstPosition != position) {
                        secondString = mShuffledCards.get(position);

                        int tempTerm;
                        int tempDef;

                        if (mTermMap.get(firstString) != null) {
                            tempTerm = mTermMap.get(firstString);
                            if (mTermList.get(tempTerm).equals(secondString)) {
                                Toast.makeText(mContext, "Match!", Toast.LENGTH_SHORT).show();
                                mSolved.set(position, true);
                                mSolved.set(firstPosition, true);
                                holder.matchTextView.setText("");
                                if (mRecyclerView.findViewHolderForAdapterPosition(firstPosition) != null){
                                    View view = mRecyclerView.findViewHolderForAdapterPosition(firstPosition).itemView;
                                    RelativeLayout parent_layout = (RelativeLayout) view.findViewById(R.id.parent_layout);
                                    parent_layout.setBackgroundResource(R.drawable.black_border_card_view);
                                    TextView matchTextView = view.findViewById(R.id.matchTextView);
                                    matchTextView.setText("");
                                }
                            }
                            else {
//                            Toast.makeText(mContext, "Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (mDefMap.get(firstString) != null) {
                            tempDef = mDefMap.get(firstString);
                            if (mDefList.get(tempDef).equals(secondString)) {
                                Toast.makeText(mContext, "Match!", Toast.LENGTH_SHORT).show();
                                mSolved.set(position, true);
                                mSolved.set(firstPosition, true);
                                holder.matchTextView.setText("");
                                if (mRecyclerView.findViewHolderForAdapterPosition(firstPosition) != null){
                                    View view = mRecyclerView.findViewHolderForAdapterPosition(firstPosition).itemView;
                                    RelativeLayout parent_layout = (RelativeLayout) view.findViewById(R.id.parent_layout);
                                    parent_layout.setBackgroundResource(R.drawable.black_border_card_view);
                                    TextView matchTextView = view.findViewById(R.id.matchTextView);
                                    matchTextView.setText("");
                                }
                            }
                            else {
//                            Toast.makeText(mContext, "Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }

                        firstString = "";

                    } else {
                        firstString = mShuffledCards.get(position);
                        firstPosition = position;
                        Toast.makeText(mContext, firstString, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
        return 0;

    }

    @Override
    public int getItemCount() {
        return mShuffledCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView matchTextView;
        RelativeLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            matchTextView = itemView.findViewById(R.id.matchTextView);
            parent_layout = itemView.findViewById(R.id.parent_layout);

        }
    }

}
