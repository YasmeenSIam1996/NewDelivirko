package com.ict.delivirko.Module.pilot;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DataNearestCompany implements Serializable {
    private int id;
    private String address;
    private double lat;
    private double lng;
    @SerializedName("nearest_company")
    private List<NearestCompany> nearestCompanies;

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public List<NearestCompany> getNearestCompanies() {
        return nearestCompanies;
    }

}
