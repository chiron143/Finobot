package com.purplepath.purplepath.expenses.expensesmodel;

import java.io.Serializable;

/**
 * Created by Bert on 29-Jun-16.
 */
public class AddExpensesDetailData implements Serializable
{
    private String message;

    private String exp_id;

    private AddExpenesesUserDetails input;

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

    public AddExpenesesUserDetails getInput ()
    {
        return input;
    }

    public void setInput (AddExpenesesUserDetails input)
    {
        this.input = input;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", exp_id = "+exp_id+", input = "+input+"]";
    }

}