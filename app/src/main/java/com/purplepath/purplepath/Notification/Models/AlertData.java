package com.purplepath.purplepath.Notification.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 20-01-2017.
 */

public class AlertData implements Serializable {
    private String message;

    private ArrayList<User_alerts> user_alerts;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<User_alerts> getUser_alerts ()
    {
        return user_alerts;
    }

    public void setUser_alerts (ArrayList<User_alerts> user_alerts)
    {
        this.user_alerts = user_alerts;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_alerts = "+user_alerts+"]";
    }
}
