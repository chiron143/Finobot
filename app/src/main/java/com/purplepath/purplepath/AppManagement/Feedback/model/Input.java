package com.purplepath.purplepath.AppManagement.Feedback.model;

import java.io.Serializable;

/**
 * Created by pravinr on 5/25/17.
 */

public class Input implements Serializable {
    private String email;

    private String name;

    private String user_id;

    private String comments;

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getComments ()
    {
        return comments;
    }

    public void setComments (String comments)
    {
        this.comments = comments;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [email = "+email+", name = "+name+", user_id = "+user_id+", comments = "+comments+"]";
    }
}
