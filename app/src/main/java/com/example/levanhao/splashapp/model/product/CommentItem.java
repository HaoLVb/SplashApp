package com.example.levanhao.splashapp.model.product;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by HaoLV on 11/13/2017.
 */

public class CommentItem {
    private int id;
    private String comment;
    private String created_at;
    private Seller poster;

    public CommentItem(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.comment = jsonObject.getString("comment");
            this.created_at = jsonObject.getString("created_at");
            this.poster = new Seller(jsonObject.getJSONObject("poster"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public CommentItem(int id, String comment, String created_at, Seller poster) {
        this.id = id;
        this.comment = comment;
        this.created_at = created_at;
        this.poster = poster;
    }

    public CommentItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Seller getPoster() {
        return poster;
    }

    public void setPoster(Seller poster) {
        this.poster = poster;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "id='" + id + '\'' +
                ", comment='" + comment + '\'' +
                ", created_at='" + created_at + '\'' +
                ", poster=" + poster +
                '}';
    }
}
