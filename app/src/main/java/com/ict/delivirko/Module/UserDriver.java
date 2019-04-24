package com.ict.delivirko.Module;

import java.io.Serializable;


public class UserDriver implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int active;
    private String car_number;
    private String token;
    private boolean isDriver;
    private String image;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public int getActive() {
        return active;
    }

    public String getCar_number() {
        return car_number;
    }

    public String getToken() {
        return token;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
