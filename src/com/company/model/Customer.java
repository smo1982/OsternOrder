package com.company.model;

public class Customer {
    int idCustomer;
    String nameCustomer;
    String cityCustomer;

    public Customer(int idCustomer, String nameCustomer, String cityCustomer) {
        this.idCustomer = idCustomer;
        this.nameCustomer = nameCustomer;
        this.cityCustomer = cityCustomer;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getCityCustomer() {
        return cityCustomer;
    }

    public void setCityCustomer(String cityCustomer) {
        this.cityCustomer = cityCustomer;
    }

}
