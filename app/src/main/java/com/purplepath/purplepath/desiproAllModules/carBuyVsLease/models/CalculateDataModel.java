package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models;

/**
 * Created by bertrandrussellsakthees on 24/01/18.
 */

public class CalculateDataModel {
    private String message;

    private String result;

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

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", result = "+result+"]";
    }

}
