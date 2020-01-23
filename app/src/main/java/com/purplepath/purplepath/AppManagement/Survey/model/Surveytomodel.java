package com.purplepath.purplepath.AppManagement.Survey.model;

import java.io.Serializable;

/**
 * Created by marut on 18-08-2017.
 */

public class Surveytomodel implements Serializable {

    private String status_code;

    private String status;

    private Surveytodata data;

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

    public Surveytodata getData ()
    {
        return data;
    }

    public void setData (Surveytodata data)
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
