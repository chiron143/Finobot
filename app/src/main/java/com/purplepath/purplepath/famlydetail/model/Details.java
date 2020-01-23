package com.purplepath.purplepath.famlydetail.model;

import java.io.Serializable;

/**
 * Created by dinesh on 11/02/17.
 */
public class Details implements Serializable{
    private String fid;

    private String user_id;

    public String getFid ()
    {
        return fid;
    }

    public void setFid (String fid)
    {
        this.fid = fid;
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
        return "ClassPojo [fid = "+fid+", user_id = "+user_id+"]";
    }
}
