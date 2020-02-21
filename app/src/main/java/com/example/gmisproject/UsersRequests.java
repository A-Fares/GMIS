package com.example.gmisproject;

public class UsersRequests {

    private String full_name ;
    private String email ;
    private String address ;
    private String home_number ;
    private String phone_number ;

    public UsersRequests() {
    }

    public UsersRequests(String full_name, String email, String address, String home_number, String phone_number) {
        this.full_name = full_name;
        this.email = email;
        this.address = address;
        this.home_number = home_number;
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

    public void setHome_number(String home_number) {
        this.home_number = home_number;
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

    public String getHome_number() {
        return home_number;
    }

    public String getPhone_number() {
        return phone_number;
    }
}
