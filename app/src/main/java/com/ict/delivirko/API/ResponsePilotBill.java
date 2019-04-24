package com.ict.delivirko.API;


import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.pilot.Pilot_bill;

public class ResponsePilotBill {

    private boolean status;
    private String message;
    @SerializedName("data")
    private Pilot_bill pilot_bill;

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

    public Pilot_bill getPilot_bill() {
        return pilot_bill;
    }
}
