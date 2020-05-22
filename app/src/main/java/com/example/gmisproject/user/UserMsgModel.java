package com.example.gmisproject.user;

//update attributes
public class UserMsgModel {

    public static final int REQUEST_RESPONSE = 0;
    public static final int COMPLAINING_RESPONSE = 1;


    private String Message, Tel, Cost, ComplaintMsg, address, contact_number, id;
    private int viewType;

    public UserMsgModel(int viewType, String Message, String Tel, String Cost) {
        this.Message = Message;
        this.Tel = Tel;
        this.Cost = Cost;
        this.viewType = viewType;
    }

    public UserMsgModel(int viewType, String ComplaintMsg) {
        this.ComplaintMsg = ComplaintMsg;
        this.viewType = viewType;
    }

    public UserMsgModel(String message, String tel, String cost, String complaintMsg, String address, String contact_number, int viewType) {
        Message = message;
        Tel = tel;
        Cost = cost;
        ComplaintMsg = complaintMsg;
        this.address = address;
        this.contact_number = contact_number;
        this.viewType = viewType;
    }

    public UserMsgModel(String Message, String Tel, String Cost, String ComplaintMsg, String address, String contact_number) {
        this.Message = Message;
        this.Tel = Tel;
        this.Cost = Cost;
        this.ComplaintMsg = ComplaintMsg;
        this.address = address;
        this.contact_number = contact_number;
    }


    public UserMsgModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String usename) {
        this.id = usename;
    }

    public String getMessage() {
        return Message;
    }


    public String getTel() {
        return Tel;
    }


    public String getCost() {
        return Cost;
    }


    public String getComplaintMsg() {
        return ComplaintMsg;
    }


    public int getViewType() {
        return viewType;
    }

}
