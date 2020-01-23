package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class Cc_det implements Serializable
{
    private String cc_char_don;

    private String cc_char_don_per;

    public String getCc_char_don ()
{
    return cc_char_don;
}

    public void setCc_char_don (String cc_char_don)
    {
        this.cc_char_don = cc_char_don;
    }

    public String getCc_char_don_per ()
    {
        return cc_char_don_per;
    }

    public void setCc_char_don_per (String cc_char_don_per)
    {
        this.cc_char_don_per = cc_char_don_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cc_char_don = "+cc_char_don+", cc_char_don_per = "+cc_char_don_per+"]";
    }
}
