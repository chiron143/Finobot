package com.purplepath.purplepath.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvestArray {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("asset_name")
    @Expose
    private String assetName;
    @SerializedName("current_value")
    @Expose
    private String currentValue;
    @SerializedName("annual_contr")
    @Expose
    private String annualContr;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("purchase_val")
    @Expose
    private String purchaseVal;
    @SerializedName("growth_rate")
    @Expose
    private String growthRate;
    @SerializedName("asset_fuure_value")
    @Expose
    private Double assetFuureValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getAnnualContr() {
        return annualContr;
    }

    public void setAnnualContr(String annualContr) {
        this.annualContr = annualContr;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseVal() {
        return purchaseVal;
    }

    public void setPurchaseVal(String purchaseVal) {
        this.purchaseVal = purchaseVal;
    }

    public String getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(String growthRate) {
        this.growthRate = growthRate;
    }

    public Double getAssetFuureValue() {
        return assetFuureValue;
    }

    public void setAssetFuureValue(Double assetFuureValue) {
        this.assetFuureValue = assetFuureValue;
    }

}
