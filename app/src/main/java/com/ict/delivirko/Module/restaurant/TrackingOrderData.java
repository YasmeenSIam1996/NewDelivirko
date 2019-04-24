package com.ict.delivirko.Module.restaurant;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TrackingOrderData implements Serializable {
    private Driver driver;
    @SerializedName("order")
    private Company company;

    public Driver getDriver() {
        return driver;
    }

    public Company getCompany() {
        return company;
    }
}
