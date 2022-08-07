package com.example.myapplication.DataClasses;

public class OrderDataClass {
    String orderId;
    String productId;
    String totalQuantity;
    String totalPrice;
    String shipmentPrice;
    String grandTotal;

    public OrderDataClass(String orderId, String productId, String totalQuantity, String totalPrice, String shipmentPrice, String grandTotal) {
        this.orderId = orderId;
        this.productId = productId;
        this.totalQuantity = totalQuantity;
        this.totalPrice = totalPrice;
        this.shipmentPrice = shipmentPrice;
        this.grandTotal = grandTotal;
    }

    public OrderDataClass() {
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getShipmentPrice() {
        return shipmentPrice;
    }

    public void setShipmentPrice(String shipmentPrice) {
        this.shipmentPrice = shipmentPrice;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }
}
