package com.example.along002.testingfinal;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by along002 on 1/27/2018.
 */

public class FlashcardInfo implements Serializable {
    private String Creator, Name, Privacy, Size, Id, Author;
    private ArrayList<String> TagList;
    private ArrayList<String> TermList;
    private ArrayList<String> DefinitionList;

    public FlashcardInfo(){
        super();
    }
//
//    public FlashcardInfo(Parcel parcel){
//        Creator = parcel.readString();
//        Name = parcel.readString();
//        Privacy = parcel.readString();
//        Size = parcel.readString();
//        Id = parcel.readString();
//        Author = parcel.readString();
//        TagList = parcel.createStringArrayList();
//        TermList = parcel.createStringArrayList();
//        DefinitionList = parcel.createStringArrayList();
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeString(Creator);
//        dest.writeString(Name);
//        dest.writeString(Privacy);
//        dest.writeString(Size);
//        dest.writeString(Id);
//        dest.writeString(Author);
//        dest.writeStringList(TagList);
//        dest.writeStringList(TermList);
//        dest.writeStringList(DefinitionList);
//    }
//    /** Static field used to regenerate object, individually or as arrays */
//    public static final Creator<FlashcardInfo> CREATOR=new Creator<FlashcardInfo>() {
//        @Override
//        public FlashcardInfo createFromParcel(Parcel source) {
//            return new FlashcardInfo(source);
//        }
//
//        @Override
//        public FlashcardInfo[] newArray(int i) {
//            return new FlashcardInfo[i];
//        }
//    };

    public ArrayList<String> getDefinitionList() {
        return DefinitionList;
    }

    public void setDefinitionList(ArrayList<String> definitionList) {
        DefinitionList = definitionList;
    }

    public ArrayList<String> getTermList() {
        return TermList;
    }

    public void setTermList(ArrayList<String> termList) {
        TermList = termList;
    }

    public ArrayList<String> getTagList() {
        return TagList;
    }

    public void setTagList(ArrayList<String> tagList) {
        TagList = tagList;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getPrivacy() {
        return Privacy;
    }

    public void setPrivacy(String privacy) {
        Privacy = privacy;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCreator() {
        return Creator;
    }

    public void setCreator(String creator) {
        Creator = creator;
    }
}
