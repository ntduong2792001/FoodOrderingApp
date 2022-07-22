package com.example.foodorderingapp.Models;

public class Order {
    int orderId;
    String email;
    double price;
    String address;

    public Order() {
    }

    public Order(int orderId, String email, double price, String address) {
        this.orderId = orderId;
        this.email = email;
        this.price = price;
        this.address = address;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
