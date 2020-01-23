package com.purplepath.purplepath.assets.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 06-Jul-16.
 */
public class AssetCategoriesData implements Serializable
{
    private String message;

    private ArrayList<AssetCategoriesLevelThree> asset_cat_lev3;

    private ArrayList<AssetCategoriesLevelTwo> asset_cat_lev2;

    private ArrayList<AssetCategoriesLevelOne> asset_cat_lev1;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<AssetCategoriesLevelThree> getAsset_cat_lev3 ()
    {
        return asset_cat_lev3;
    }

    public void setAsset_cat_lev3 (ArrayList<AssetCategoriesLevelThree> asset_cat_lev3)
    {
        this.asset_cat_lev3 = asset_cat_lev3;
    }

    public ArrayList<AssetCategoriesLevelTwo> getAsset_cat_lev2 ()
    {
        return asset_cat_lev2;
    }

    public void setAsset_cat_lev2 (ArrayList<AssetCategoriesLevelTwo> asset_cat_lev2)
    {
        this.asset_cat_lev2 = asset_cat_lev2;
    }

    public ArrayList<AssetCategoriesLevelOne> getAsset_cat_lev1 ()
    {
        return asset_cat_lev1;
    }

    public void setAsset_cat_lev1 (ArrayList<AssetCategoriesLevelOne> asset_cat_lev1)
    {
        this.asset_cat_lev1 = asset_cat_lev1;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", asset_cat_lev3 = "+asset_cat_lev3+", asset_cat_lev2 = "+asset_cat_lev2+", asset_cat_lev1 = "+asset_cat_lev1+"]";
    }
}
