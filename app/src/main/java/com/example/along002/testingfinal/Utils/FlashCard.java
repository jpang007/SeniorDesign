package com.example.along002.testingfinal.Utils;

/**
 * Created by along002 on 1/27/2018.
 */

public class FlashCard {
    private String Definition;
    private String Term;

    FlashCard(){
    }

    public String getTerm() {
        return Term;
    }

    public void setTerm(String term) {
        Term = term;
    }

    public String getDefinition() {
        return Definition;
    }

    public void setDefinition(String definition) {
        Definition = definition;
    }
}
