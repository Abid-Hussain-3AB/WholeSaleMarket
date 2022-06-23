package com.example.myapplication.DataClasses;

public class BuyerAddressDataClass {
    String name;
    String phone;
    String province;
    String city;
    String address;

    public BuyerAddressDataClass(String name, String phone, String province, String city, String address) {
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.address = address;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public BuyerAddressDataClass() {
    }
}
