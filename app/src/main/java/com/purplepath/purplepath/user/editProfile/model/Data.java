package com.purplepath.purplepath.user.editProfile.model;

import java.io.Serializable;

/**
 * Created by pravinr on 5/18/17.
 */

public class Data implements Serializable {

        private String message;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+"]";
    }
}