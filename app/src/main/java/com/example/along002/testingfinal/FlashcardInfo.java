package com.example.along002.testingfinal;

/**
 * Created by along002 on 1/27/2018.
 */

public class FlashcardInfo {
    private String Creator;
    private String Name;
    private String Privacy;
    private String Size;
    FlashcardInfo(){
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
