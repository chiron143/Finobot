package com.purplepath.purplepath.desiproAllModules.loanEligibility.view.models;

import java.io.Serializable;

/**
 * Created by Suresh on 23/08/17.
 */

public class Data implements Serializable {

    private String message;

    private String result;

    private String[] non_eligible_array;

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

    public String[] getNon_eligible_array ()
    {
        return non_eligible_array;
    }

    public void setNon_eligible_array (String[] non_eligible_array)
    {
        this.non_eligible_array = non_eligible_array;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", result = "+result+", non_eligible_array = "+non_eligible_array+"]";
    }

}
