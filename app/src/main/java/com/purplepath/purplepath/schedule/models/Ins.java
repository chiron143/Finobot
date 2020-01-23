package com.purplepath.purplepath.schedule.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 06-07-2017.
 */

public class Ins implements Serializable {

    private String id;

    private String ins_type;

    private String flag;

    private String next_prem_date;

    private String coverage;

    private String annual_prem;

    private String family_id;

    private String policy_name;

    private String user_id;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIns_type ()
    {
        return ins_type;
    }

    public void setIns_type (String ins_type)
    {
        this.ins_type = ins_type;
    }

    public String getFlag ()
    {
        return flag;
    }

    public void setFlag (String flag)
    {
        this.flag = flag;
    }

    public String getNext_prem_date ()
    {
        return next_prem_date;
    }

    public void setNext_prem_date (String next_prem_date)
    {
        this.next_prem_date = next_prem_date;
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

    public String getFamily_id ()
    {
        return family_id;
    }

    public void setFamily_id (String family_id)
    {
        this.family_id = family_id;
    }

    public String getPolicy_name ()
    {
        return policy_name;
    }

    public void setPolicy_name (String policy_name)
    {
        this.policy_name = policy_name;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", ins_type = "+ins_type+", flag = "+flag+", next_prem_date = "+next_prem_date+", coverage = "+coverage+", annual_prem = "+annual_prem+", family_id = "+family_id+", policy_name = "+policy_name+", user_id = "+user_id+"]";
    }
}
