package com.ict.delivirko.Module.restaurant;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MakeOrderData implements Serializable {
    @SerializedName("places")
    private List<places> placesList;
    private com.ict.delivirko.Module.restaurant.free_trips free_trips;

    public List<places> getPlacesList() {
        return placesList;
    }

    public com.ict.delivirko.Module.restaurant.free_trips getFree_trips() {
        return free_trips;
    }
}
