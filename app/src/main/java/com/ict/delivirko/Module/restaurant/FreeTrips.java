package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class FreeTrips implements Serializable {
    private int id;
    private int company_id;
    private int order_num;
    private int order_num_used;
    private double discount_rate;
    private double discount_max_value;
    private int end_at;

    public int getId() {
        return id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public int getOrder_num() {
        return order_num;
    }

    public int getOrder_num_used() {
        return order_num_used;
    }

    public double getDiscount_rate() {
        return discount_rate;
    }

    public double getDiscount_max_value() {
        return discount_max_value;
    }

    public int getEnd_at() {
        return end_at;
    }
}
