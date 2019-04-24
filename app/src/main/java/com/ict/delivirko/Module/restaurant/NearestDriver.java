package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class NearestDriver implements Serializable {

    private int driver_id;
    private String name;
    private double lat;
    private double lng;
    private String distance;
    private String time;

    public int getDriver_id() {
        return driver_id;
    }

    public String getName() {
        return name;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getDistance() {
        return distance;
    }

    public String getTime() {
        return time;
    }
}
