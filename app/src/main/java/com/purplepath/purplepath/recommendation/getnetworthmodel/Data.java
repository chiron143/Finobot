package com.purplepath.purplepath.recommendation.getnetworthmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 7/17/17.
 */

public class Data implements Serializable {
    private String message;

    private String networth_val;

    private String over_all_asset_val;

    private Assets assets;

    private String over_all_liab_val;

    private Liab liab;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getNetworth_val ()
    {
        return networth_val;
    }

    public void setNetworth_val (String networth_val)
    {
        this.networth_val = networth_val;
    }

    public String getOver_all_asset_val ()
    {
        return over_all_asset_val;
    }

    public void setOver_all_asset_val (String over_all_asset_val)
    {
        this.over_all_asset_val = over_all_asset_val;
    }

    public Assets getAssets ()
    {
        return assets;
    }

    public void setAssets (Assets assets)
    {
        this.assets = assets;
    }

    public String getOver_all_liab_val ()
    {
        return over_all_liab_val;
    }

    public void setOver_all_liab_val (String over_all_liab_val)
    {
        this.over_all_liab_val = over_all_liab_val;
    }

    public Liab getLiab ()
    {
        return liab;
    }

    public void setLiab (Liab liab)
    {
        this.liab = liab;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", networth_val = "+networth_val+", over_all_asset_val = "+over_all_asset_val+", assets = "+assets+", over_all_liab_val = "+over_all_liab_val+", liab = "+liab+"]";
    }
}

