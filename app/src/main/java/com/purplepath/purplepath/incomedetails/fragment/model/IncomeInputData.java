package com.purplepath.purplepath.incomedetails.fragment.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 30/06/16.
 */
public class IncomeInputData implements Serializable {
    private String message;
    private ArrayList<Inc_ids> inc_ids;

    public ArrayList<Inc_ids> getInc_ids() {
        return inc_ids;
    }

    public void setInc_ids(ArrayList<Inc_ids> inc_ids) {
        this.inc_ids = inc_ids;
    }

    private Input input;

    private ArrayList<Income_cat_lev2> income_cat_lev2;

    private ArrayList<Income_cat_lev3> income_cat_lev3;

    private ArrayList<Income_cat_lev1> income_cat_lev1;

    public ArrayList<Income_cat_lev2> getIncome_cat_lev2() {
        return income_cat_lev2;
    }

    public void setIncome_cat_lev2(ArrayList<Income_cat_lev2> income_cat_lev2) {
        this.income_cat_lev2 = income_cat_lev2;
    }

    public ArrayList<Income_cat_lev3> getIncome_cat_lev3() {
        return income_cat_lev3;
    }

    public void setIncome_cat_lev3(ArrayList<Income_cat_lev3> income_cat_lev3) {
        this.income_cat_lev3 = income_cat_lev3;
    }

    public ArrayList<Income_cat_lev1> getIncome_cat_lev1() {
        return income_cat_lev1;
    }

    public void setIncome_cat_lev1(ArrayList<Income_cat_lev1> income_cat_lev1) {
        this.income_cat_lev1 = income_cat_lev1;
    }

    private String income_id;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Input getInput ()
    {
        return input;
    }

    public void setInput (Input input)
    {
        this.input = input;
    }

    public String getIncome_id ()
    {
        return income_id;
    }

    public void setIncome_id (String income_id)
    {
        this.income_id = income_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", input = "+input+", income_id = "+income_id+"]";
    }
}
