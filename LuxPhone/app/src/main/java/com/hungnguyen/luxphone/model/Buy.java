package com.hungnguyen.luxphone.model;

public class Buy {
    private String productName;
    private String productImageLink;
    private String productPrice;
    private String productQuantity;

    public Buy() {
    }

    public Buy(String productName, String productImageLink, String productPrice, String productQuantity) {
        this.productName = productName;
        this.productImageLink = productImageLink;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImageLink() {
        return productImageLink;
    }

    public void setProductImageLink(String productImageLink) {
        this.productImageLink = productImageLink;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }
}
