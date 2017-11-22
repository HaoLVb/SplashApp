package com.example.levanhao.splashapp.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vokhuyet on 28/07/2016.
 */

//class thông tin trang cá nhân của user_id
public class UserInfoPage {
    private String id;
    private String username;
    private String url;
    private String status;
    private String avatar;
    private String address;
    private String city;
    private String followed;
    private String is_blocked;
    private String default_address;


    public UserInfoPage() {
    }

    public UserInfoPage(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("id");
            this.username = jsonObject.getString("username");
            this.url = jsonObject.getString("url");
            this.status = jsonObject.getString("status");
            this.avatar = jsonObject.getString("avatar");
            this.address = jsonObject.getString("address");
            this.city = jsonObject.getString("city");
            this.followed = jsonObject.getString("followed");
            this.is_blocked = jsonObject.getString("is_blocked");
            this.status = jsonObject.getString("default_address");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public UserInfoPage(String id, String username, String url, String status, String avatar, String address, String city, String followed, String is_blocked, String default_address) {
        this.id = id;
        this.username = username;
        this.url = url;
        this.status = status;
        this.avatar = avatar;
        this.address = address;
        this.city = city;
        this.followed = followed;
        this.is_blocked = is_blocked;
        this.default_address = default_address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public String getFollowed() {
        return followed;
    }

    public void setFollowed(String followed) {
        this.followed = followed;
    }

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }

    public String getDefault_address() {
        return default_address;
    }

    public void setDefault_address(String default_address) {
        this.default_address = default_address;
    }

    @Override
    public String toString() {
        return "UserInfoPage{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                ", avatar='" + avatar + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", followed='" + followed + '\'' +
                ", is_blocked='" + is_blocked + '\'' +
                ", default_address='" + default_address + '\'' +
                '}';
    }
}
