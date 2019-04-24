package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class Driver implements Serializable {

    private int id;
    private String name;
    private String image;
    private double lat;
    private double lng;
    private String car_number;
    private String phone;
    private float driver_rating;
    private int driver_rating_count;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }


    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getCar_number() {
        return car_number;
    }

    public String getPhone() {
        return phone;
    }

    public float getDriver_rating() {
        return driver_rating;
    }

    public int getDriver_rating_count() {
        return driver_rating_count;
    }
}
