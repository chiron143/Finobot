package com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 30-05-2017.
 */

public class Data implements Serializable {

    private String message;

    private ArrayList<Amtz_sch> amtz_sch;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Amtz_sch> getAmtz_sch ()
    {
        return amtz_sch;
    }

    public void setAmtz_sch (ArrayList<Amtz_sch> amtz_sch)
    {
        this.amtz_sch = amtz_sch;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", amtz_sch = "+amtz_sch+"]";
    }
}
