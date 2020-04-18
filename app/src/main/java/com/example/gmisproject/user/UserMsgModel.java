package com.example.gmisproject.user;

public class UserMsgModel {

    public static final int REQUEST_RESPONSE=0;
    public static final int COMPLAINING_RESPONSE=1;


    private String msg, phone, costs, complaintMsg;
    private int viewType;

    public UserMsgModel(int viewType,String msg, String phone, String costs) {
        this.msg = msg;
        this.phone = phone;
        this.costs = costs;
        this.viewType=viewType;
    }
    public UserMsgModel(int viewType,String complaintMsg) {
        this.complaintMsg = complaintMsg;
        this.viewType=viewType;
    }


    public UserMsgModel() {
    }

    public String getMsg() {
        return msg;
    }

    public String getPhone() {
        return phone;
    }

    public String getCosts() {
        return costs;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCosts(String costs) {
        this.costs = costs;
    }

    public String getComplaintMsg() {
        return complaintMsg;
    }

    public void setComplaintMsg(String complaintMsg) {
        this.complaintMsg = complaintMsg;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
