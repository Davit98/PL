package com.davitmartirosyan.pl.db.entity;

import com.google.gson.annotations.SerializedName;


public class Product {

    @SerializedName("product_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private int price;

    @SerializedName("image")
    private String image;

    @SerializedName("description")
    private String description;

    public Product() {
    }

    public Product(String id, String name, int price, String image, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new StringBuilder().
                append("Id: ").append(getId()).append("\n").
                append("Name: ").append(getName()).append("\n").
                append("Price: ").append(getPrice()).append("\n").
                append("Image: ").append(getImage()).append("\n").
                toString();
    }
}
