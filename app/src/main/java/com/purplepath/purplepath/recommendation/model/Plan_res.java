package com.purplepath.purplepath.recommendation.model;

import java.io.Serializable;

/**
 * Created by pravinr on 9/1/17.
 */

public class Plan_res implements Serializable {
    private String ins_type;

    private String coverage;

    private String annual_prem;

    private String ins_sub_type;

    private String ins_prod_type;

    public String getIns_type ()
    {
        return ins_type;
    }

    public void setIns_type (String ins_type)
    {
        this.ins_type = ins_type;
    }

    public String getCoverage ()
    {
        return coverage;
    }

    public void setCoverage (String coverage)
    {
        this.coverage = coverage;
    }

    public String getAnnual_prem ()
    {
        return annual_prem;
    }

    public void setAnnual_prem (String annual_prem)
    {
        this.annual_prem = annual_prem;
    }

    public String getIns_sub_type ()
    {
        return ins_sub_type;
    }

    public void setIns_sub_type (String ins_sub_type)
    {
        this.ins_sub_type = ins_sub_type;
    }

    public String getIns_prod_type ()
    {
        return ins_prod_type;
    }

    public void setIns_prod_type (String ins_prod_type)
    {
        this.ins_prod_type = ins_prod_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ins_type = "+ins_type+", coverage = "+coverage+", annual_prem = "+annual_prem+", ins_sub_type = "+ins_sub_type+", ins_prod_type = "+ins_prod_type+"]";
    }
}

