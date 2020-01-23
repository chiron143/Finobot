package com.purplepath.purplepath.investmentPlan.models;

import java.io.Serializable;

public class InvestmentPlan implements Serializable {
    private String status_code;

    private ISP_Data data;

    private String service_name;

    private String status;

    public String getStatus_code ()
    {
        return status_code;
    }

    public void setStatus_code (String status_code)
    {
        this.status_code = status_code;
    }

    public ISP_Data getData ()
    {
        return data;
    }

    public void setData (ISP_Data data)
    {
        this.data = data;
    }

    public String getService_name ()
    {
        return service_name;
    }

    public void setService_name (String service_name)
    {
        this.service_name = service_name;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status_code = "+status_code+", data = "+data+", service_name = "+service_name+", status = "+status+"]";
    }
}

