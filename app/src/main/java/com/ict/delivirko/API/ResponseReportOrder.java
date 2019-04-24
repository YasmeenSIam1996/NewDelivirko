package com.ict.delivirko.API;

import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.restaurant.ReportData;

import java.io.Serializable;


public class ResponseReportOrder implements Serializable {

    private boolean status;
    private String message;
    @SerializedName("data")
    private ReportData reportData;

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ReportData getReportData() {
        return reportData;
    }
}
