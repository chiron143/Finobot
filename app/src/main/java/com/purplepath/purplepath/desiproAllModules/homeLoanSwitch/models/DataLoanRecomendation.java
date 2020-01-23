package com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 05-04-2017.
 */

public class DataLoanRecomendation implements Serializable {

    private String message;

    private Recommend recommend;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Recommend getRecommend ()
    {
        return recommend;
    }

    public void setRecommend (Recommend recommend)
    {
        this.recommend = recommend;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", recommend = "+recommend+"]";
    }
}
