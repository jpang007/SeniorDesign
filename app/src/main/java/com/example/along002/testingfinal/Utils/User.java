package com.example.along002.testingfinal.Utils;

/**
 * Created by along002 on 1/27/2018.
 */

public class User {
    private String email;
    private String username;

    public User(){
    }

    public User(String email, String username) {
        this.email = email;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
