package com.ict.delivirko.API;

import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.pilot.DataNearestCompany;

import java.io.Serializable;


public class ResponseNearestDriver implements Serializable {

    private boolean status;
    private String message;
    @SerializedName("data")
    private DataNearestCompany dataNearestCompany;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


    public DataNearestCompany getDataNearestCompany() {
        return dataNearestCompany;
    }
}
