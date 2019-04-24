package com.ict.delivirko.API;

import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.restaurant.MakeOrderData;

import java.io.Serializable;


public class ResponseMakeOrder implements Serializable {

    private boolean status;
    private String message;
    @SerializedName("data")
    private MakeOrderData makeOrderData;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public MakeOrderData getMakeOrderData() {
        return makeOrderData;
    }
}
