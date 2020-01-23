package com.purplepath.purplepath.incomedetails.fragment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 24/06/16.
 */
public class Income_cat_lev1  implements Serializable {
    private ArrayList<Income_cat_lev2> cat_lev2List;

    private String lev1_name;

    @SerializedName("tb_field_name")
    private String tb_field_name;

    @SerializedName("value")
    private String value;

    @SerializedName("info_value")
    private String info_value;


    public String getInfo_value() {
        return info_value;
    }

    public ArrayList<Income_cat_lev2> getCat_lev2List() {
        return cat_lev2List;
    }

    public void setCat_lev2List(ArrayList<Income_cat_lev2> cat_lev2List) {
        this.cat_lev2List = cat_lev2List;
    }

    public String getValue() {
        return value;
    }

    public String getTb_field_name() {
        return tb_field_name;
    }

    private String id;

    public String getLev1_name ()
    {
        return lev1_name;
    }

    public void setLev1_name (String lev1_name)
    {
        this.lev1_name = lev1_name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lev1_name = "+lev1_name+", id = "+id+"]";
    }
}