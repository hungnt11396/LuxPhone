package com.hungnguyen.luxphone.model;

public class Order {
    private String orderID;
    private String customerName;
    private String customerPhone;
    private String dateBuy;

    public Order() {
    }

    public Order(String orderID, String customerName, String customerPhone, String datebuy) {
        this.orderID = orderID;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.dateBuy = datebuy;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(String dateBuy) {
        this.dateBuy = dateBuy;
    }
}
