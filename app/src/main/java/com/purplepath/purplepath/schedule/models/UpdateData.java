package com.purplepath.purplepath.schedule.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 11-07-2017.
 */

public class UpdateData implements Serializable {
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
