package com.purplepath.purplepath.goal;

import java.io.Serializable;

/**
 * Created by Bert on 23-Jun-16.
 */
public class GoalCategoriesModel implements Serializable
{
    private String status_code;

    private String status;

    private GoalCategoriesData data;

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

    public GoalCategoriesData getData ()
    {
        return data;
    }

    public void setData (GoalCategoriesData data)
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
