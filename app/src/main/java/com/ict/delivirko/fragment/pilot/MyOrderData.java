package com.ict.delivirko.fragment.pilot;

import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.pilot.MyOrder;

import java.io.Serializable;
import java.util.List;

public class MyOrderData implements Serializable {
    private String start_date;
    private String end_date;
    private String rate;
    @SerializedName("parent_orders")
    private List<MyOrder> myOrder;

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getRate() {
        return rate;
    }

    public List<MyOrder> getMyOrder() {
        return myOrder;
    }

    public void setMyOrder(List<MyOrder> myOrder) {
        this.myOrder = myOrder;
    }

    @Override
    public String toString() {
        return "MyOrderData{" +
                "start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", rate='" + rate + '\'' +
                ", myOrder=" + myOrder +
                '}';
    }
}
