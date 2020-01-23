package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 17-Jul-16.
 */
public class ExpensesAnalysisData implements Serializable{
    private String message;

    private ArrayList<User_exp_analysis> user_exp_analysis;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<User_exp_analysis> getUser_exp_analysis ()
    {
        return user_exp_analysis;
    }

    public void setUser_exp_analysis (ArrayList<User_exp_analysis> user_exp_analysis)
    {
        this.user_exp_analysis = user_exp_analysis;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_exp_analysis = "+user_exp_analysis+"]";
    }
}

