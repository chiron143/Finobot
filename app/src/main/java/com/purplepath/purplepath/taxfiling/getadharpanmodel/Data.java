package com.purplepath.purplepath.taxfiling.getadharpanmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 5/22/18.
 */

public class Data implements Serializable {
    private String message;

    private Input input;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Input getInput ()
    {
        return input;
    }

    public void setInput (Input input)
    {
        this.input = input;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", input = "+input+"]";
    }
}

