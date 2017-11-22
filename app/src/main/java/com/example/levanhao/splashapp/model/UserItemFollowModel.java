package com.example.levanhao.splashapp.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vokhuyet on 22/07/2016.
 */
public class UserItemFollowModel {
    private int id;
    private String username;
    private String avatar;
    private int followed;

    public UserItemFollowModel() {
    }

    public UserItemFollowModel(int id, String username, String avatar, int followed) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.followed = followed;
    }

    public UserItemFollowModel(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.username = jsonObject.getString("username");
            this.avatar = jsonObject.getString("avatar");
            this.followed = jsonObject.getInt("followed");
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getFollowed() {
        return followed;
    }

    public void setFollowed(int followed) {
        this.followed = followed;
    }
}
