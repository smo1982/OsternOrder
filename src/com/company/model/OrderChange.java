package com.company.model;

public class OrderChange {
    int id;
    String change;
    int changePriceID = 0;
    int detailsID;

    public int getDetailsID() {
        return detailsID;
    }

    public void setDetailsID(int detailsID) {
        this.detailsID = detailsID;
    }

    public int getChangePriceID() {
        return changePriceID;
    }

    public void setChangePriceID(int changePriceID) {
        this.changePriceID = changePriceID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
