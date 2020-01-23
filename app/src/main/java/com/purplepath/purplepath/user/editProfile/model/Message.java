package com.purplepath.purplepath.user.editProfile.model;

import java.io.Serializable;

/**
 * Created by pravinr on 5/25/17.
 */

public class Message implements Serializable {
    private String old_password;

    private String user_id;

    private String new_password;

    public String getOld_password ()
    {
        return old_password;
    }

    public void setOld_password (String old_password)
    {
        this.old_password = old_password;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getNew_password ()
    {
        return new_password;
    }

    public void setNew_password (String new_password)
    {
        this.new_password = new_password;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [old_password = "+old_password+", user_id = "+user_id+", new_password = "+new_password+"]";
    }
}

