package com.ict.delivirko.Module.restaurant;


import java.io.Serializable;

public class TrackingOrderDetails implements Serializable {
    private Driver driver;
    private Company company;
    private Order order;

    public Driver getDriver() {
        return driver;
    }

    public Company getCompany() {
        return company;
    }

    public Order getOrder() {
        return order;
    }
}
