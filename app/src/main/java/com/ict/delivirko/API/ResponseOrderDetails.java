package com.ict.delivirko.API;


import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.restaurant.OrderDetails;

public class ResponseOrderDetails {
    private boolean status;
    private String message;
    @SerializedName("data")
    private OrderDetails orderDetails;

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

    public OrderDetails getOrderDetails() {
        return orderDetails;
    }
}
