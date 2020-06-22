package com.hungnguyen.luxphone.model;

public class OrderDetail {
    private String OrderID;
    private String ProductName;
    private String Quantity;

    public OrderDetail(String orderID, String productName, String quantity) {
        OrderID = orderID;
        ProductName = productName;
        Quantity = quantity;
    }

    public OrderDetail() {
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }
}
