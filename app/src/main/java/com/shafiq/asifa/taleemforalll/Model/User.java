package com.shafiq.asifa.taleemforalll.Model;

public class User {


    private String id;
    private String userEmail;
    private String userPassword;
    private String username;
    private String status;
    private String search;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User(String id, String userEmail, String userPassword, String username, String status,String search) {
        this.id = id;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.username = username;
        this.status = status;
        this.search = search;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
