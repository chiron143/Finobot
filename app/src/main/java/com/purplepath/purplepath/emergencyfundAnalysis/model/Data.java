package com.purplepath.purplepath.emergencyfundAnalysis.model;

import java.io.Serializable;

/**
 * Created by dinesh on 12/10/16.
 */
public class Data implements Serializable {
    private String message;

    private Ef_result ef_result;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Ef_result getEf_result ()
    {
        return ef_result;
    }

    public void setEf_result (Ef_result ef_result)
    {
        this.ef_result = ef_result;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", ef_result = "+ef_result+"]";
    }
}
