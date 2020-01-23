package com.purplepath.purplepath.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CardResult {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("tax_plan_display")
    @Expose
    private String taxPlanDisplay;
    @SerializedName("tax_file_display")
    @Expose
    private String taxFileDisplay;
    @SerializedName("created_datetime")
    @Expose
    private String createdDatetime;
    @SerializedName("modified_datetime")
    @Expose
    private String modifiedDatetime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaxPlanDisplay() {
        return taxPlanDisplay;
    }

    public void setTaxPlanDisplay(String taxPlanDisplay) {
        this.taxPlanDisplay = taxPlanDisplay;
    }

    public String getTaxFileDisplay() {
        return taxFileDisplay;
    }

    public void setTaxFileDisplay(String taxFileDisplay) {
        this.taxFileDisplay = taxFileDisplay;
    }

    public String getCreatedDatetime() {
        return createdDatetime;
    }

    public void setCreatedDatetime(String createdDatetime) {
        this.createdDatetime = createdDatetime;
    }

    public String getModifiedDatetime() {
        return modifiedDatetime;
    }

    public void setModifiedDatetime(String modifiedDatetime) {
        this.modifiedDatetime = modifiedDatetime;
    }

}
