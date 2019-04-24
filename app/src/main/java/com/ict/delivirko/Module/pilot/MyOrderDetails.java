package com.ict.delivirko.Module.pilot;

import java.io.Serializable;

public class MyOrderDetails implements Serializable {

      private int id;
      private String time;
      private String date;

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "MyOrderDetails{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
