package com.example.levanhao.splashapp.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by HaoLV on 11/7/2017.
 */

public class UserInformationModel implements Serializable {
    private int id;
    private String username;
    private String token;
    private String avatar;

    public UserInformationModel() {
    }

    public UserInformationModel(int id, String username, String token, String avatar) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.avatar = avatar;
    }

    public UserInformationModel(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.username = jsonObject.getString("username");
            this.token = jsonObject.getString("token");
            this.avatar = jsonObject.getString("avatar");
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
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
        return "UserInformationModel{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", token='" + token + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
