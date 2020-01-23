package com.purplepath.purplepath.insuranceAnalysis.models;

import java.io.Serializable;

/**
 * Created by Suresh on 04/01/17.
 */


public class Data implements Serializable{
    private String message;

    private Ins_plan ins_plan;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Ins_plan getIns_plan ()
    {
        return ins_plan;
    }

    public void setIns_plan (Ins_plan ins_plan)
    {
        this.ins_plan = ins_plan;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", ins_plan = "+ins_plan+"]";
    }
}

