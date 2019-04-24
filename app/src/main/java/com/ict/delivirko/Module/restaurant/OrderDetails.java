package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class OrderDetails extends Orders implements Serializable {
    private String place;
    private String street;
    private int building_no;
    private int apartment_no;
    private int floor_no;
    private double price;
    private double shipping;
    private int payment;
    private double company_rating;
    private String order_type;
    private String driver_code;
    private float points;

    public String getPlace() {
        return place;
    }

    public String getStreet() {
        return street;
    }

    public int getBuilding_no() {
        return building_no;
    }

    public int getApartment_no() {
        return apartment_no;
    }

    public int getFloor_no() {
        return floor_no;
    }

    public double getPrice() {
        return price;
    }

    public double getShipping() {
        return shipping;
    }

    public int getPayment() {
        return payment;
    }

    public double getCompany_rating() {
        return company_rating;
    }


    public String getOrder_type() {
        return order_type;
    }

    public String getDriver_code() {
        return driver_code;
    }

    public float getPoints() {
        return points;
    }
}
