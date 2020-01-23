package com.purplepath.purplepath.riskAssesment.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 26-01-2017.
 */

public class Risk_appetite implements Serializable {

    private String result;

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
        return "ClassPojo [result = "+result+"]";
    }
}
