package com.example.gmisproject;

public class EmpBin {


    private int binPercentage;
    private String binID;
    private String binStatus;
    private String clientAddress;

    public EmpBin(String binID, int binPercentage, String clientAddress, String binStatus) {
        this.binPercentage = binPercentage;
        this.binID = binID;
        this.binStatus = binStatus;
        this.clientAddress = clientAddress;
    }


    public int getBinPercentage() {
        return binPercentage;
    }

    public void setBinPercentage(int binPercentage) {
        this.binPercentage = binPercentage;
    }

    public String getBinID() {
        return binID;
    }

    public void setBinID(String binID) {
        this.binID = binID;
    }

    public String getBinStatus() {
        return binStatus;
    }

    public void setBinStatus(String binStatus) {
        this.binStatus = binStatus;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(String clientAddress) {
        this.clientAddress = clientAddress;
    }
}