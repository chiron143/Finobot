package com.purplepath.purplepath.AppManagement.Feedback.model;

import java.io.Serializable;

/**
 * Created by pravinr on 5/25/17.
 */

public class Feedbackdata implements Serializable {
    private String message;

    private String fid;

    private Input input;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getFid ()
    {
        return fid;
    }

    public void setFid (String fid)
    {
        this.fid = fid;
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
        return "ClassPojo [message = "+message+", fid = "+fid+", input = "+input+"]";
    }
}

