package com.purplepath.purplepath.liabilitiesanaysis.model;

import java.io.Serializable;

/**
 * @author Bert on 27-Apr-17.
 */

public class Data implements Serializable {
    private String message;

    private Liab_analysis liab_analysis;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Liab_analysis getLiab_analysis ()
    {
        return liab_analysis;
    }

    public void setLiab_analysis (Liab_analysis liab_analysis)
    {
        this.liab_analysis = liab_analysis;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", liab_analysis = "+liab_analysis+"]";
    }
}
