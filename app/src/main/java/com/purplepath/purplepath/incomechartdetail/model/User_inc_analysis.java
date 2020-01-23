package com.purplepath.purplepath.incomechartdetail.model;

import java.io.Serializable;

/**
 * Created by dinesh on 18/07/16.
 */
public class User_inc_analysis implements Serializable {
    private Fam_det fam_det;

    private String overall_income;

    private String income_percent;

    private String family_id;

    private String familyname;

    public Fam_det getFam_det ()
    {
        return fam_det;
    }

    public void setFam_det (Fam_det fam_det)
    {
        this.fam_det = fam_det;
    }

    public String getOverall_income ()
    {
        return overall_income;
    }

    public void setOverall_income (String overall_income)
    {
        this.overall_income = overall_income;
    }

    public String getIncome_percent ()
    {
        return income_percent;
    }

    public void setIncome_percent (String income_percent)
    {
        this.income_percent = income_percent;
    }

    public String getFamily_id ()
    {
        return family_id;
    }

    public void setFamily_id (String family_id)
    {
        this.family_id = family_id;
    }

    public String getFamilyname ()
    {
        return familyname;
    }

    public void setFamilyname (String familyname)
    {
        this.familyname = familyname;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [fam_det = "+fam_det+", overall_income = "+overall_income+", income_percent = "+income_percent+", family_id = "+family_id+", familyname = "+familyname+"]";
    }
}
