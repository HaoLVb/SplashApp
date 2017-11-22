package com.example.levanhao.splashapp.model;

/**
 * Created by thanglv on 7/2/2016.
 */
public class LoginInfo {
    private String userName;
    private String phoneNumber;
    private String password;
    private String token;
    private String avatar;

    public LoginInfo(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }

    public LoginInfo(){}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
