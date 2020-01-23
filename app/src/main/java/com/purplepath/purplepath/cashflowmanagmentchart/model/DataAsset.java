package com.purplepath.purplepath.cashflowmanagmentchart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataAsset implements Serializable {


    private ArrayList<Cashflow> cashflow = null;

    private String message;

    public ArrayList<Cashflow> getCashflow() {
        return cashflow;
    }

    public void setCashflow(ArrayList<Cashflow> cashflow) {
        this.cashflow = cashflow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", cashflow = "+cashflow+"]";
    }

}
