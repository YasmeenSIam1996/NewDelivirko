package com.ict.delivirko.API;


import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.fragment.pilot.MyOrderData;

public class ResponseOrderPilot {

    private boolean status;
    private String message;
    @SerializedName("data")
    private MyOrderData myOrderData;

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

    public MyOrderData getMyOrder() {
        return myOrderData;
    }

    @Override
    public String toString() {
        return "ResponseOrderPilot{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", myOrderData=" + myOrderData +
                '}';
    }
}
