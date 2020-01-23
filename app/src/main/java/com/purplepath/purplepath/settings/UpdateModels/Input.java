package com.purplepath.purplepath.settings.UpdateModels;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 07-01-2017.
 */

public class Input implements Serializable {
    private String sas_details;

    private String user_id;

    public String getSas_details ()
    {
        return sas_details;
    }

    public void setSas_details (String sas_details)
    {
        this.sas_details = sas_details;
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
        return "ClassPojo [sas_details = "+sas_details+", user_id = "+user_id+"]";
    }
}
