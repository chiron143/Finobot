package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;

/**
 * Created by Bert on 08-Jul-16.
 */
public class AddInsuranceInputData implements Serializable
{
    private String ins_sub_det;

    private String family_id;

    private String ins_sub_type;

    private String term_years;

    private String last_paid_date;

    private String ins_prod_type;

    private String ins_freq;

    private String ins_type;

    private String next_prem_date;

    private String policy_end_date;

    private String maturity_date;

    private String coverage;

    private String last_prem_date;

    private String annual_prem;

    private String exp_incr;

    private String policy_name;

    private String user_id;

    private String notes;

    private String prem_due_date;

    private String plan_type;

    private String policy_issue_date;

    public String getIns_sub_det ()
    {
        return ins_sub_det;
    }

    public void setIns_sub_det (String ins_sub_det)
    {
        this.ins_sub_det = ins_sub_det;
    }

    public String getFamily_id ()
    {
        return family_id;
    }

    public void setFamily_id (String family_id)
    {
        this.family_id = family_id;
    }

    public String getIns_sub_type ()
    {
        return ins_sub_type;
    }

    public void setIns_sub_type (String ins_sub_type)
    {
        this.ins_sub_type = ins_sub_type;
    }

    public String getTerm_years ()
    {
        return term_years;
    }

    public void setTerm_years (String term_years)
    {
        this.term_years = term_years;
    }

    public String getLast_paid_date ()
    {
        return last_paid_date;
    }

    public void setLast_paid_date (String last_paid_date)
    {
        this.last_paid_date = last_paid_date;
    }

    public String getIns_prod_type ()
    {
        return ins_prod_type;
    }

    public void setIns_prod_type (String ins_prod_type)
    {
        this.ins_prod_type = ins_prod_type;
    }

    public String getIns_freq ()
    {
        return ins_freq;
    }

    public void setIns_freq (String ins_freq)
    {
        this.ins_freq = ins_freq;
    }

    public String getIns_type ()
    {
        return ins_type;
    }

    public void setIns_type (String ins_type)
    {
        this.ins_type = ins_type;
    }

    public String getNext_prem_date ()
    {
        return next_prem_date;
    }

    public void setNext_prem_date (String next_prem_date)
    {
        this.next_prem_date = next_prem_date;
    }

    public String getPolicy_end_date ()
    {
        return policy_end_date;
    }

    public void setPolicy_end_date (String policy_end_date)
    {
        this.policy_end_date = policy_end_date;
    }

    public String getMaturity_date ()
    {
        return maturity_date;
    }

    public void setMaturity_date (String maturity_date)
    {
        this.maturity_date = maturity_date;
    }

    public String getCoverage ()
    {
        return coverage;
    }

    public void setCoverage (String coverage)
    {
        this.coverage = coverage;
    }

    public String getLast_prem_date ()
    {
        return last_prem_date;
    }

    public void setLast_prem_date (String last_prem_date)
    {
        this.last_prem_date = last_prem_date;
    }

    public String getAnnual_prem ()
    {
        return annual_prem;
    }

    public void setAnnual_prem (String annual_prem)
    {
        this.annual_prem = annual_prem;
    }

    public String getExp_incr ()
    {
        return exp_incr;
    }

    public void setExp_incr (String exp_incr)
    {
        this.exp_incr = exp_incr;
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

    public String getNotes ()
    {
        return notes;
    }

    public void setNotes (String notes)
    {
        this.notes = notes;
    }

    public String getPrem_due_date ()
    {
        return prem_due_date;
    }

    public void setPrem_due_date (String prem_due_date)
    {
        this.prem_due_date = prem_due_date;
    }

    public String getPlan_type ()
    {
        return plan_type;
    }

    public void setPlan_type (String plan_type)
    {
        this.plan_type = plan_type;
    }

    public String getPolicy_issue_date ()
    {
        return policy_issue_date;
    }

    public void setPolicy_issue_date (String policy_issue_date)
    {
        this.policy_issue_date = policy_issue_date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ins_sub_det = "+ins_sub_det+", family_id = "+family_id+", ins_sub_type = "+ins_sub_type+", term_years = "+term_years+", last_paid_date = "+last_paid_date+", ins_prod_type = "+ins_prod_type+", ins_freq = "+ins_freq+", ins_type = "+ins_type+", next_prem_date = "+next_prem_date+", policy_end_date = "+policy_end_date+", maturity_date = "+maturity_date+", coverage = "+coverage+", last_prem_date = "+last_prem_date+", annual_prem = "+annual_prem+", exp_incr = "+exp_incr+", policy_name = "+policy_name+", user_id = "+user_id+", notes = "+notes+", prem_due_date = "+prem_due_date+", plan_type = "+plan_type+", policy_issue_date = "+policy_issue_date+"]";
    }
}

