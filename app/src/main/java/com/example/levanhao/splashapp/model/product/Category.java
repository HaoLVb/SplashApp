package com.example.levanhao.splashapp.model.product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by thanglv on 8/16/2016.
 */
public class Category implements Serializable {
    private int id;
    private String name;
    private String hasBrand;
    private String hasName;

    public Category(int id, String name, String hasBrand, String hasName) {
        this.id = id;
        this.name = name;
        this.hasBrand = hasBrand;
        this.hasName = hasName;
    }

    public Category(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Category() {
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

    public String getHasBrand() {
        return hasBrand;
    }

    public void setHasBrand(String hasBrand) {
        this.hasBrand = hasBrand;
    }

    public String getHasName() {
        return hasName;
    }

    public void setHasName(String hasName) {
        this.hasName = hasName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hasBrand='" + hasBrand + '\'' +
                ", hasName='" + hasName + '\'' +
                '}';
    }
}
