package com.purplepath.purplepath.AppManagement.Payment.getdiscountmodel;

import java.io.Serializable;

public class Data implements Serializable{
    private String message;

    private String discount_per;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getDiscount_per ()
    {
        return discount_per;
    }

    public void setDiscount_per (String discount_per)
    {
        this.discount_per = discount_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", discount_per = "+discount_per+"]";
    }
}

