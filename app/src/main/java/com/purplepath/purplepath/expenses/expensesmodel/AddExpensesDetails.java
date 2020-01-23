package com.purplepath.purplepath.expenses.expensesmodel;

import java.io.Serializable;

/**
 * Created by Bert on 01-Jul-16.
 */
public class AddExpensesDetails implements Serializable {

    private String field;

    private String value;

    public String getField ()
    {
        return field;
    }

    public void setField (String field)
    {
        this.field = field;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [field = "+field+", value = "+value+"]";
    }
}
