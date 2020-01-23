package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;

/**
 * Created by Bert on 08-Jul-16.
 */
public class DeleteInsuranceInputData implements Serializable
{
    private String ins_id;

    private String user_id;

    public String getIns_id ()
    {
        return ins_id;
    }

    public void setIns_id (String ins_id)
    {
        this.ins_id = ins_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ins_id = "+ins_id+", user_id = "+user_id+"]";
    }
}