package com.example.levanhao.splashapp.model.product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by thanglv on 8/16/2016.
 */
public class Image implements Serializable {
    private int id;
    private String url;

    public Image(int id, String url) {
        this.id = id;
        this.url = url;

    }

    public Image(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.url = jsonObject.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Image() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
