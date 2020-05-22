package com.example.gmisproject;

import java.util.ArrayList;

public class UsersModel {

    private String username, email, type, rate, report, tokenId;
    private ArrayList<Integer> Bins;

    public UsersModel() {
    }

    public UsersModel(String username, String email, String type, String rate, String report) {
        this.username = username;
        this.email = email;
        this.type = type;
        this.rate = rate;
        this.report = report;
    }

    public UsersModel(String username, String email, String rate, String report) {
        this.username = username;
        this.email = email;
        this.rate = rate;
        this.report = report;
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

    public UsersModel(String username, String email, String type, String tokenId, ArrayList<Integer> bins) {
        this.username = username;
        this.email = email;
        this.type = type;
        this.tokenId = tokenId;
        Bins = bins;
    }

    public ArrayList<Integer> getBins() {
        return Bins;
    }
}
