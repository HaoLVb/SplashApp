package com.example.levanhao.splashapp.model;

import java.io.Serializable;

/**
 * Created by HaoVanLe on 10/29/2017.
 */

public class ExhibitItem implements Serializable{
    private String name;
    private int id;

    public ExhibitItem() {
    }

    public ExhibitItem(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
