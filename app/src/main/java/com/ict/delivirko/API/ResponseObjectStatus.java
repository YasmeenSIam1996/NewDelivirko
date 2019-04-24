package com.ict.delivirko.API;


import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.pilot.Status;

public class ResponseObjectStatus {

    private boolean status;
    private String message;
    @SerializedName("data")
    private Status status_;

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

    public Status getStatus_() {
        return status_;
    }
}
