package com.purplepath.purplepath.settings.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 05-01-2017.
 */

public class Data implements Serializable
{
    private String message;

   // private Sas_assumptions[] sas_assumptions;

    private ArrayList<Sas_assumptions> sas_assumptions;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Sas_assumptions> getSas_assumptions ()
    {
        return sas_assumptions;
    }

    public void setSas_assumptions (ArrayList<Sas_assumptions> sas_assumptions)
    {
        this.sas_assumptions = sas_assumptions;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", sas_assumptions = "+sas_assumptions+"]";
    }
}


