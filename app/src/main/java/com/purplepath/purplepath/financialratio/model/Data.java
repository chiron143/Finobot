package com.purplepath.purplepath.financialratio.model;

import java.io.Serializable;

/**
 * Created by dinesh on 28/04/17.
 */

public class Data implements Serializable {

    private String message;

    private Ratios ratios;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Ratios getRatios ()
    {
        return ratios;
    }

    public void setRatios (Ratios ratios)
    {
        this.ratios = ratios;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", ratios = "+ratios+"]";
    }

}
