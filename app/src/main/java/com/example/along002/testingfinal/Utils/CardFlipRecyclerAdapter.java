package com.example.along002.testingfinal.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.along002.testingfinal.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;

/**
 * Created by along002 on 2/21/2018.
 */

public class CardFlipRecyclerAdapter extends RecyclerView.Adapter<CardFlipRecyclerAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mTermList = new ArrayList<>();
    private ArrayList<String> mDefList = new ArrayList<>();

    public CardFlipRecyclerAdapter(Context mContext, ArrayList<String> mTermList, ArrayList<String> mDefList) {
        this.mContext = mContext;
        this.mTermList = mTermList;
        this.mDefList = mDefList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_flip_preview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.frontView.setText(mTermList.get(position));
        holder.backView.setText(mDefList.get(position));
    }

    @Override
    public int getItemCount() {
        return mTermList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView frontView, backView;
        RelativeLayout parentLayout;
        EasyFlipView flipView;

        public ViewHolder(View itemView){
            super(itemView);
            frontView = itemView.findViewById(R.id.frontView);
            backView = itemView.findViewById(R.id.backView);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            flipView = itemView.findViewById(R.id.easyFlipView);
//            flipView.setFlipDuration(0);
//            flipView.flipTheView();
            flipView.setFlipDuration(500);



        }
    }

}
