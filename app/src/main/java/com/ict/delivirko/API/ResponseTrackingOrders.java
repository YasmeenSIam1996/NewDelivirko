package com.ict.delivirko.API;


import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.restaurant.DriverTracking;

import java.util.List;

public class ResponseTrackingOrders {

    private boolean status;
    private String message;
    @SerializedName("data")
    private List<DriverTracking> driverTracking;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public List<DriverTracking> getDriverTracking() {
        return driverTracking;
    }
}
