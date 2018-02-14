package com.example.along002.testingfinal;

import java.util.ArrayList;

/**
 * Created by along002 on 1/27/2018.
 */

public class FlashcardInfo {
    private String Creator, Name, Privacy, Size, Id, Author;
    private ArrayList<String> TagList = new ArrayList<>();
    private ArrayList<String> TermList = new ArrayList<>();
    private ArrayList<String> DefinitionList;

    FlashcardInfo(){
    }

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
