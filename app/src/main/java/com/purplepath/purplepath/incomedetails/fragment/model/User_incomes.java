package com.purplepath.purplepath.incomedetails.fragment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 26/07/16.
 */
public class User_incomes implements Serializable {
    private ArrayList<Income_cat_lev2> income_cat_lev2;

    private ArrayList<Income_cat_lev3> income_cat_lev3;

    private String level_1_ids;

    private String level_3_ids;

    private String level_2_ids;

    private ArrayList<Income_cat_lev1> income_cat_lev1;

    private String family_name;

    private String family_id;

    @SerializedName("over_all_total")
    private String over_all_total;

    public String getOver_all_total() {
        return over_all_total;
    }

    public ArrayList<Income_cat_lev2> getIncome_cat_lev2 ()
    {
        return income_cat_lev2;
    }

    public void setIncome_cat_lev2 (ArrayList<Income_cat_lev2> income_cat_lev2)
    {
        this.income_cat_lev2 = income_cat_lev2;
    }

    public ArrayList<Income_cat_lev3> getIncome_cat_lev3 ()
    {
        return income_cat_lev3;
    }

    public void setIncome_cat_lev3 (ArrayList<Income_cat_lev3> income_cat_lev3)
    {
        this.income_cat_lev3 = income_cat_lev3;
    }

    public String getLevel_1_ids ()
    {
        return level_1_ids;
    }

    public void setLevel_1_ids (String level_1_ids)
    {
        this.level_1_ids = level_1_ids;
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

    public ArrayList<Income_cat_lev1> getIncome_cat_lev1 ()
    {
        return income_cat_lev1;
    }

    public void setIncome_cat_lev1 (ArrayList<Income_cat_lev1> income_cat_lev1)
    {
        this.income_cat_lev1 = income_cat_lev1;
    }

    public String getFamily_id ()
    {
        return family_id;
    }

    public void setFamily_id (String family_id)
    {
        this.family_id = family_id;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [income_cat_lev2 = "+income_cat_lev2+", income_cat_lev3 = "+income_cat_lev3+", level_1_ids = "+level_1_ids+", level_3_ids = "+level_3_ids+", level_2_ids = "+level_2_ids+", income_cat_lev1 = "+income_cat_lev1+", family_id = "+family_id+"]";
    }
}
