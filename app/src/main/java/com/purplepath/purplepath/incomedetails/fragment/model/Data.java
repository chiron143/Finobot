package com.purplepath.purplepath.incomedetails.fragment.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 24/06/16.
 */
public class Data  implements Serializable{

    private String message;

    private ArrayList<Income_cat_lev2> income_cat_lev2;

    private ArrayList<Income_cat_lev3> income_cat_lev3;

    private ArrayList<Income_cat_lev1> income_cat_lev1;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Income_cat_lev2> getIncome_cat_lev2 ()
    {
        return income_cat_lev2;
    }

    public void setIncome_cat_lev2 (ArrayList<Income_cat_lev2> income_cat_lev2)
    {
        this.income_cat_lev2 = income_cat_lev2;
    }

    public ArrayList<Income_cat_lev3> getIncome_cat_lev3 ()
    {
        return income_cat_lev3;
    }

    public void setIncome_cat_lev3 (ArrayList<Income_cat_lev3> income_cat_lev3)
    {
        this.income_cat_lev3 = income_cat_lev3;
    }

    public ArrayList<Income_cat_lev1> getIncome_cat_lev1 ()
    {
        return income_cat_lev1;
    }

    public void setIncome_cat_lev1 (ArrayList<Income_cat_lev1> income_cat_lev1)
    {
        this.income_cat_lev1 = income_cat_lev1;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", income_cat_lev2 = "+income_cat_lev2+", income_cat_lev3 = "+income_cat_lev3+", income_cat_lev1 = "+income_cat_lev1+"]";
    }
}