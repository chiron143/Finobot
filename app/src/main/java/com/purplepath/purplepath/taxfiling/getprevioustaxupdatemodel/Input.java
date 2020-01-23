package com.purplepath.purplepath.taxfiling.getprevioustaxupdatemodel;

import java.io.Serializable;

/**
 * Created by pravinr on 6/8/18.
 */

public class Input implements Serializable {

    private String is_file;

    private String user_id;

    public String getIs_file ()
    {
        return is_file;
    }

    public void setIs_file (String is_file)
    {
        this.is_file = is_file;
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
        return "ClassPojo [is_file = "+is_file+", user_id = "+user_id+"]";
    }
}

