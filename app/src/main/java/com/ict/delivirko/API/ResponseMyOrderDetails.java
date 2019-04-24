package com.ict.delivirko.API;

import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.MyOrderDetails;

import java.io.Serializable;


public class ResponseMyOrderDetails implements Serializable {

    private boolean status;
    private String message;
    @SerializedName("data")
    private MyOrderDetails myOrderDetails;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public MyOrderDetails getMyOrderDetails() {
        return myOrderDetails;
    }
}
