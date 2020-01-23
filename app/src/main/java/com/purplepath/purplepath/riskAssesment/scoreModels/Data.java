package com.purplepath.purplepath.riskAssesment.scoreModels;

import java.io.Serializable;

/**
 * Created by bertrandrussellsakthees on 16/01/17.
 */

public class Data implements Serializable {

    private Message message;

    public Message getMessage ()
    {
        return message;
    }

    public void setMessage (Message message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+"]";
    }
}
