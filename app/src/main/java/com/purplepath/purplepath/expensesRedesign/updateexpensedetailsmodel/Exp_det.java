package com.purplepath.purplepath.expensesRedesign.updateexpensedetailsmodel;

/**
 * Created by srinivasan on 9/1/2016.
 */
public class Exp_det
{
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
