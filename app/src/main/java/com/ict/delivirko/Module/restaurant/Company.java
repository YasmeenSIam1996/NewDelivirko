package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class Company implements Serializable {
    private int id;
    private double lat;
    private double lng;
    private String address;

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getAddress() {
        return address;
    }

    public static class Orders implements Serializable {
        private String date;
        private double price;

        public String getDate() {
            return date;
        }

        public double getPrice() {
            return price;
        }

    }
}
