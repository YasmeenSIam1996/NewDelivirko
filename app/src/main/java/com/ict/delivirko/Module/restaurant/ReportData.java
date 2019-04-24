package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;
import java.util.List;

public class ReportData implements Serializable {
    private List<BillOrders> orders;
    private double total_price;

    public List<BillOrders> getOrders() {
        return orders;
    }

    public double getTotal_price() {
        return total_price;
    }
}
