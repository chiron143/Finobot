package com.purplepath.purplepath.document.deleteModels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Suresh on 30/06/17.
 */

class DeleteModelsData implements Serializable {

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
