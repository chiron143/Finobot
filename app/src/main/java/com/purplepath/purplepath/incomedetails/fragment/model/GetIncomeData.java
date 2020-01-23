package com.purplepath.purplepath.incomedetails.fragment.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 26/07/16.
 */
public class GetIncomeData implements Serializable
    {
        private String message;

        private ArrayList<User_incomes> user_incomes;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<User_incomes> getUser_incomes ()
    {
        return user_incomes;
    }

    public void setUser_incomes (ArrayList<User_incomes> user_incomes)
    {
        this.user_incomes = user_incomes;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_incomes = "+user_incomes+"]";
    }
}

