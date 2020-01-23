package com.purplepath.purplepath.propertyinsurance.motorinsurancemodel;

import java.io.Serializable;

/**
 * Created by pravinr on 11/22/17.
 */

public class Assets implements Serializable {
    private String lev1_name;

    private String id;

    private String current_value;

    private String asset_name;

    private String purchase_val;

    private String lev2_name;

    private String is_own_house;

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

    public String getCurrent_value ()
    {
        return current_value;
    }

    public void setCurrent_value (String current_value)
    {
        this.current_value = current_value;
    }

    public String getAsset_name ()
    {
        return asset_name;
    }

    public void setAsset_name (String asset_name)
    {
        this.asset_name = asset_name;
    }



    public String getPurchase_val ()
    {
        return purchase_val;
    }

    public void setPurchase_val (String purchase_val)
    {
        this.purchase_val = purchase_val;
    }

    public String getLev2_name ()
    {
        return lev2_name;
    }

    public void setLev2_name (String lev2_name)
    {
        this.lev2_name = lev2_name;
    }

    public String getIs_own_house ()
    {
        return is_own_house;
    }

    public void setIs_own_house (String is_own_house)
    {
        this.is_own_house = is_own_house;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lev1_name = "+lev1_name+", id = "+id+", current_value = "+current_value+"," +
                " asset_name = "+asset_name+", purchase_val = "+purchase_val+", lev2_name = "+lev2_name+", is_own_house = "+is_own_house+"]";
    }
}

