package com.ict.delivirko.Module.pilot;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MyOrder implements Serializable {
    private int id;
    private String time;
    private String date;
    private int is_paid;
    @SerializedName("orders")
    private List<MyOrderDetails> myOrderDetails;

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public List<MyOrderDetails> getMyOrderDetails() {
        return myOrderDetails;
    }

    public void setMyOrderDetails(List<MyOrderDetails> myOrderDetails) {
        this.myOrderDetails = myOrderDetails;
    }

    public int getIs_paid() {
        return is_paid;
    }

    @Override
    public String toString() {
        return "MyOrder{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", is_paid=" + is_paid +
                ", myOrderDetails=" + myOrderDetails +
                '}';
    }
}
