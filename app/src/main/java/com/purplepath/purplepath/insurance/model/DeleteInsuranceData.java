package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;

/**
 * Created by Bert on 08-Jul-16.
 */
public class DeleteInsuranceData implements Serializable
{
    private String message;

    private DeleteInsuranceInputData input;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public DeleteInsuranceInputData getInput ()
    {
        return input;
    }

    public void setInput (DeleteInsuranceInputData input)
    {
        this.input = input;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", input = "+input+"]";
    }
}