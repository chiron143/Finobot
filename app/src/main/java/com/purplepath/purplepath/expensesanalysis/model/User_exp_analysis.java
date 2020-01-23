package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class User_exp_analysis implements Serializable {
    private Fam_det fam_det;

    private String overall_expense;

    private String family_id;

    private String familyname;

    private String expense_percent;

    public Fam_det getFam_det ()
    {
        return fam_det;
    }

    public void setFam_det (Fam_det fam_det)
    {
        this.fam_det = fam_det;
    }

    public String getOverall_expense ()
    {
        return overall_expense;
    }

    public void setOverall_expense (String overall_expense)
    {
        this.overall_expense = overall_expense;
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

    public String getExpense_percent ()
    {
        return expense_percent;
    }

    public void setExpense_percent (String expense_percent)
    {
        this.expense_percent = expense_percent;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [fam_det = "+fam_det+", overall_expense = "+overall_expense+", family_id = "+family_id+", familyname = "+familyname+", expense_percent = "+expense_percent+"]";
    }
}
