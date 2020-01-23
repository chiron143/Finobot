package com.purplepath.purplepath.incomedetails.fragment.model;

import java.io.Serializable;

/**
 * Created by dinesh on 30/06/16.
 */
public class Inc_det implements Serializable{
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