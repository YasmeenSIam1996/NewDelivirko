package com.ict.delivirko.Module.restaurant;

import java.io.Serializable;

public class Questions implements Serializable {

    private int id;
    private String question;
    private String answer;

    public int getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
