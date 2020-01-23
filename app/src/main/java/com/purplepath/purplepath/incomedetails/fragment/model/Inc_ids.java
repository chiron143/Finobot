package com.purplepath.purplepath.incomedetails.fragment.model;

import java.io.Serializable;

/**
 * Created by dinesh on 04/07/16.
 */

public class Inc_ids implements Serializable
{
    private String id;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+"]";
    }
}
