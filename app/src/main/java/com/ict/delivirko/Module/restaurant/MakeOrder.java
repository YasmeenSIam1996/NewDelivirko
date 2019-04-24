package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class MakeOrder implements Serializable {

    private String order_id;
    private String name;
    private String phone;
    private String place;
    private String street;
    private String building_no;
    private String apartment_no;
    private String floor_no;
    private String special_mark;
    private String to_lat;
    private String to_lng;
    private String price;
    private String shipping;
    private String payment;
    private String free_trip;
    private String place_id;

    public MakeOrder(String order_id, String name, String phone, String place, String street, String building_no, String apartment_no, String floor_no, String special_mark, String to_lat, String to_lng, String price, String shipping, String payment, String free_trip, String place_id) {
        this.order_id = order_id;
        this.name = name;
        this.phone = phone;
        this.place = place;
        this.street = street;
        this.building_no = building_no;
        this.apartment_no = apartment_no;
        this.floor_no = floor_no;
        this.special_mark = special_mark;
        this.to_lat = to_lat;
        this.to_lng = to_lng;
        this.price = price;
        this.shipping = shipping;
        this.payment = payment;
        this.free_trip = free_trip;
        this.place_id = place_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getPlace() {
        return place;
    }

    public String getStreet() {
        return street;
    }

    public String getBuilding_no() {
        return building_no;
    }

    public String getApartment_no() {
        return apartment_no;
    }

    public String getFloor_no() {
        return floor_no;
    }

    public String getSpecial_mark() {
        return special_mark;
    }

    public String getTo_lat() {
        return to_lat;
    }

    public String getTo_lng() {
        return to_lng;
    }

    public String getPrice() {
        return price;
    }

    public String getShipping() {
        return shipping;
    }

    public String getPayment() {
        return payment;
    }

    public String getFree_trip() {
        return free_trip;
    }

    public String getPlace_id() {
        return place_id;
    }
}
