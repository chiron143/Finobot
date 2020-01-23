package com.purplepath.purplepath.cashflowmanagmentchart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cashflow implements Serializable {


    private String cash_age;

    private String years_pass;

    private String years_remain;

    private AssetArray asset_array;

    private LiabArray liab_array;

    public String getCashAge() {
        return cash_age;
    }

    public void setCashAge(String cash_age) {
        this.cash_age = cash_age;
    }

    public String getYearsPass() {
        return years_pass;
    }

    public void setYearsPass(String years_pass) {
        this.years_pass = years_pass;
    }

    public String getYearsRemain() {
        return years_remain;
    }

    public void setYearsRemain(String years_remain) {
        this.years_remain = years_remain;
    }

    public AssetArray getAssetArray() {
        return asset_array;
    }

    public void setAssetArray(AssetArray asset_array) {
        this.asset_array = asset_array;
    }

    public LiabArray getLiabArray() {
        return liab_array;
    }

    public void setLiabArray(LiabArray liab_array) {
        this.liab_array = liab_array;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [years_remain = "+years_remain+", years_pass = "+years_pass+", cash_age = "+cash_age+", asset_array = "+asset_array+", liab_array = "+liab_array+"]";
    }

}
