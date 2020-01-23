package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 08-Jul-16.
 */
public class GetInsuranceData implements Serializable
{
    private String message;

    private ArrayList<GetInsuranceInputData> user_insurance;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<GetInsuranceInputData> getUser_insurance ()
    {
        return user_insurance;
    }

    public void setUser_insurance (ArrayList<GetInsuranceInputData> user_insurance)
    {
        this.user_insurance = user_insurance;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_insurance = "+user_insurance+"]";
    }
}