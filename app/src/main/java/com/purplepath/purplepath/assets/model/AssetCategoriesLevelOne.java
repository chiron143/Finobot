package com.purplepath.purplepath.assets.model;

import java.io.Serializable;

/**
 * Created by Bert on 06-Jul-16.
 */
public class AssetCategoriesLevelOne implements Serializable
{
    private String lev1_name;

    private String id;

    private String type;

    private String asset_class;

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

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getAsset_class ()
    {
        return asset_class;
    }

    public void setAsset_class (String asset_class)
    {
        this.asset_class = asset_class;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lev1_name = "+lev1_name+", id = "+id+", type = "+type+", asset_class = "+asset_class+"]";
    }
}