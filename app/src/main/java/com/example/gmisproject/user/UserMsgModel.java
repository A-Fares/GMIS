package com.example.gmisproject.user;

public class UserMsgModel {
    private String msg,phone,costs;

    public UserMsgModel(String msg, String phone, String costs) {
        this.msg = msg;
        this.phone = phone;
        this.costs = costs;
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
}
