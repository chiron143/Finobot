package com.purplepath.purplepath.expenses.expensesmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 28-Jun-16.
 */
public class ExpensesDetailsData implements Serializable
{
    private String message;

    private ArrayList<ExpensesLevelZeroData>  expense_cat_lev0;

    private ArrayList<ExpensesLevelThreeData> expense_cat_lev3;

    private ArrayList<ExpensesLevelOneData> expense_cat_lev1;

    private ArrayList<ExpensesLevelTwoData> expense_cat_lev2;

    public ArrayList<ExpensesLevelZeroData> getExpense_cat_lev0() {
        return expense_cat_lev0;
    }

    public void setExpense_cat_lev0(ArrayList<ExpensesLevelZeroData> expense_cat_lev0) {
        this.expense_cat_lev0 = expense_cat_lev0;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<ExpensesLevelThreeData> getExpense_cat_lev3 ()
    {
        return expense_cat_lev3;
    }

    public void setExpense_cat_lev3 (ArrayList<ExpensesLevelThreeData> expense_cat_lev3)
    {
        this.expense_cat_lev3 = expense_cat_lev3;
    }

    public ArrayList<ExpensesLevelOneData> getExpense_cat_lev1 ()
    {
        return expense_cat_lev1;
    }

    public void setExpense_cat_lev1 (ArrayList<ExpensesLevelOneData> expense_cat_lev1)
    {
        this.expense_cat_lev1 = expense_cat_lev1;
    }

    public ArrayList<ExpensesLevelTwoData> getExpense_cat_lev2 ()
    {
        return expense_cat_lev2;
    }

    public void setExpense_cat_lev2 (ArrayList<ExpensesLevelTwoData> expense_cat_lev2)
    {
        this.expense_cat_lev2 = expense_cat_lev2;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", expense_cat_lev3 = "+expense_cat_lev3+", expense_cat_lev1 = "+expense_cat_lev1+", expense_cat_lev2 = "+expense_cat_lev2+"]";
    }
}