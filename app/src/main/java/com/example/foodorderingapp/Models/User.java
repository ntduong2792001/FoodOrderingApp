package com.example.foodorderingapp.Models;

public class User {

    private String fullName,email,phone;
    private String imageUrl;

    public User(String fullName, String email, String phone, String imageUrl) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.imageUrl = imageUrl;
    }

    public User(){}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
