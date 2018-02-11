package com.example.along002.testingfinal;

import java.util.ArrayList;

/**
 * Created by along002 on 1/27/2018.
 */

public class FlashcardInfo {
    private String Creator, Name, Privacy, Size, Tags, Id, Author;
    private ArrayList<String> Tag = new ArrayList<>();

    FlashcardInfo(){
    }

    public ArrayList<String> getTag() {
        return Tag;
    }

    public void setTag(ArrayList<String> tag) {
        Tag = tag;
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
