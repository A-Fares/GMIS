package com.example.gmisproject;

public class UsersRequests {

    private String full_name ;
    private String email ;
    private String address ;
    private String bin_number ;
    private String phone_number ;

    public UsersRequests() {
    }

    public UsersRequests(String full_name, String email, String address, String bin_number, String phone_number) {
        this.full_name = full_name;
        this.email = email;
        this.address = address;
        this.bin_number = bin_number;
        this.phone_number = phone_number;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBin_number(String bin_number) {
        this.bin_number = bin_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getBin_number() {
        return bin_number;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
