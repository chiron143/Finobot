package com.purplepath.purplepath.expenses.expensesmodel;

import java.io.Serializable;

/**
 * Created by Bert on 29-Jun-16.
 */
public class AddExpenesesUserDetails implements Serializable
{
    private String overall_expense;

    private String exp_type;

    private String family_id;

    private String user_id;

    private AddExpensesDetails exp_details;

    private String notes;

    public String getOverall_expense ()
    {
        return overall_expense;
    }

    public void setOverall_expense (String overall_expense)
    {
        this.overall_expense = overall_expense;
    }

    public String getExp_type ()
    {
        return exp_type;
    }

    public void setExp_type (String exp_type)
    {
        this.exp_type = exp_type;
    }

    public String getFamily_id ()
    {
        return family_id;
    }

    public void setFamily_id (String family_id)
    {
        this.family_id = family_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public AddExpensesDetails getExp_details ()
    {
        return exp_details;
    }

    public void setExp_details (AddExpensesDetails exp_details)
    {
        this.exp_details = exp_details;
    }

    public String getNotes ()
    {
        return notes;
    }

    public void setNotes (String notes)
    {
        this.notes = notes;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [overall_expense = "+overall_expense+", exp_type = "+exp_type+", family_id = "+family_id+", user_id = "+user_id+", exp_details = "+exp_details+", notes = "+notes+"]";
    }

}


