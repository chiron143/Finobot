package com.purplepath.purplepath.taxprepaid.gettaxprepaidmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 1/10/18.
 */

public class Pre_tax implements Serializable{
    private String id;

    private String total_tds_deducted;

    private String total_advance_tax_paid;

    private String created_datetime;

    private String mat_credit;

    private String total_self_assessment_tax_paid;

    private String user_id;

    private String modified_datetime;

    private String notes;

    private String amt_credit;

    private String total_tcs_deducted;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTotal_tds_deducted ()
    {
        return total_tds_deducted;
    }

    public void setTotal_tds_deducted (String total_tds_deducted)
    {
        this.total_tds_deducted = total_tds_deducted;
    }

    public String getTotal_advance_tax_paid ()
    {
        return total_advance_tax_paid;
    }

    public void setTotal_advance_tax_paid (String total_advance_tax_paid)
    {
        this.total_advance_tax_paid = total_advance_tax_paid;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getMat_credit ()
    {
        return mat_credit;
    }

    public void setMat_credit (String mat_credit)
    {
        this.mat_credit = mat_credit;
    }

    public String getTotal_self_assessment_tax_paid ()
    {
        return total_self_assessment_tax_paid;
    }

    public void setTotal_self_assessment_tax_paid (String total_self_assessment_tax_paid)
    {
        this.total_self_assessment_tax_paid = total_self_assessment_tax_paid;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getNotes ()
    {
        return notes;
    }

    public void setNotes (String notes)
    {
        this.notes = notes;
    }

    public String getAmt_credit ()
    {
        return amt_credit;
    }

    public void setAmt_credit (String amt_credit)
    {
        this.amt_credit = amt_credit;
    }

    public String getTotal_tcs_deducted ()
    {
        return total_tcs_deducted;
    }

    public void setTotal_tcs_deducted (String total_tcs_deducted)
    {
        this.total_tcs_deducted = total_tcs_deducted;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", total_tds_deducted = "+total_tds_deducted+", total_advance_tax_paid = "+total_advance_tax_paid+", created_datetime = "+created_datetime+", mat_credit = "+mat_credit+", total_self_assessment_tax_paid = "+total_self_assessment_tax_paid+", user_id = "+user_id+", modified_datetime = "+modified_datetime+", notes = "+notes+", amt_credit = "+amt_credit+", total_tcs_deducted = "+total_tcs_deducted+"]";
    }
}

