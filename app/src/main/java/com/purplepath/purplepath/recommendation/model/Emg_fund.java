package com.purplepath.purplepath.recommendation.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Emg_fund implements Serializable
{
    private String no_of_months;

    private String diff;

    private String status;

    private String amt_accum;

    private String amt_req;

    public String getNo_of_months ()
    {
        return no_of_months;
    }

    public void setNo_of_months (String no_of_months)
    {
        this.no_of_months = no_of_months;
    }

    public String getDiff ()
    {
        return diff;
    }

    public void setDiff (String diff)
    {
        this.diff = diff;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getAmt_accum ()
    {
        return amt_accum;
    }

    public void setAmt_accum (String amt_accum)
    {
        this.amt_accum = amt_accum;
    }

    public String getAmt_req ()
    {
        return amt_req;
    }

    public void setAmt_req (String amt_req)
    {
        this.amt_req = amt_req;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [no_of_months = "+no_of_months+", diff = "+diff+", status = "+status+", amt_accum = "+amt_accum+", amt_req = "+amt_req+"]";
    }
}

