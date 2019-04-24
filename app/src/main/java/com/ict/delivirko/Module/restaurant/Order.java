package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class Order implements Serializable {

    private int id;
    private int status;
    private String status_text;
    private String address;
    private int company_rate;
    private int driver_rate;
    private double d_lat;
    private double d_lng;
    private float rate_count;

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getStatus_text() {
        return status_text;
    }

    public int getCompany_rate() {
        return company_rate;
    }

    public int getDriver_rate() {
        return driver_rate;
    }

    public double getD_lat() {
        return d_lat;
    }

    public double getD_lng() {
        return d_lng;
    }

    public float getRate_count() {
        return rate_count;
    }

    public String getAddress() {
        return address;
    }
}
