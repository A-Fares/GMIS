package com.example.gmisproject;

public class UsersRequestsModel {

    private String id, full_name,
            address, bin_number,
            phone_number, payment;

    public UsersRequestsModel(String id, String full_name, String address, String bin_number, String phone_number, String payment) {
        this.id = id;
        this.full_name = full_name;
        this.address = address;
        this.bin_number = bin_number;
        this.phone_number = phone_number;
        this.payment = payment;
    }

    public UsersRequestsModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}


