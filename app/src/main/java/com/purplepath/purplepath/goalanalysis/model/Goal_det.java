package com.purplepath.purplepath.goalanalysis.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 18-Jul-16.
 */
public class Goal_det implements Serializable {

    private ArrayList<Short_goal> short_goal;

    private ArrayList<Short_goal> long_goal;

    private ArrayList<Short_goal> med_goal;

    private String tot_goals;

    public ArrayList<Short_goal> getShort_goal ()
    {
        return short_goal;
    }

    public void setShort_goal (ArrayList<Short_goal> short_goal)
    {
        this.short_goal = short_goal;
    }

    public ArrayList<Short_goal> getLong_goal ()
    {
        return long_goal;
    }

    public void setLong_goal (ArrayList<Short_goal> long_goal)
    {
        this.long_goal = long_goal;
    }

    public ArrayList<Short_goal> getMed_goal ()
    {
        return med_goal;
    }

    public void setMed_goal (ArrayList<Short_goal> med_goal)
    {
        this.med_goal = med_goal;
    }

    public String getTot_goals ()
    {
        return tot_goals;
    }

    public void setTot_goals (String tot_goals)
    {
        this.tot_goals = tot_goals;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [short_goal = "+short_goal+", long_goal = "+long_goal+", med_goal = "+med_goal+", tot_goals = "+tot_goals+"]";
    }
}