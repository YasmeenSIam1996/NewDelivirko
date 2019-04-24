package com.ict.delivirko.API;


import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.pilot.OnTheWay;

public class ResponseOnTheWay {

    private boolean status;
    private String message;
    @SerializedName("data")
    private OnTheWay onTheWay;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OnTheWay getOnTheWay() {
        return onTheWay;
    }
}
