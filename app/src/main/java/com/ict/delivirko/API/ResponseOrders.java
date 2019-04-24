package com.ict.delivirko.API;


import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.restaurant.OrderData;

public class ResponseOrders {

    private boolean status;
    private String message;
    @SerializedName("data")
    private OrderData orderData;

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

    public OrderData getOrderData() {
        return orderData;
    }

}
