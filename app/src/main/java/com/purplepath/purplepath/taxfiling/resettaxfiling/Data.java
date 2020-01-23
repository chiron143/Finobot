package com.purplepath.purplepath.taxfiling.resettaxfiling;

import java.io.Serializable;

/**
 * Created by pravinr on 7/17/18.
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

