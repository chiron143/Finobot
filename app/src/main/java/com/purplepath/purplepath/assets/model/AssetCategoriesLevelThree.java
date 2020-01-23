package com.purplepath.purplepath.assets.model;

import java.io.Serializable;

/**
 * Created by Bert on 06-Jul-16.
 */
public class AssetCategoriesLevelThree implements Serializable
{
    private String id;

    private String lev2_id;

    private String lev3_name;

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