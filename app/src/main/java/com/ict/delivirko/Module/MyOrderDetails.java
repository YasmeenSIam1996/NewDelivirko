package com.ict.delivirko.Module;

import java.io.Serializable;

public class MyOrderDetails implements Serializable {

    private int id;
    private String time;
    private String date;
    private String place;
    private String street;
    private int building_no;
    private int apartment_no;
    private int floor_no;
    private double price;
    private double shipping;
    private int payment;
    private float points;
    private String order_type;
    private float company_rating;
    private String name;

    public int getId() {
        return id;
    }

    public String  getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

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

    public float getPoints() {
        return points;
    }

    public String getOrder_type() {
        return order_type;
    }

    public float getCompany_rating() {
        return company_rating;
    }

    public String getName() {
        return name;
    }
}
