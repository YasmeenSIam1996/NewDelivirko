package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;
import java.util.List;

public class BillData implements Serializable {

    private List<BillOrders> orders;
    private double total_price;
    private String end_date;
    private String start_date;

    public List<BillOrders> getOrders() {
        return orders;
    }

    public double getTotal_price() {
        return total_price;
    }

    public String getEnd_date() {
        return end_date;
    }

    public String getStart_date() {
        return start_date;
    }
}
