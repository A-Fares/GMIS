package com.example.gmisproject;

public class Users {

    private String username;
    private String email;
    private String password;
    private String type;

    public Users() {
    }

    public Users(String username, String email, String type) {
        this.username = username;
        this.email = email;
        this.type = type;
    }

    public Users(String username, String email, String password, String type) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
