package com.purplepath.purplepath.schedule.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 06-07-2017.
 */

public class Data implements Serializable {
    private String message;

    private Pending pending;

    private Upcom_sch upcom_sch;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Pending getPending ()
    {
        return pending;
    }

    public void setPending (Pending pending)
    {
        this.pending = pending;
    }

    public Upcom_sch getUpcom_sch ()
    {
        return upcom_sch;
    }

    public void setUpcom_sch (Upcom_sch upcom_sch)
    {
        this.upcom_sch = upcom_sch;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", pending = "+pending+", upcom_sch = "+upcom_sch+"]";
    }
}
