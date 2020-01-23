package com.purplepath.purplepath.propertyinsurance.motorinsurancemodel;

import java.io.Serializable;

/**
 * Created by pravinr on 11/22/17.
 */

public class Ins_by_motor_type implements Serializable {
    private String ins_type;

    private String annual_prem;

    private String ins_sub_type;

    private String ins_prod_type;

    private String motor_type;

    private String cover_availed;

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

    public String getMotor_type ()
    {
        return motor_type;
    }

    public void setMotor_type (String motor_type)
    {
        this.motor_type = motor_type;
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
        return "ClassPojo [ins_type = "+ins_type+", annual_prem = "+annual_prem+", ins_sub_type = "+ins_sub_type+", ins_prod_type = "+ins_prod_type+", motor_type = "+motor_type+", cover_availed = "+cover_availed+"]";
    }
}

