package com.purplepath.purplepath.taxprompt.model;

/**
 * Created by dinesh on 25/01/18.
 */

public class DeleteInput {

    private String user_id;

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
        return "ClassPojo [user_id = "+user_id+"]";
    }
}
