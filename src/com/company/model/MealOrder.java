package com.company.model;

import java.util.ArrayList;

public class MealOrder {
    int id;
    int idCustomer;
    double PriceFinish;
    int idDeliveryPrice;
    double PriceDelivery;
    int reductionId;
    ArrayList<OrderDetail> orderDetails = new ArrayList<>();

    private DatabaseConnector connector;

    public MealOrder() {
        this.connector = DatabaseConnector.getInstance();
    }

    public int getReductionId() {
        return reductionId;
    }

    public void setReductionId(int reductionId) {
        this.reductionId = reductionId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public double getPriceFinish() {
        return PriceFinish;
    }

    public void setPriceFinish(double priceFinish) {
        PriceFinish =  PriceFinish + priceFinish;
    }

    public double getPriceDelivery() {
        return PriceDelivery;
    }

    public void setPriceDelivery(double priceDelivery) {
        PriceDelivery = priceDelivery;
    }

    public ArrayList<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public int getIdDeliveryPrice() {
        return idDeliveryPrice;
    }

    public void setIdDeliveryPrice(int idDeliveryPrice) {
        this.idDeliveryPrice = idDeliveryPrice;
    }

}
