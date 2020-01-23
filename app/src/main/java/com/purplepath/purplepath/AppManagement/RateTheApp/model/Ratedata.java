package com.purplepath.purplepath.AppManagement.RateTheApp.model;

import java.io.Serializable;

/**
 * Created by pravinr on 5/25/17.
 */

public class Ratedata implements Serializable {
    private String message;

    private String user_rating;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getUser_rating ()
    {
        return user_rating;
    }

    public void setUser_rating (String user_rating)
    {
        this.user_rating = user_rating;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_rating = "+user_rating+"]";
    }
}

