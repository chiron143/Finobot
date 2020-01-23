package com.purplepath.purplepath.cashflowmanagmentchart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GetcashflowAssetflowModel implements Serializable {


    private String status_code;

    private String status;

    private String service_name;

    private DataAsset data;

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public DataAsset getData() {
        return data;
    }

    public void setData(DataAsset data) {
        this.data = data;
    }

}
