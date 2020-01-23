package com.purplepath.purplepath.goaltimeline.getGoalPlanModel;

import java.io.Serializable;

/**
 * Created by Suresh on 08/11/17.
 */

public class Goal_plan_cal implements Serializable
{
    private String fv;

    private String pmt_yearly;

    private String pmt_lumpsum;

    private String annu_due_pv;

    private String pmt_monthly;

    public String getFv ()
    {
        return fv;
    }

    public void setFv (String fv)
    {
        this.fv = fv;
    }

    public String getPmt_yearly ()
    {
        return pmt_yearly;
    }

    public void setPmt_yearly (String pmt_yearly)
    {
        this.pmt_yearly = pmt_yearly;
    }

    public String getPmt_lumpsum ()
    {
        return pmt_lumpsum;
    }

    public void setPmt_lumpsum (String pmt_lumpsum)
    {
        this.pmt_lumpsum = pmt_lumpsum;
    }

    public String getAnnu_due_pv ()
    {
        return annu_due_pv;
    }

    public void setAnnu_due_pv (String annu_due_pv)
    {
        this.annu_due_pv = annu_due_pv;
    }

    public String getPmt_monthly ()
    {
        return pmt_monthly;
    }

    public void setPmt_monthly (String pmt_monthly)
    {
        this.pmt_monthly = pmt_monthly;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [fv = "+fv+", pmt_yearly = "+pmt_yearly+", pmt_lumpsum = "+pmt_lumpsum+", annu_due_pv = "+annu_due_pv+", pmt_monthly = "+pmt_monthly+"]";
    }
}

