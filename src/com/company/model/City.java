package com.company.model;

public class City {
    int id;
    String cityName;
    double deliveryPrice;
    int deliveryId;

    public City(int id, String cityName, double deliveryPrice, int deliveryId) {
        this.id = id;
        this.cityName = cityName;
        this.deliveryPrice = deliveryPrice;
        this.deliveryId = deliveryId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }
}

