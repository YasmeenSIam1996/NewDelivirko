package com.ict.delivirko.Module.pilot;

import java.io.Serializable;

public class Company implements Serializable {
    private int id;
    private String name;
    private String image;
    private double lat;
    private double lng;
    private String address;
    private String phone;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
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

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
