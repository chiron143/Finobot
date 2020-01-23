package com.purplepath.purplepath.riskAssesment.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 26-01-2017.
 */

public class RiskScoreForUserModel implements Serializable {

    private String status_code;

    private String status;

    private RiskScoreData data;

    private String service_name;

    public String getStatus_code ()
    {
        return status_code;
    }

    public void setStatus_code (String status_code)
    {
        this.status_code = status_code;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public RiskScoreData getData ()
    {
        return data;
    }

    public void setData (RiskScoreData data)
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

    @Override
    public String toString()
    {
        return "ClassPojo [status_code = "+status_code+", status = "+status+", data = "+data+", service_name = "+service_name+"]";
    }
}
