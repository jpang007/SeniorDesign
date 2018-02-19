package com.example.along002.testingfinal.Utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.along002.testingfinal.R;

import java.util.ArrayList;

/**
 * Created by along002 on 2/16/2018.
 */

public class MakeSetRecyclerViewAdapter extends RecyclerView.Adapter<MakeSetRecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "MakeSetRecyclerViewAdap";
    private Context mContext;
    private int mSetSize;
    private ArrayList<String> mTermList = new ArrayList<>();
    private ArrayList<String> mDefList = new ArrayList<>();
    private ArrayList<String> mCardNum = new ArrayList<>();

    public MakeSetRecyclerViewAdapter(Context mContext, ArrayList<String> mTermList, ArrayList<String> mDefList, ArrayList<String> mCardNum) {
        this.mContext = mContext;
        this.mTermList = mTermList;
        this.mDefList = mDefList;
        this.mCardNum = mCardNum;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_make_aset, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.def.setTag(position);
        holder.def.setText(mDefList.get(position));

        holder.term.setTag(position);
        holder.term.setText(mTermList.get(position));
    }

    public ArrayList<String> getmDefList() {
        return mDefList;
    }

    public ArrayList<String> getmTermList(){ return mTermList;}

    public void removeItem(int position){
        mTermList.remove(position);
        mDefList.remove(position);
        mCardNum.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return mCardNum.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        EditText def;
        EditText term;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            def = itemView.findViewById(R.id.defEditText);
            term = itemView.findViewById(R.id.termEditText);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            def.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    int position = (int)def.getTag();
                    mDefList.set(position,s.toString());
                }
            });

            term.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    int position = (int)term.getTag();
                    mTermList.set(position,s.toString());
                }
            });

        }
    }
}
