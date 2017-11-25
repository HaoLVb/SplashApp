package com.example.levanhao.splashapp.model;

import android.util.Log;

import com.example.levanhao.splashapp.model.product.Brand;
import com.example.levanhao.splashapp.model.product.Category;
import com.example.levanhao.splashapp.model.product.Image;
import com.example.levanhao.splashapp.model.product.Seller;
import com.example.levanhao.splashapp.model.product.Size;
import com.example.levanhao.splashapp.model.product.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by LeVanHao on 10/6/2017.
 */

public class ProductItem implements Serializable {
    private int id;
    private String name;
    private int price;
    private int price_percent;
    private String described;
    private String ships_from;
    private ArrayList<String> ships_from_id;
    private String condition;
    private String created;
    private int like;
    private int number_comment;
    private boolean is_liked;
    private ArrayList<Image> images;
    private ArrayList<Video> videos;
    private ArrayList<Size> sizes;
    private ArrayList<Brand> brands;
    private Seller seller;
    private ArrayList<Category> categories;
    private String is_blocked;
    private String can_edit;
    private String banned;
    private String url;
    private String weight;
    private ArrayList<String> dimension;

    public ProductItem(int id, String name, int price, int price_percent, String described,
                       String ships_from, ArrayList<String> ships_from_id, String condition,
                       String created, int like, int number_comment, boolean is_liked, ArrayList<Image> images,
                       ArrayList<Video> videos, ArrayList<Size> sizes, ArrayList<Brand> brands, Seller sellers,
                       ArrayList<Category> categories, String is_blocked, String can_edit, String banned, String url,
                       String weight, ArrayList<String> dimension) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.price_percent = price_percent;
        this.described = described;
        this.ships_from = ships_from;
        this.ships_from_id = ships_from_id;
        this.condition = condition;
        this.created = created;
        this.like = like;
        this.number_comment = number_comment;
        this.is_liked = is_liked;
        this.images = images;
        this.videos = videos;
        this.sizes = sizes;
        this.brands = brands;
        this.seller = sellers;
        this.categories = categories;
        this.is_blocked = is_blocked;
        this.can_edit = can_edit;
        this.banned = banned;
        this.url = url;
        this.weight = weight;
        this.dimension = dimension;
    }

    public ProductItem(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getInt("id");
            this.name = jsonObject.getString("name");
            this.price = jsonObject.getInt("price");
            this.price_percent = jsonObject.getInt("price_percent");
            this.described = jsonObject.getString("described");
            this.like = jsonObject.getInt("like");
            this.number_comment = jsonObject.getInt("number_comment");
            this.seller = new Seller(jsonObject.getJSONObject("seller"));
            JSONArray imageArray = new JSONArray(jsonObject.getString("images"));
            this.images = new ArrayList<>();
            for (int i = 0; i < imageArray.length(); i++) {
                JSONObject object = imageArray.getJSONObject(i);
                Image image = new Image(object);
                images.add(image);
            }
            this.is_liked = jsonObject.getBoolean("is_liked");
            this.condition = jsonObject.getString("condition");
            this.ships_from = jsonObject.getString("ship_from");

            this.weight = jsonObject.getString("weight");
            this.dimension = new ArrayList<>();
            JSONArray dimensionArray = new JSONArray(jsonObject.getString("dimension"));
            for (int i = 0; i < dimensionArray.length(); i++) {
                String s = String.valueOf(dimensionArray.get(i));
                dimension.add(s);
            }
            this.categories = new ArrayList<>();
            Category category = new Category(jsonObject.getJSONObject("category"));
            categories.add(category);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ProductItem() {
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice_percent() {
        return price_percent;
    }

    public void setPrice_percent(int price_percent) {
        this.price_percent = price_percent;
    }

    public String getDescribed() {
        return described;
    }

    public void setDescribed(String described) {
        this.described = described;
    }

    public String getShips_from() {
        return ships_from;
    }

    public void setShips_from(String ships_from) {
        this.ships_from = ships_from;
    }

    public ArrayList<String> getShips_from_id() {
        return ships_from_id;
    }

    public void setShips_from_id(ArrayList<String> ships_from_id) {
        this.ships_from_id = ships_from_id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getNumber_comment() {
        return number_comment;
    }

    public void setNumber_comment(int number_comment) {
        this.number_comment = number_comment;
    }

    public boolean isIs_liked() {
        return is_liked;
    }

    public void setIs_liked(boolean is_liked) {
        this.is_liked = is_liked;
    }

    public ArrayList<Image> getImages() {
        return images;
    }

    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }

    public ArrayList<Size> getSizes() {
        return sizes;
    }

    public void setSizes(ArrayList<Size> sizes) {
        this.sizes = sizes;
    }

    public ArrayList<Brand> getBrands() {
        return brands;
    }

    public void setBrands(ArrayList<Brand> brands) {
        this.brands = brands;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public String getIs_blocked() {
        return is_blocked;
    }

    public void setIs_blocked(String is_blocked) {
        this.is_blocked = is_blocked;
    }

    public String getCan_edit() {
        return can_edit;
    }

    public void setCan_edit(String can_edit) {
        this.can_edit = can_edit;
    }

    public String getBanned() {
        return banned;
    }

    public void setBanned(String banned) {
        this.banned = banned;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public ArrayList<String> getDimension() {
        return dimension;
    }

    public void setDimension(ArrayList<String> dimension) {
        this.dimension = dimension;
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", price_percent=" + price_percent +
                ", described='" + described + '\'' +
                ", ships_from='" + ships_from + '\'' +
                ", ships_from_id=" + ships_from_id +
                ", condition='" + condition + '\'' +
                ", created='" + created + '\'' +
                ", like=" + like +
                ", number_comment=" + number_comment +
                ", is_liked=" + is_liked +
                ", images=" + images +
                ", videos=" + videos +
                ", sizes=" + sizes +
                ", brands=" + brands +
                ", seller=" + seller +
                ", categories=" + categories +
                ", is_blocked='" + is_blocked + '\'' +
                ", can_edit='" + can_edit + '\'' +
                ", banned='" + banned + '\'' +
                ", url='" + url + '\'' +
                ", weight='" + weight + '\'' +
                ", dimension=" + dimension +
                '}';
    }
}
