package com.example.gmisproject;

import java.util.ArrayList;

public class BinsModel {

    private String status, address, location;
    private int percentage,binId;

    public BinsModel() {
    }


    public BinsModel(String status, String address, String location, int percentage, int binId) {
        this.status = status;
        this.address = address;
        this.location = location;
        this.percentage = percentage;
        this.binId = binId;
    }


    public String getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public String getLocation() {
        return location;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getBinId() {
        return binId;
    }
}