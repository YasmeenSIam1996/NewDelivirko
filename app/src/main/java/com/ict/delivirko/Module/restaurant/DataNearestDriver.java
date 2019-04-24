package com.ict.delivirko.Module.restaurant;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DataNearestDriver implements Serializable {
    private int id;
    private String address;
    private double lat;
    private double lng;
    @SerializedName("nearest_driver")
    private NearestDriver nearestDriver;

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public NearestDriver getNearestDriver() {
        return nearestDriver;
    }
}
