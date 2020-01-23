package com.purplepath.purplepath.Notification.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 17-01-2017.
 */

public class Data implements Serializable {
    private String message;

    private ArrayList <Other_notifications> other_notifications;

    private ArrayList <User_notifications> user_notifications;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Other_notifications> getOther_notifications ()
    {
        return other_notifications;
    }

    public void setOther_notifications (ArrayList <Other_notifications> other_notifications)
    {
        this.other_notifications = other_notifications;
    }

    public ArrayList <User_notifications> getUser_notifications ()
    {
        return user_notifications;
    }

    public void setUser_notifications (ArrayList<User_notifications> user_notifications)
    {
        this.user_notifications = user_notifications;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", other_notifications = "+other_notifications+", user_notifications = "+user_notifications+"]";
    }
}
