package com.example.myapplication.DataClasses;

public class ShopDataClass {
    public String shopName;
    public String shopType;
    public String shopCity;
    public String shopId;

    public ShopDataClass(String shopName, String shopType, String shopCity, String shopId) {
        this.shopName = shopName;
        this.shopType = shopType;
        this.shopCity = shopCity;
        this.shopId = shopId;
    }

    public ShopDataClass() {
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopCity() {
        return shopCity;
    }

    public void setShopCity(String shopCity) {
        this.shopCity = shopCity;
    }
}
