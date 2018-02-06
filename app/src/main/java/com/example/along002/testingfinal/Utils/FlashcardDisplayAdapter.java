package com.example.along002.testingfinal.Utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.along002.testingfinal.FlashcardInfo;
import com.example.along002.testingfinal.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by along002 on 2/6/2018.
 */

public class FlashcardDisplayAdapter extends ArrayAdapter<FlashcardInfo> {
    private static final String TAG = "FlashcardDisplayAdapter";

    private Context mContext;
    private int mResource;
    public FlashcardDisplayAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FlashcardInfo> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //get Flashcard Info
        String author = getItem(position).getAuthor();
        String name = getItem(position).getName();
        String size = getItem(position).getSize();

        //Create a new Flashcard Display
        FlashcardDisplay flashcardDisplay = new FlashcardDisplay(author,name,size);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView set_Size = (TextView) convertView.findViewById(R.id.set_Size);
        TextView set_Name = (TextView) convertView.findViewById(R.id.set_Name);
        TextView set_Author = (TextView) convertView.findViewById(R.id.set_Author);

        set_Size.setText(size + " cards");
        set_Name.setText(name);
        set_Author.setText(author);

        return convertView;
    }

}
