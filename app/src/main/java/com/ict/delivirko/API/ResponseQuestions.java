package com.ict.delivirko.API;


import com.google.gson.annotations.SerializedName;
import com.ict.delivirko.Module.restaurant.Questions;

import java.util.List;

public class ResponseQuestions {

    private boolean status;
    private String message;

    @SerializedName("data")
    private List<Questions> questions;

    public boolean isStatus() {
        return status;
    }


    public String getMessage() {
        return message;
    }


    public List<Questions> getQuestions() {
        return questions;
    }
}
