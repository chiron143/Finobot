package com.purplepath.purplepath.incomechartdetail.model;

import java.io.Serializable;

/**
 * Created by dinesh on 18/07/16.
 */
public class Ib_det implements Serializable {

    private String ib_busi_inc2;

    private String ib_busi_inc1;

    private String ib_busi_inc1_per;

    private String ib_busi_inc2_per;

    public String getIb_busi_inc2 ()
    {
        return ib_busi_inc2;
    }

    public void setIb_busi_inc2 (String ib_busi_inc2)
    {
        this.ib_busi_inc2 = ib_busi_inc2;
    }

    public String getIb_busi_inc1 ()
    {
        return ib_busi_inc1;
    }

    public void setIb_busi_inc1 (String ib_busi_inc1)
    {
        this.ib_busi_inc1 = ib_busi_inc1;
    }

    public String getIb_busi_inc1_per ()
    {
        return ib_busi_inc1_per;
    }

    public void setIb_busi_inc1_per (String ib_busi_inc1_per)
    {
        this.ib_busi_inc1_per = ib_busi_inc1_per;
    }

    public String getIb_busi_inc2_per ()
    {
        return ib_busi_inc2_per;
    }

    public void setIb_busi_inc2_per (String ib_busi_inc2_per)
    {
        this.ib_busi_inc2_per = ib_busi_inc2_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ib_busi_inc2 = "+ib_busi_inc2+", ib_busi_inc1 = "+ib_busi_inc1+", ib_busi_inc1_per = "+ib_busi_inc1_per+", ib_busi_inc2_per = "+ib_busi_inc2_per+"]";
    }
}
