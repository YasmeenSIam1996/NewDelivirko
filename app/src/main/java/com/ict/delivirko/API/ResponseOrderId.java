package com.ict.delivirko.API;


import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.restaurant.OrderId;

public class ResponseOrderId {

    private boolean status;
    private String message;
    @SerializedName("data")
    private OrderId orderId;

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


    public OrderId getOrderId() {
        return orderId;
    }
}
