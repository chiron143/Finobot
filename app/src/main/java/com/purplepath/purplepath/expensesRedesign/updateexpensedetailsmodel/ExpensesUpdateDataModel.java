package com.purplepath.purplepath.expensesRedesign.updateexpensedetailsmodel;

import com.purplepath.purplepath.expensesRedesign.expensesredesignmodel.Expense_cat_lev0;

import java.util.ArrayList;

/**
 * Created by srinivasan on 9/1/2016.
 */

public class ExpensesUpdateDataModel
{
    private String message;

    private String exp_id;

    private Input input;

    private ArrayList<Expense_cat_lev0> expense_cat_lev0;

    public ArrayList<Expense_cat_lev0> getExpense_cat_lev0() {
        return expense_cat_lev0;
    }

    public void setExpense_cat_lev0(ArrayList<Expense_cat_lev0> expense_cat_lev0) {
        this.expense_cat_lev0 = expense_cat_lev0;
    }

    private ArrayList<Expense_cat_lev3> expense_cat_lev3;

    private  ArrayList<Expense_cat_lev1> expense_cat_lev1;

    private ArrayList<Expense_cat_lev2> expense_cat_lev2;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getExp_id ()
    {
        return exp_id;
    }

    public void setExp_id (String exp_id)
    {
        this.exp_id = exp_id;
    }

    public Input getInput ()
    {
        return input;
    }

    public void setInput (Input input)
    {
        this.input = input;
    }

    public ArrayList<Expense_cat_lev3> getExpense_cat_lev3 ()
    {
        return expense_cat_lev3;
    }

    public void setExpense_cat_lev3 (ArrayList<Expense_cat_lev3> expense_cat_lev3)
    {
        this.expense_cat_lev3 = expense_cat_lev3;
    }

    public ArrayList<Expense_cat_lev1> getExpense_cat_lev1 ()
    {
        return expense_cat_lev1;
    }

    public void setExpense_cat_lev1 (ArrayList<Expense_cat_lev1> expense_cat_lev1)
    {
        this.expense_cat_lev1 = expense_cat_lev1;
    }

    public ArrayList<Expense_cat_lev2> getExpense_cat_lev2 ()
    {
        return expense_cat_lev2;
    }

    public void setExpense_cat_lev2 (ArrayList<Expense_cat_lev2> expense_cat_lev2)
    {
        this.expense_cat_lev2 = expense_cat_lev2;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", exp_id = "+exp_id+", input = "+input+", expense_cat_lev3 = "+expense_cat_lev3+", expense_cat_lev1 = "+expense_cat_lev1+", expense_cat_lev2 = "+expense_cat_lev2+"]";
    }
}
