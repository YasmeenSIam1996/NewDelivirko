package com.ict.delivirko.Module;

import java.io.Serializable;

public class OrderNotification implements Serializable {
    private String status_id;
    private String duration;
    private String driver_id;
    private String driver_car_number;
    private String driver_phone;
    private String distance;
    private String driver_lat;
    private String driver_lng;
    private String order_status;
    private String order_id;
    private String driver_name;
    private String driver_rating;
    private String order_status_text;
    private String driver_image;
    private int driver_rating_count;
    private String waiting_time;
    private String company_name;
    private String company_address;
    private String company_lng;
    private String company_lat;

    public String getStatus_id() {
        return status_id;
    }

    public String getDuration() {
        return duration;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public String getDriver_car_number() {
        return driver_car_number;
    }

    public String getDriver_phone() {
        return driver_phone;
    }

    public String getDistance() {
        return distance;
    }

    public String getDriver_lat() {
        return driver_lat;
    }

    public String getDriver_lng() {
        return driver_lng;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getDriver_name() {
        return driver_name;
    }

    public String getDriver_rating() {
        return driver_rating;
    }

    public String getOrder_status_text() {
        return order_status_text;
    }

    public String getDriver_image() {
        return driver_image;
    }

    public int getDriver_rating_count() {
        return driver_rating_count;
    }

    @Override
    public String toString() {
        return "OrderNotification{" +
                "status_id='" + status_id + '\'' +
                ", duration='" + duration + '\'' +
                ", driver_id='" + driver_id + '\'' +
                ", driver_car_number='" + driver_car_number + '\'' +
                ", driver_phone='" + driver_phone + '\'' +
                ", distance='" + distance + '\'' +
                ", driver_lat='" + driver_lat + '\'' +
                ", driver_lng='" + driver_lng + '\'' +
                ", order_status='" + order_status + '\'' +
                ", order_id='" + order_id + '\'' +
                ", driver_name='" + driver_name + '\'' +
                ", driver_rating='" + driver_rating + '\'' +
                ", order_status_text='" + order_status_text + '\'' +
                ", driver_image='" + driver_image + '\'' +
                ", driver_rating_count=" + driver_rating_count +
                '}';
    }


    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public void setDriver_car_number(String driver_car_number) {
        this.driver_car_number = driver_car_number;
    }

    public void setDriver_phone(String driver_phone) {
        this.driver_phone = driver_phone;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setDriver_lat(String driver_lat) {
        this.driver_lat = driver_lat;
    }

    public void setDriver_lng(String driver_lng) {
        this.driver_lng = driver_lng;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setDriver_name(String driver_name) {
        this.driver_name = driver_name;
    }

    public void setDriver_rating(String driver_rating) {
        this.driver_rating = driver_rating;
    }

    public void setOrder_status_text(String order_status_text) {
        this.order_status_text = order_status_text;
    }

    public void setDriver_image(String driver_image) {
        this.driver_image = driver_image;
    }

    public void setDriver_rating_count(int driver_rating_count) {
        this.driver_rating_count = driver_rating_count;
    }

    public String getWaiting_time() {
        return waiting_time;
    }

    public void setWaiting_time(String waiting_time) {
        this.waiting_time = waiting_time;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_address() {
        return company_address;
    }

    public void setCompany_address(String company_address) {
        this.company_address = company_address;
    }

    public String getCompany_lng() {
        return company_lng;
    }

    public void setCompany_lng(String company_lng) {
        this.company_lng = company_lng;
    }

    public String getCompany_lat() {
        return company_lat;
    }

    public void setCompany_lat(String company_lat) {
        this.company_lat = company_lat;
    }
}
