package com.purplepath.purplepath.assets.model;

import java.io.Serializable;

/**
 * Created by Bert on 06-Jul-16.
 */
public class AssetCategoriesLevelTwo implements Serializable
{
    private String id;

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