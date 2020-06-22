package com.hungnguyen.luxphone.model;

import androidx.annotation.Nullable;

public class Product {
    private String company, imagelink, name, price;

    public Product() {
    }

    public Product(@Nullable String company, @Nullable String imagelink, @Nullable String name, @Nullable String price) {
        this.company = company;
        this.imagelink = imagelink;
        this.name = name;
        this.price = price;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
