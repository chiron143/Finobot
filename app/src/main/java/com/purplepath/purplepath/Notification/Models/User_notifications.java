package com.purplepath.purplepath.Notification.Models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 17-01-2017.
 */

public class User_notifications implements Serializable {

    private String id;

    private String notification_text;

    private String created_datetime;

    private String user_id;

    private String modified_datetime;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getNotification_text ()
    {
        return notification_text;
    }

    public void setNotification_text (String notification_text)
    {
        this.notification_text = notification_text;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", notification_text = "+notification_text+", created_datetime = "+created_datetime+", user_id = "+user_id+", modified_datetime = "+modified_datetime+"]";
    }
}
