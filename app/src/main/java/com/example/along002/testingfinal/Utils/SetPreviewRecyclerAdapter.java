package com.example.along002.testingfinal.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;

import java.util.ArrayList;

/**
 * Created by along002 on 2/24/2018.
 */

public class SetPreviewRecyclerAdapter extends RecyclerView.Adapter<SetPreviewRecyclerAdapter.ViewHolder> {

    private OnItemClick mCallback;
    private Context mContext;
    private ArrayList<FlashcardInfo> mSetInfoList = new ArrayList<>();


    public SetPreviewRecyclerAdapter(Context mContext, ArrayList<FlashcardInfo> mSetInfoList, OnItemClick listener) {
        this.mContext = mContext;
        this.mSetInfoList = mSetInfoList;
        this.mCallback = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_set_preview, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.setName.setText(mSetInfoList.get(position).getName());
        holder.setSize.setText(mSetInfoList.get(position).getSize() + " cards");
        holder.setAuthor.setText(mSetInfoList.get(position).getAuthor());

        String tag = "";
        if(mSetInfoList.get(position).getTagList().size() == 1){
            tag = "Tag: ";
        }
        else {
            tag = "Tags: ";
        }
        for(int i = 0; i < mSetInfoList.get(position).getTagList().size(); i++){
            if((tag == "Tag: ") || (tag == "Tags: ")){
                tag = tag + mSetInfoList.get(position).getTagList().get(i);
            }
            else{
                tag = tag + ", " + mSetInfoList.get(position).getTagList().get(i);
            }
        }
        holder.setTags.setText(tag);

        holder.parent_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallback.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mSetInfoList.size();
    }

    public interface OnItemClick {
        void onClick(int value);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView setName, setAuthor, setSize, setTags;
        LinearLayout parent_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            setName = itemView.findViewById(R.id.setName);
            setAuthor = itemView.findViewById(R.id.setAuthor);
            setSize = itemView.findViewById(R.id.setSize);
            setTags = itemView.findViewById(R.id.setTags);
            parent_layout = itemView.findViewById(R.id.parent_layout);


        }
    }
}
