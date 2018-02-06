package com.example.along002.testingfinal.Utils;

/**
 * Created by along002 on 2/6/2018.
 */

public class FlashcardDisplay {
    private String author;
    private String setName;
    private String setSize;

    public FlashcardDisplay(String author, String setName, String setSize) {
        this.author = author;
        this.setName = setName;
        this.setSize = setSize;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public String getSetSize() {
        return setSize;
    }

    public void setSetSize(String setSize) {
        this.setSize = setSize;
    }
}
