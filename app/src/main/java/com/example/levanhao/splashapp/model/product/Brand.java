package com.example.levanhao.splashapp.model.product;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by thanglv on 8/16/2016.
 */
public class Brand implements Serializable {
    private int id;
    private String brand_name;

    public Brand(int id, String brand_name) {
        this.id = id;
        this.brand_name = brand_name;
    }
    public Brand(JSONObject jsonObject){
        try {
            this.id=jsonObject.getInt("id");
            this.brand_name=jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Brand() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }
}
