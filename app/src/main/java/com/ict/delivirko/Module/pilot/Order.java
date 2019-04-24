package com.ict.delivirko.Module.pilot;

import java.io.Serializable;

public class Order implements Serializable {
    private int id;
    private double price;
    private double lat;
    private double lng;
    private String name;
    private String address;
    private String phone;
    private String place;
    private String waiting_time;

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPlace() {
        return place;
    }

    public String getWaiting_time() {
        return waiting_time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", price=" + price +
                ", lat=" + lat +
                ", lng=" + lng +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", place='" + place + '\'' +
                ", waiting_time='" + waiting_time + '\'' +
                '}';
    }
}
