package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;


public class AddInsuranceData implements Serializable
{
    private String message;

    private AddInsuranceInputData input;

    private String ins_id;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public AddInsuranceInputData getInput ()
    {
        return input;
    }

    public void setInput (AddInsuranceInputData input)
    {
        this.input = input;
    }

    public String getIns_id ()
    {
        return ins_id;
    }

    public void setIns_id (String ins_id)
    {
        this.ins_id = ins_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", input = "+input+", ins_id = "+ins_id+"]";
    }
}