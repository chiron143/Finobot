package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by Bert on 09-Jun-16.
 */
public class PersonalDetailsData implements Serializable
{
    private String message;

    private PersonalUpdatedDetails updated_details;

    private String pid;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public PersonalUpdatedDetails getUpdated_details ()
    {
        return updated_details;
    }

    public void setUpdated_details (PersonalUpdatedDetails updated_details)
    {
        this.updated_details = updated_details;
    }

    public String getPid ()
    {
        return pid;
    }

    public void setPid (String pid)
    {
        this.pid = pid;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", updated_details = "+updated_details+", pid = "+pid+"]";
    }
}