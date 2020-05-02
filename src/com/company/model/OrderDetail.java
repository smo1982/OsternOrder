package com.company.model;

import java.util.ArrayList;

public class OrderDetail {
    int id;
    int menuId;
    int orderId;
    ArrayList<OrderChange> orderChanges = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ArrayList<OrderChange> getOrderChanges() {
        return orderChanges;
    }

    public void setOrderChanges(ArrayList<OrderChange> orderChanges) {
        this.orderChanges = orderChanges;
    }

}
