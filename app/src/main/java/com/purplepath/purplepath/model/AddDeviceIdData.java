package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by Bert on 20-May-16.
 */
public class AddDeviceIdData implements Serializable {

    private String message;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+"]";
    }
}
