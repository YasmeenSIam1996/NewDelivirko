package com.ict.delivirko.API;

import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.restaurant.DataNearestDriver;

import java.io.Serializable;


public class ResponseNearestCompany implements Serializable {

    private boolean status;
    private String message;
    @SerializedName("data")
    private DataNearestDriver dataNearestDriver;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public DataNearestDriver getDataNearestDriver() {
        return dataNearestDriver;
    }
}
