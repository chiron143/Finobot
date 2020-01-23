package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by Bert on 10-Jun-16.
 */

public class AddPersonalDetailsModelData implements Serializable
{
    private String message;

    private AddPersonalDetailsDataListModel personal_details;

    private String pid;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public AddPersonalDetailsDataListModel getPersonal_details ()
    {
        return personal_details;
    }

    public void setPersonal_details (AddPersonalDetailsDataListModel personal_details)
    {
        this.personal_details = personal_details;
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
        return "ClassPojo [message = "+message+", personal_details = "+personal_details+", pid = "+pid+"]";
    }
}