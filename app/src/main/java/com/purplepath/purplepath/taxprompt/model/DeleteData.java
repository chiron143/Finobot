package com.purplepath.purplepath.taxprompt.model;

import java.io.Serializable;

/**
 * Created by dinesh on 25/01/18.
 */

public class DeleteData implements Serializable {
    private String message;

    private DeleteData input;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public DeleteData getInput ()
    {
        return input;
    }

    public void setInput (DeleteData input)
    {
        this.input = input;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", input = "+input+"]";
    }
}
