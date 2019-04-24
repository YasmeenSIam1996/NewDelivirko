package com.ict.delivirko.API;

import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.restaurant.FreeTrips;

import java.io.Serializable;


public class ResponseFreeTrips implements Serializable {

    private boolean status;
    private String message;
    @SerializedName("data")
    private FreeTrips freeTrips;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public FreeTrips getFreeTrips() {
        return freeTrips;
    }
}
