package com.ict.delivirko.API;

import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.UserDriver;


public class ResponseSign {
    private boolean status;
    private String message;
    @SerializedName("data")
    private UserDriver user;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public UserDriver getUser() {
        return user;
    }
}
