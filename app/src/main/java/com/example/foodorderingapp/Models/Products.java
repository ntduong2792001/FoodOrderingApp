package com.example.foodorderingapp.Models;

import java.io.Serializable;

public class Products implements Serializable {
    int categoryId;
    String productDescription;
    int productId;
    int productImageUrl;
    String productName;
    int productPrice;
    int productQuantity;


    public Products() {
    }

    public Products(int categoryId, String productDescription, int productId, int productImageUrl, String productName, int productPrice, int productQuantity) {
        this.categoryId = categoryId;
        this.productDescription = productDescription;
        this.productId = productId;
        this.productImageUrl = productImageUrl;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductImageUrl() {
        return productImageUrl;
    }

    public void setProductImageUrl(int productImageUrl) {
        this.productImageUrl = productImageUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "Products{" +
                "categoryId=" + categoryId +
                ", productDescription='" + productDescription + '\'' +
                ", productId=" + productId +
                ", productImageUrl=" + productImageUrl +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productQuantity=" + productQuantity +
                '}';
    }
}
