package com.example.gmisproject;

public class EmpBin {

    //Add the type of BIN indicators here
    public static final int PUBLIC_BIN = 0;
    public static final int PRIVATE_BIN = 1;

    private int viewType;
    private int binPercentage;
    private String binID;
    private String binStatus;
    private String clientAddress;
    private String clientPhone;

    public EmpBin(int viewType, int binPercentage, String binID, String binStatus, String clientAddress, String clientPhone) {
        this.viewType = viewType;
        this.binPercentage = binPercentage;
        this.binID = binID;
        this.binStatus = binStatus;
        this.clientAddress = clientAddress;
        this.clientPhone = clientPhone;
    }

    public EmpBin(int viewType, int binPercentage, String binID, String binStatus) {
        this.viewType = viewType;
        this.binPercentage = binPercentage;
        this.binID = binID;
        this.binStatus = binStatus;
    }

    public int getViewType() {
        return viewType;
    }

    public int getBinPercentage() {
        return binPercentage;
    }

    public String getBinID() {
        return binID;
    }

    public String getBinStatus() {
        return binStatus;
    }

    public String getClientAddress() {
        return clientAddress;
    }

    public String getClientPhone() {
        return clientPhone;
    }
}