package com.purplepath.purplepath.AppManagement.Payment.upgrademodel;

import java.io.Serializable;

/**
 * Created by pravinr on 4/23/18.
 */

public class Payment_details implements Serializable {

    private String last_paid_amount;

    private String user_paid_type;

    private String expiry_date;

    private String is_paid;

    private String last_paid_date;

    public String getLast_paid_amount ()
    {
        return last_paid_amount;
    }

    public void setLast_paid_amount (String last_paid_amount)
    {
        this.last_paid_amount = last_paid_amount;
    }

    public String getUser_paid_type ()
    {
        return user_paid_type;
    }

    public void setUser_paid_type (String user_paid_type)
    {
        this.user_paid_type = user_paid_type;
    }

    public String getExpiry_date ()
    {
        return expiry_date;
    }

    public void setExpiry_date (String expiry_date)
    {
        this.expiry_date = expiry_date;
    }

    public String getIs_paid ()
    {
        return is_paid;
    }

    public void setIs_paid (String is_paid)
    {
        this.is_paid = is_paid;
    }

    public String getLast_paid_date ()
    {
        return last_paid_date;
    }

    public void setLast_paid_date (String last_paid_date)
    {
        this.last_paid_date = last_paid_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [last_paid_amount = "+last_paid_amount+", user_paid_type = "+user_paid_type+", expiry_date = "+expiry_date+", is_paid = "+is_paid+", last_paid_date = "+last_paid_date+"]";
    }
}

