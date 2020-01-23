package com.purplepath.purplepath.recommendation.getgoalmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 9/5/17.
 */

public class User_goals implements Serializable {
    private Resources_used resources_used;

    private String pval;

    private String fv_1_year;

    private String fund_val;

    private String growth_rate;

    private String expected_increment;

    private String pmt_1_year;

    private String goal_name;

    private String goal_years;

    private String pmt_1_mon;

    private String cost_of_goal;

    public Resources_used getResources_used ()
    {
        return resources_used;
    }

    public void setResources_used (Resources_used resources_used)
    {
        this.resources_used = resources_used;
    }

    public String getPval ()
    {
        return pval;
    }

    public void setPval (String pval)
    {
        this.pval = pval;
    }

    public String getFv_1_year ()
    {
        return fv_1_year;
    }

    public void setFv_1_year (String fv_1_year)
    {
        this.fv_1_year = fv_1_year;
    }

    public String getFund_val ()
    {
        return fund_val;
    }

    public void setFund_val (String fund_val)
    {
        this.fund_val = fund_val;
    }

    public String getGrowth_rate ()
    {
        return growth_rate;
    }

    public void setGrowth_rate (String growth_rate)
    {
        this.growth_rate = growth_rate;
    }

    public String getExpected_increment ()
    {
        return expected_increment;
    }

    public void setExpected_increment (String expected_increment)
    {
        this.expected_increment = expected_increment;
    }

    public String getPmt_1_year ()
    {
        return pmt_1_year;
    }

    public void setPmt_1_year (String pmt_1_year)
    {
        this.pmt_1_year = pmt_1_year;
    }

    public String getGoal_name ()
    {
        return goal_name;
    }

    public void setGoal_name (String goal_name)
    {
        this.goal_name = goal_name;
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

    public String getCost_of_goal ()
    {
        return cost_of_goal;
    }

    public void setCost_of_goal (String cost_of_goal)
    {
        this.cost_of_goal = cost_of_goal;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [resources_used = "+resources_used+", pval = "+pval+", fv_1_year = "+fv_1_year+", fund_val = "+fund_val+", growth_rate = "+growth_rate+", expected_increment = "+expected_increment+", pmt_1_year = "+pmt_1_year+", goal_name = "+goal_name+", goal_years = "+goal_years+", pmt_1_mon = "+pmt_1_mon+", cost_of_goal = "+cost_of_goal+"]";
    }
}

