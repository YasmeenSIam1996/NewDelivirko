package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class places implements Serializable {
        private int id;
        private String name;
        private String address;
        private double lat;
        private double lng;
        private double price_from;
        private double price_to;

        public int getId() {
                return id;
        }

        public String getName() {
                return name;
        }

        public String getAddress() {
                return address;
        }

        public double getLat() {
                return lat;
        }

        public double getLng() {
                return lng;
        }

        public double getPrice_from() {
                return price_from;
        }

        public double getPrice_to() {
                return price_to;
        }
}
