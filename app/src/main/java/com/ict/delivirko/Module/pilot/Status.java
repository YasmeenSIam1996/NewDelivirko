package com.ict.delivirko.Module.pilot;

import java.io.Serializable;

public class Status implements Serializable {
    private int status_id;
    private Order order;
    private Company company;

    public int getStatus_id() {
        return status_id;
    }

    public Order getOrder() {
        return order;
    }

    public Company getCompany() {
        return company;
    }
}
