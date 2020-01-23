package com.purplepath.purplepath.lifeevent.model;

import java.io.Serializable;

/**
 * Created by pravinr on 3/7/18.
 */

public class Life_events implements Serializable{

    private String id;

    private String created_datetime;

    private String user_id;

    private String modified_datetime;

    private String event_name;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
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

    public String getEvent_name ()
    {
        return event_name;
    }

    public void setEvent_name (String event_name)
    {
        this.event_name = event_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", created_datetime = "+created_datetime+", user_id = "+user_id+", modified_datetime = "+modified_datetime+", event_name = "+event_name+"]";
    }
}


