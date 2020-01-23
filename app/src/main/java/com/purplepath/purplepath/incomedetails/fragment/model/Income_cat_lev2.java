package com.purplepath.purplepath.incomedetails.fragment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 24/06/16.
 */
public class Income_cat_lev2  implements Serializable{

    @SerializedName("info_value")
    private String info_value;


    public String getInfo_value() {
        return info_value;
    }

    private ArrayList<Income_cat_lev3> income_cat_lev3List;
    private String id;

    public ArrayList<Income_cat_lev3> getIncome_cat_lev3List() {
        return income_cat_lev3List;
    }

    public void setIncome_cat_lev3List(ArrayList<Income_cat_lev3> income_cat_lev3List) {
        this.income_cat_lev3List = income_cat_lev3List;
    }
    @SerializedName("tb_field_name")
    private  String tb_field_name;

    public String getTb_field_name() {
        return tb_field_name;
    }

    @SerializedName("value")
    private String value;

    public String getValue() {
        return value;
    }

    private String lev2_name;

    private String lev1_id;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLev2_name ()
    {
        return lev2_name;
    }

    public void setLev2_name (String lev2_name)
    {
        this.lev2_name = lev2_name;
    }

    public String getLev1_id ()
    {
        return lev1_id;
    }

    public void setLev1_id (String lev1_id)
    {
        this.lev1_id = lev1_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", lev2_name = "+lev2_name+", lev1_id = "+lev1_id+"]";
    }
}

