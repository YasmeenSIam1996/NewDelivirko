package com.ict.delivirko.API;


import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.fragment.Conditions;

public class ResponseConditions {

    private boolean status;
    private String message;

    @SerializedName("data")
    private Conditions conditions;

    public boolean isStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }


    public Conditions getConditions() {
        return conditions;
    }
}
