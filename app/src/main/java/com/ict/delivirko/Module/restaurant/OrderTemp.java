package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class OrderTemp implements Serializable {

    private int id;
    private int status;

    public int getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
