package com.example.levanhao.splashapp.model;

/**
 * Created by haova on 10/23/2017.
 */

public class StatusItem {
    private String name;
    private String detail;

    public StatusItem(String name, String detail) {
        this.name = name;
        this.detail = detail;
    }

    public StatusItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
