package com.purplepath.purplepath.firebaseFcm.model;

import java.io.Serializable;

/**
 * Created by pravinr on 1/2/18.
 */

public class NotificationModel implements Serializable{
    private String message;

    private String device_id;

    private String view_id;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getDevice_id ()
    {
        return device_id;
    }

    public void setDevice_id (String device_id)
    {
        this.device_id = device_id;
    }

    public String getView_id ()
    {
        return view_id;
    }

    public void setView_id (String view_id)
    {
        this.view_id = view_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", device_id = "+device_id+", view_id = "+view_id+"]";
    }
}

