package com.purplepath.purplepath.assets.model;

import java.io.Serializable;

/**
 * Created by Bert on 07-Jul-16.
 */
public class AddAssetInputData implements Serializable
{
    private String objective;

    private String asset_name;

    public String getAsst_id() {
        return asst_id;
    }

    public void setAsst_id(String asst_id) {
        this.asst_id = asst_id;
    }

    private  String asst_id;

    private String alloc_to_goal;

    private String family_id;

    private String purchase_val;

    private String years_of_contr;

    private String type;

    private String freq_of_contr;

    private String cat_lev1_id;

    private String current_value;

    private String annual_contr;

    private String cat_lev3_id;

    private String years_to_maturity;

    private String cat_lev2_id;

    private String user_id;

    private String purchase_date;

    private String notes;

    public String getObjective ()
    {
        return objective;
    }

    public void setObjective (String objective)
    {
        this.objective = objective;
    }

    public String getAsset_name ()
    {
        return asset_name;
    }

    public void setAsset_name (String asset_name)
    {
        this.asset_name = asset_name;
    }

    public String getAlloc_to_goal ()
    {
        return alloc_to_goal;
    }

    public void setAlloc_to_goal (String alloc_to_goal)
    {
        this.alloc_to_goal = alloc_to_goal;
    }

    public String getFamily_id ()
    {
        return family_id;
    }

    public void setFamily_id (String family_id)
    {
        this.family_id = family_id;
    }

    public String getPurchase_val ()
    {
        return purchase_val;
    }

    public void setPurchase_val (String purchase_val)
    {
        this.purchase_val = purchase_val;
    }

    public String getYears_of_contr ()
    {
        return years_of_contr;
    }

    public void setYears_of_contr (String years_of_contr)
    {
        this.years_of_contr = years_of_contr;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getFreq_of_contr ()
    {
        return freq_of_contr;
    }

    public void setFreq_of_contr (String freq_of_contr)
    {
        this.freq_of_contr = freq_of_contr;
    }

    public String getCat_lev1_id ()
    {
        return cat_lev1_id;
    }

    public void setCat_lev1_id (String cat_lev1_id)
    {
        this.cat_lev1_id = cat_lev1_id;
    }

    public String getCurrent_value ()
    {
        return current_value;
    }

    public void setCurrent_value (String current_value)
    {
        this.current_value = current_value;
    }

    public String getAnnual_contr ()
    {
        return annual_contr;
    }

    public void setAnnual_contr (String annual_contr)
    {
        this.annual_contr = annual_contr;
    }

    public String getCat_lev3_id ()
    {
        return cat_lev3_id;
    }

    public void setCat_lev3_id (String cat_lev3_id)
    {
        this.cat_lev3_id = cat_lev3_id;
    }

    public String getYears_to_maturity ()
    {
        return years_to_maturity;
    }

    public void setYears_to_maturity (String years_to_maturity)
    {
        this.years_to_maturity = years_to_maturity;
    }

    public String getCat_lev2_id ()
    {
        return cat_lev2_id;
    }

    public void setCat_lev2_id (String cat_lev2_id)
    {
        this.cat_lev2_id = cat_lev2_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getPurchase_date ()
    {
        return purchase_date;
    }

    public void setPurchase_date (String purchase_date)
    {
        this.purchase_date = purchase_date;
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
        return "ClassPojo [objective = "+objective+", asset_name = "+asset_name+", alloc_to_goal = "+alloc_to_goal+", family_id = "+family_id+", purchase_val = "+purchase_val+", years_of_contr = "+years_of_contr+", type = "+type+", freq_of_contr = "+freq_of_contr+", cat_lev1_id = "+cat_lev1_id+", current_value = "+current_value+", annual_contr = "+annual_contr+", cat_lev3_id = "+cat_lev3_id+", years_to_maturity = "+years_to_maturity+", cat_lev2_id = "+cat_lev2_id+", user_id = "+user_id+", purchase_date = "+purchase_date+", notes = "+notes+"]";
    }
}