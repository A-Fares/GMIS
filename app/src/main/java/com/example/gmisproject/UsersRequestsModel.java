package com.example.gmisproject;

public class UsersRequestsModel {

    private String full_name;
    private String email;
    private String address;
    private String bin_number;
    private String phone_number;
    private String payment;

    public UsersRequestsModel(String full_name, String email, String address, String bin_number, String phone_number, String payment) {
        this.full_name = full_name;
        this.email = email;
        this.address = address;
        this.bin_number = bin_number;
        this.phone_number = phone_number;
        this.payment = payment;
    }

    public UsersRequestsModel() {
    }


    public UsersRequestsModel(String full_name, String email, String address, String bin_number, String phone_number) {
        this.full_name = full_name;
        this.email = email;
        this.address = address;
        this.bin_number = bin_number;
        this.phone_number = phone_number;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String type) {
        this.payment = type;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBin_number() {
        return bin_number;
    }

    public void setBin_number(String bin_number) {
        this.bin_number = bin_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}