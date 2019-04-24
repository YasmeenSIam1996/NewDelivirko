package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class Orders implements Serializable {
    private int id;
    private String time;
    private String date;
    private Driver driver;

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public Driver getDriver() {
        return driver;
    }
}
