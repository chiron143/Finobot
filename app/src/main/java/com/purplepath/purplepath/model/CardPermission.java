package com.purplepath.purplepath.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CardPermission {

    @SerializedName("result")
    @Expose
    private List<CardResult> result = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<CardResult> getResult() {
        return result;
    }

    public void setResult(List<CardResult> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
