package com.purplepath.purplepath.chatprompt.insertmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 12/14/17.
 */

public class InsertData implements Serializable {
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

