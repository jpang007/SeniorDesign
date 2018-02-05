package com.example.along002.testingfinal;

/**
 * Created by along002 on 1/27/2018.
 */

public class FlashcardInfo {
    private String Creator, Name, Privacy, Size, Tags, Id;

    FlashcardInfo(){
    }

    public String getTags() {
        return Tags;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setTags(String tags) {
        Tags = tags;
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
