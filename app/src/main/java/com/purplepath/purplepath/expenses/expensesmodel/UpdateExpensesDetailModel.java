package com.purplepath.purplepath.expenses.expensesmodel;

import java.io.Serializable;

/**
 * Created by Bert on 22-Jul-16.
 */
public class UpdateExpensesDetailModel implements Serializable
{
    private String status_code;

    private String status;

    private UpdateExpensesDetailData data;

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

    public UpdateExpensesDetailData getData ()
    {
        return data;
    }

    public void setData (UpdateExpensesDetailData data)
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