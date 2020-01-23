package com.purplepath.purplepath.schedule.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 11-07-2017.
 */

public class Input implements Serializable {
    private String id;

    private String paid_date;

    private String flag;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPaid_date ()
    {
        return paid_date;
    }

    public void setPaid_date (String paid_date)
    {
        this.paid_date = paid_date;
    }

    public String getFlag ()
    {
        return flag;
    }

    public void setFlag (String flag)
    {
        this.flag = flag;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", paid_date = "+paid_date+", flag = "+flag+"]";
    }
}
