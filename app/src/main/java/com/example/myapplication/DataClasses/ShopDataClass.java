package com.example.myapplication.DataClasses;

public class ShopDataClass {
    public String shopName;
    public String shopType;
    public String shopCity;
    public String shopAddress;
    public String shopLocation;
    public String shopId;
    public String shopImage;

    public ShopDataClass(String shopName, String shopType, String shopCity, String shopAddress, String shopLocation, String shopId, String shopImage) {
        this.shopName = shopName;
        this.shopType = shopType;
        this.shopCity = shopCity;
        this.shopAddress = shopAddress;
        this.shopLocation = shopLocation;
        this.shopId = shopId;
        this.shopImage = shopImage;
    }

    public String getShopLocation() {
        return shopLocation;
    }

    public void setShopLocation(String shopLocation) {
        this.shopLocation = shopLocation;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopImage() {
        return shopImage;
    }

    public void setShopImage(String shopImage) {
        this.shopImage = shopImage;
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
