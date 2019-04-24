package com.ict.delivirko.API;

import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.adapter.restaurant.Contacts;

import java.io.Serializable;
import java.util.List;


public class ResponseContact implements Serializable {

    private boolean status;
    private String message;
    @SerializedName("data")
    private List<Contacts> contactsList;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Contacts> getContactsList() {
        return contactsList;
    }
}
