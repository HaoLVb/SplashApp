package com.example.levanhao.splashapp.model.product;

import java.io.Serializable;

/**
 * Created by thanglv on 8/16/2016.
 */
public class Brand implements Serializable {
    private String id;
    private String brand_name;

    public Brand(String id, String brand_name) {
        this.id = id;
        this.brand_name = brand_name;
    }

    public Brand() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }
}
