package com.purplepath.purplepath.incomedetails.fragment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dinesh on 24/06/16.
 */
public class Income_cat_lev3  implements Serializable {

    @SerializedName("info_value")
    private String info_value;


    public String getInfo_value() {
        return info_value;
    }

    private String id;

    private String lev2_id;

    private String lev3_name;

    @SerializedName("value")
    private String value;

    public String getValue() {
        return value;
    }

    @SerializedName("tb_field_name")
    private  String tb_field_name;

    public String getTb_field_name() {
        return tb_field_name;
    }
    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLev2_id ()
    {
        return lev2_id;
    }

    public void setLev2_id (String lev2_id)
    {
        this.lev2_id = lev2_id;
    }

    public String getLev3_name ()
    {
        return lev3_name;
    }

    public void setLev3_name (String lev3_name)
    {
        this.lev3_name = lev3_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", lev2_id = "+lev2_id+", lev3_name = "+lev3_name+"]";
    }
}


