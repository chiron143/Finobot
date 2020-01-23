package com.purplepath.purplepath.incomedetails.fragment.model;

import java.io.Serializable;

/**
 * Created by dinesh on 26/07/16.
 */
public class GetIncomeModel implements Serializable {
    private String status_code;

    private String status;

    private GetIncomeData data;

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

    public GetIncomeData getData ()
    {
        return data;
    }

    public void setData (GetIncomeData data)
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
