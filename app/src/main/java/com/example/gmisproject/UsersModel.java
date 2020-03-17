package com.example.gmisproject;

import java.util.ArrayList;

public class UsersModel {

    private String username, email, type;
    private ArrayList<Integer> Bins;

    public UsersModel() {
    }


    public UsersModel(String email, String username, String type) {
        this.username = username;
        this.email = email;
        this.type = type;
    }


    public UsersModel(String username, String email, String type, ArrayList<Integer> bins) {
        this.username = username;
        this.email = email;
        this.type = type;
        Bins = bins;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }

    public ArrayList<Integer> getBins() {
        return Bins;
    }
}
