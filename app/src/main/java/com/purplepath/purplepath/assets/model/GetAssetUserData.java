package com.purplepath.purplepath.assets.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 07-Jul-16.
 */
public class GetAssetUserData implements Serializable
{

    private String alloc_flag;

    private String objective;

    private String asset_name;

    private String status;

    private String alloc_to_goal;

    private String family_id;

    private String purchase_val;

    private String years_of_contr;

    private String freq_of_contr;

    private String type;

    private String other_cat;

    private String id;

    private String cat_lev1_id;

    private String current_value;

    private String annual_contr;

    private String cat_lev3_id;

    private String created_datetime;

    private String years_to_maturity;

    private String cat_lev2_id;

    private String user_id;

    private String purchase_date;

    private String modified_datetime;

    private String notes;

    private String is_own_house;

    public String getIs_own_house() {
        return is_own_house;
    }

    public void setIs_own_house(String is_own_house) {
        this.is_own_house = is_own_house;
    }

    private ArrayList<String> empty_flds;

    public ArrayList<String> getEmpty_flds() {
        return empty_flds;
    }

    public void setEmpty_flds(ArrayList<String> empty_flds) {
        this.empty_flds = empty_flds;
    }

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

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
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

    public String getFreq_of_contr ()
    {
        return freq_of_contr;
    }

    public void setFreq_of_contr (String freq_of_contr)
    {
        this.freq_of_contr = freq_of_contr;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getOther_cat ()
{
    return other_cat;
}

    public void setOther_cat (String other_cat)
    {
        this.other_cat = other_cat;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
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

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
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


    public String getAlloc_flag() {
        return alloc_flag;
    }

    public void setAlloc_flag(String alloc_flag) {
        this.alloc_flag = alloc_flag;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [objective = "+objective+", asset_name = "+asset_name+", " +
                "status = "+status+", alloc_to_goal = "+alloc_to_goal+", family_id = "+family_id+", " +
                "purchase_val = "+purchase_val+", years_of_contr = "+years_of_contr+", freq_of_contr =" +
                " "+freq_of_contr+", type = "+type+", other_cat = "+other_cat+", id = "+id+", cat_lev1_id = " +
                ""+cat_lev1_id+", current_value = "+current_value+", annual_contr = "+annual_contr+"," +
                " cat_lev3_id = "+cat_lev3_id+", created_datetime = "+created_datetime+", years_to_maturity = " +
                ""+years_to_maturity+", cat_lev2_id = "+cat_lev2_id+", user_id = "+user_id+", purchase_date = "
                +purchase_date+", modified_datetime = "+modified_datetime+", notes = "+notes+",is_own_house="+is_own_house+"]";
    }
}