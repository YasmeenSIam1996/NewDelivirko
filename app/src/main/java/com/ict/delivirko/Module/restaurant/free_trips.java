package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class free_trips implements Serializable {
    private int id;
    private int remain_trips;
    private String  end_at;

    public int getId() {
        return id;
    }

    public int getRemain_trips() {
        return remain_trips;
    }

    public String getEnd_at() {
        return end_at;
    }
}
