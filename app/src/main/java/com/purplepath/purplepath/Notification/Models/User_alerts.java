package com.purplepath.purplepath.Notification.Models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 20-01-2017.
 */

public class User_alerts implements Serializable {
    private String alert_priority;

    private String alert_text;

    private String created_datetime;

    public String getAlert_priority ()
    {
        return alert_priority;
    }

    public void setAlert_priority (String alert_priority)
    {
        this.alert_priority = alert_priority;
    }

    public String getAlert_text ()
    {
        return alert_text;
    }

    public void setAlert_text (String alert_text)
    {
        this.alert_text = alert_text;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [alert_priority = "+alert_priority+", alert_text = "+alert_text+", created_datetime = "+created_datetime+"]";
    }
}
