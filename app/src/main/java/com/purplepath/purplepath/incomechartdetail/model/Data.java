package com.purplepath.purplepath.incomechartdetail.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 18/07/16.
 */
public class Data implements Serializable {
    private String message;

    private ArrayList<User_inc_analysis> user_inc_analysis;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<User_inc_analysis> getUser_inc_analysis ()
    {
        return user_inc_analysis;
    }

    public void setUser_inc_analysis (ArrayList<User_inc_analysis> user_inc_analysis)
    {
        this.user_inc_analysis = user_inc_analysis;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_inc_analysis = "+user_inc_analysis+"]";
    }
}
