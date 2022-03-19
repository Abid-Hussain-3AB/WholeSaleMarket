package com.example.myapplication.DataClasses;

import android.net.Uri;

public class ProductDataClass {

    public String productName;
    public String productType;
    public String productQuantity;
    public String productId;
    public String image;

    public ProductDataClass(String productName, String productType, String productQuantity, String productId, String image) {
        this.productName = productName;
        this.productType = productType;
        this.productQuantity = productQuantity;
        this.productId = productId;
        this.image = image;
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
