package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class DriverTracking implements Serializable {

    private int id;
    private Driver driver;
    private int status_id;

    public int getId() {
        return id;
    }

    public Driver getDriver() {
        return driver;
    }

    public int getStatus_id() {
        return status_id;
    }
}
