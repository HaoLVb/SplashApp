package com.example.levanhao.splashapp.model.product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by thanglv on 8/16/2016.
 */
public class Seller implements Serializable {
    private int id;
    private String name;
    private String phonenumber;
    private String password;
    private String avatar;
    private String role;
    private boolean status;
    private String email;
    private String address;
    private String city;
    private int score;
    private int listing;


    public Seller(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
            this.phonenumber = jsonObject.getString("phonenumber");
            this.password = jsonObject.getString("password");
            this.avatar = jsonObject.getString("avatar");
            this.role = jsonObject.getString("role");
            this.status = jsonObject.getBoolean("status");
            this.email = jsonObject.getString("email");
            this.address = jsonObject.getString("address");
            this.city = jsonObject.getString("city");
            this.score = jsonObject.getInt("score");
            this.listing = jsonObject.getInt("listing");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getListing() {
        return listing;
    }

    public void setListing(int listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", password='" + password + '\'' +
                ", avatar='" + avatar + '\'' +
                ", role='" + role + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", score='" + score + '\'' +
                ", listing='" + listing + '\'' +
                '}';
    }
}
