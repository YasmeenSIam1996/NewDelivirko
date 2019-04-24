package com.ict.delivirko.Module.restaurant;

import java.util.List;

public class OrderData {
     private String rate;
     private String start_dat;
     private String end_date;
     private List<Orders> orders;

    public String getRate() {
        return rate;
    }

    public String getStart_dat() {
        return start_dat;
    }

    public String getEnd_date() {
        return end_date;
    }

    public List<Orders> getOrders() {
        return orders;
    }
}
