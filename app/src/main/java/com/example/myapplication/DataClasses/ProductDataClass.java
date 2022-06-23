package com.example.myapplication.DataClasses;

import android.net.Uri;

public class ProductDataClass {

    public String productName;
    public String productType;
    public String productCompany;
    public String productPrice;
    public String productMinSale;
    public String productMaxSale;
    public String productQuantity;
    public String productDetail;
    public String productId;
    public String productShopId;
    public String image;


    public ProductDataClass(String productName, String productType, String productCompany, String productPrice, String productMinSale, String productMaxSale, String productQuantity, String productDetail, String productId, String productShopId, String image) {
        this.productName = productName;
        this.productType = productType;
        this.productCompany = productCompany;
        this.productPrice = productPrice;
        this.productMinSale = productMinSale;
        this.productMaxSale = productMaxSale;
        this.productQuantity = productQuantity;
        this.productDetail = productDetail;
        this.productId = productId;
        this.productShopId = productShopId;
        this.image = image;
    }

    public String getProductShopId() {
        return productShopId;
    }

    public void setProductShopId(String productShopId) {
        this.productShopId = productShopId;
    }

    public String getProductCompany() {
        return productCompany;
    }

    public void setProductCompany(String productCompany) {
        this.productCompany = productCompany;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductMinSale() {
        return productMinSale;
    }

    public void setProductMinSale(String productMinSale) {
        this.productMinSale = productMinSale;
    }

    public String getProductMaxSale() {
        return productMaxSale;
    }

    public void setProductMaxSale(String productMaxSale) {
        this.productMaxSale = productMaxSale;
    }

    public String getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public ProductDataClass() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
