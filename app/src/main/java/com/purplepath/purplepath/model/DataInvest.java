package com.purplepath.purplepath.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataInvest {

    @SerializedName("cashflow")
    @Expose
    private List<InvestCashflow> cashflow = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<InvestCashflow> getCashflow() {
        return cashflow;
    }

    public void setCashflow(List<InvestCashflow> cashflow) {
        this.cashflow = cashflow;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
