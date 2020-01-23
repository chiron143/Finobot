package com.purplepath.purplepath.desiproAllModules.timeValueOfMoney.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 26-06-2017.
 */

public class Data implements Serializable {
    private String message;

    private String result;

    private String flag;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getResult ()
    {
        return result;
    }

    public void setResult (String result)
    {
        this.result = result;
    }

    public String getFlag ()
    {
        return flag;
    }

    public void setFlag (String flag)
    {
        this.flag = flag;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", result = "+result+", flag = "+flag+"]";
    }
}
