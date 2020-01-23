package com.purplepath.purplepath.cashflowmanagmentchart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AssetArray implements Serializable {


    private String liquid;

    private String comm_gold;

    private String emp_benf;

    private String fixed_inc;

    private String real_estate;

    private String equity;

    public String getLiquid() {
        return liquid;
    }

    public void setLiquid(String liquid) {
        this.liquid = liquid;
    }

    public String getCommGold() {
        return comm_gold;
    }

    public void setCommGold(String comm_gold) {
        this.comm_gold = comm_gold;
    }

    public String getEmpBenf() {
        return emp_benf;
    }

    public void setEmpBenf(String emp_benf) {
        this.emp_benf = emp_benf;
    }

    public String getFixedInc() {
        return fixed_inc;
    }

    public void setFixedInc(String fixed_inc) {
        this.fixed_inc = fixed_inc;
    }

    public String getRealEstate() {
        return real_estate;
    }

    public void setRealEstate(String real_estate) {
        this.real_estate = real_estate;
    }

    public String getEquity() {
        return equity;
    }

    public void setEquity(String equity) {
        this.equity = equity;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [liquid = "+liquid+", comm_gold = "+comm_gold+", emp_benf = "+emp_benf+", fixed_inc = "+fixed_inc+", real_estate = "+real_estate+", equity = "+equity+"]";
    }

}
