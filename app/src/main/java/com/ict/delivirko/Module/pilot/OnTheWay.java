package com.ict.delivirko.Module.pilot;

import java.io.Serializable;

public class OnTheWay implements Serializable {
    private Order order;
    private Company company;

    public Order getOrder() {
        return order;
    }

    public Company getCompany() {
        return company;
    }
}
