package com.ict.delivirko.API;

import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.restaurant.BillData;

import java.io.Serializable;


public class ResponseBillResData implements Serializable {

    private boolean status;
    private String message;
    @SerializedName("data")
    private BillData billData;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public BillData getBillData() {
        return billData;
    }
}
