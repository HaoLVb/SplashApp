package com.example.levanhao.splashapp.model.product;

import java.io.Serializable;

/**
 * Created by HaoLV on 11/8/2017.
 */

public class Size  implements Serializable{
    private String id;
    private String size_name;

    public Size(String id, String size_name) {
        this.id = id;
        this.size_name = size_name;
    }

    public Size() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSize_name() {
        return size_name;
    }

    public void setSize_name(String size_name) {
        this.size_name = size_name;
    }
}
