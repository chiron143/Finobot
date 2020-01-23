package com.purplepath.purplepath.expenses.expensesmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 22-Jul-16.
 */
public class UpdateExpensesDetailData implements Serializable
{
    private String message;

    private ArrayList<UpdateUserExpensesDetail> user_expense;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<UpdateUserExpensesDetail> getuser_expense()
    {
        return user_expense;
    }

    public void setuser_expense(ArrayList<UpdateUserExpensesDetail> user_expense)
    {
        this.user_expense = user_expense;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", updateUser_expensesDetail = "+ user_expense +"]";
    }
}