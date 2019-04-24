package com.ict.delivirko.Module.pilot;

import java.io.Serializable;

public class NearestCompany implements Serializable {

    private int company_id;
    private String name;
    private double lat;
    private double lng;
    private String distance;
    private String time;
    private String image;

    public int getDriver_id() {
        return company_id;
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

    public int getCompany_id() {
        return company_id;
    }

    public String getImage() {
        return image;
    }
}
