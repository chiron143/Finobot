package com.purplepath.purplepath.expensesRedesign.updateexpensedetailsmodel;

import java.io.Serializable;

/**
 * Created by srinivasan on 9/1/2016.
 */
public class Input implements Serializable
{
    private String level_1_ids;

    private String exp_id;

    private String overall_expense;

    private String level_3_ids;

    private String level_2_ids;

    public String getLevel_0_ids() {
        return level_0_ids;
    }

    public void setLevel_0_ids(String level_0_ids) {
        this.level_0_ids = level_0_ids;
    }

    private String level_0_ids;

    private String exp_type;

    private String family_id;

    private String user_id;

    private Exp_details exp_details;

    private String notes;

    public String getLevel_1_ids ()
    {
        return level_1_ids;
    }

    public void setLevel_1_ids (String level_1_ids)
    {
        this.level_1_ids = level_1_ids;
    }

    public String getExp_id ()
    {
        return exp_id;
    }

    public void setExp_id (String exp_id)
    {
        this.exp_id = exp_id;
    }

    public String getOverall_expense ()
    {
        return overall_expense;
    }

    public void setOverall_expense (String overall_expense)
    {
        this.overall_expense = overall_expense;
    }

    public String getLevel_3_ids ()
    {
        return level_3_ids;
    }

    public void setLevel_3_ids (String level_3_ids)
    {
        this.level_3_ids = level_3_ids;
    }

    public String getLevel_2_ids ()
    {
        return level_2_ids;
    }

    public void setLevel_2_ids (String level_2_ids)
    {
        this.level_2_ids = level_2_ids;
    }

    public String getExp_type ()
    {
        return exp_type;
    }

    public void setExp_type (String exp_type)
    {
        this.exp_type = exp_type;
    }

    public String getFamily_id ()
    {
        return family_id;
    }

    public void setFamily_id (String family_id)
    {
        this.family_id = family_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public Exp_details getExp_details ()
    {
        return exp_details;
    }

    public void setExp_details (Exp_details exp_details)
    {
        this.exp_details = exp_details;
    }

    public String getNotes ()
    {
        return notes;
    }

    public void setNotes (String notes)
    {
        this.notes = notes;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [level_1_ids = "+level_1_ids+", exp_id = "+exp_id+", overall_expense = "+overall_expense+", level_3_ids = "+level_3_ids+", level_2_ids = "+level_2_ids+", exp_type = "+exp_type+", family_id = "+family_id+", user_id = "+user_id+", exp_details = "+exp_details+", notes = "+notes+"]";
    }
}
