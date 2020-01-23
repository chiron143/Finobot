package com.purplepath.purplepath.taxprompt.model;

import java.io.Serializable;

/**
 * Created by dinesh on 29/12/17.
 */

public class TaxPromptAnserData  implements Serializable{

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
