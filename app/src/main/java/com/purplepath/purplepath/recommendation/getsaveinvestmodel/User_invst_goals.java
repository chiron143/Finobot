package com.purplepath.purplepath.recommendation.getsaveinvestmodel;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by pravinr on 7/29/17.
 */

public class User_invst_goals implements Serializable {
    private Resources_used resources_used;
    private String lev1_name;
    private String current_value;
    private String target_fv;
    private String growth_rate;
    private String pmt_1_year;
    private String goal_years;
    private String pmt_1_mon;
    private String asset_class;
    private String pval;

    public String getPval() {
        return pval;
    }

    public void setPval(String pval) {
        this.pval = pval;
    }

    public Resources_used getResources_used ()
    {
        return resources_used;
    }

    public void setResources_used (Resources_used resources_used)
    {
        this.resources_used = resources_used;
    }

    public String getLev1_name ()
    {
        return lev1_name;
    }

    public void setLev1_name (String lev1_name)
    {
        this.lev1_name = lev1_name;
    }

    public String getCurrent_value ()
    {
        return current_value;
    }

    public void setCurrent_value (String current_value)
    {
        this.current_value = current_value;
    }

    public String getTarget_fv ()
    {
        return target_fv;
    }

    public void setTarget_fv (String target_fv)
    {
        this.target_fv = target_fv;
    }

    public String getGrowth_rate ()
    {
        return growth_rate;
    }

    public void setGrowth_rate (String growth_rate)
    {
        this.growth_rate = growth_rate;
    }

    public String getPmt_1_year ()
    {
        return pmt_1_year;
    }

    public void setPmt_1_year (String pmt_1_year)
    {
        this.pmt_1_year = pmt_1_year;
    }

    public String getGoal_years ()
    {
        return goal_years;
    }

    public void setGoal_years (String goal_years)
    {
        this.goal_years = goal_years;
    }

    public String getPmt_1_mon ()
    {
        return pmt_1_mon;
    }

    public void setPmt_1_mon (String pmt_1_mon)
    {
        this.pmt_1_mon = pmt_1_mon;
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
        return "ClassPojo [resources_used = "+resources_used+", lev1_name = "+lev1_name+", pval = "+pval+", current_value = "+current_value+", target_fv = "+target_fv+", growth_rate = "+growth_rate+", pmt_1_year = "+pmt_1_year+", goal_years = "+goal_years+", pmt_1_mon = "+pmt_1_mon+", asset_class = "+asset_class+"]";
    }
}

