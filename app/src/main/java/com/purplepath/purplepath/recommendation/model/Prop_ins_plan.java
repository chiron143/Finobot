package com.purplepath.purplepath.recommendation.model;

import java.io.Serializable;

/**
 * Created by pravinr on 9/4/17.
 */

public class Prop_ins_plan implements Serializable{

    private String cover_required;

    private String ins_type;

    private String annual_prem;

    private String cover_recommended;

    private String ins_sub_type;

    private String ins_prod_type;

    private String cover_availed;

    public String getCover_required ()
    {
        return cover_required;
    }

    public void setCover_required (String cover_required)
    {
        this.cover_required = cover_required;
    }

    public String getIns_type ()
    {
        return ins_type;
    }

    public void setIns_type (String ins_type)
    {
        this.ins_type = ins_type;
    }

    public String getAnnual_prem ()
    {
        return annual_prem;
    }

    public void setAnnual_prem (String annual_prem)
    {
        this.annual_prem = annual_prem;
    }

    public String getCover_recommended ()
    {
        return cover_recommended;
    }

    public void setCover_recommended (String cover_recommended)
    {
        this.cover_recommended = cover_recommended;
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

    public String getCover_availed ()
    {
        return cover_availed;
    }

    public void setCover_availed (String cover_availed)
    {
        this.cover_availed = cover_availed;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cover_required = "+cover_required+", ins_type = "+ins_type+", annual_prem = "+annual_prem+", cover_recommended = "+cover_recommended+", ins_sub_type = "+ins_sub_type+", ins_prod_type = "+ins_prod_type+", cover_availed = "+cover_availed+"]";
    }
}


