package com.example.levanhao.splashapp.model.product;

import java.io.Serializable;

/**
 * Created by HaoLV on 11/8/2017.
 */

public class Video implements Serializable {
    private String url;
    private String thumb;

    public Video() {
    }

    public Video(String url, String thumb) {
        this.url = url;
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
