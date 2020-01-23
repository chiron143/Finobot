package com.purplepath.purplepath.goalanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 18-Jul-16.
 */
public class Long_goal implements Serializable
{
    private String goal_cat_lev1_id;

    private String goal_cat_lev3_id;

    private String goal_recurrence;

    private String other_category;

    private String goal_priority;

    private String status;

    private String recur_months;

    private String goal_years;

    private String goal_duration;

    private String cost_of_goal;

    private String id;

    private String goal_imp;

    private String expected_increment;

    private String goal_cat_lev2_id;

    private String belongs_to_id;

    private String goal_name;

    private String goal_end_datetime;

    private String goal_start_datetime;

    private String goal_frequency;

    private String user_id;

    private String notes;

    private String recur_years;

    public String getGoal_cat_lev1_id ()
    {
        return goal_cat_lev1_id;
    }

    public void setGoal_cat_lev1_id (String goal_cat_lev1_id)
    {
        this.goal_cat_lev1_id = goal_cat_lev1_id;
    }

    public String getGoal_cat_lev3_id ()
    {
        return goal_cat_lev3_id;
    }

    public void setGoal_cat_lev3_id (String goal_cat_lev3_id)
    {
        this.goal_cat_lev3_id = goal_cat_lev3_id;
    }

    public String getGoal_recurrence ()
    {
        return goal_recurrence;
    }

    public void setGoal_recurrence (String goal_recurrence)
    {
        this.goal_recurrence = goal_recurrence;
    }

    public String getOther_category ()
    {
        return other_category;
    }

    public void setOther_category (String other_category)
    {
        this.other_category = other_category;
    }

    public String getGoal_priority ()
    {
        return goal_priority;
    }

    public void setGoal_priority (String goal_priority)
    {
        this.goal_priority = goal_priority;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getRecur_months ()
    {
        return recur_months;
    }

    public void setRecur_months (String recur_months)
    {
        this.recur_months = recur_months;
    }

    public String getGoal_years ()
    {
        return goal_years;
    }

    public void setGoal_years (String goal_years)
    {
        this.goal_years = goal_years;
    }

    public String getGoal_duration ()
{
    return goal_duration;
}

    public void setGoal_duration (String goal_duration)
    {
        this.goal_duration = goal_duration;
    }

    public String getCost_of_goal ()
    {
        return cost_of_goal;
    }

    public void setCost_of_goal (String cost_of_goal)
    {
        this.cost_of_goal = cost_of_goal;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getGoal_imp ()
    {
        return goal_imp;
    }

    public void setGoal_imp (String goal_imp)
    {
        this.goal_imp = goal_imp;
    }

    public String getExpected_increment ()
    {
        return expected_increment;
    }

    public void setExpected_increment (String expected_increment)
    {
        this.expected_increment = expected_increment;
    }

    public String getGoal_cat_lev2_id ()
    {
        return goal_cat_lev2_id;
    }

    public void setGoal_cat_lev2_id (String goal_cat_lev2_id)
    {
        this.goal_cat_lev2_id = goal_cat_lev2_id;
    }

    public String getBelongs_to_id ()
    {
        return belongs_to_id;
    }

    public void setBelongs_to_id (String belongs_to_id)
    {
        this.belongs_to_id = belongs_to_id;
    }

    public String getGoal_name ()
    {
        return goal_name;
    }

    public void setGoal_name (String goal_name)
    {
        this.goal_name = goal_name;
    }

    public String getGoal_end_datetime ()
    {
        return goal_end_datetime;
    }

    public void setGoal_end_datetime (String goal_end_datetime)
    {
        this.goal_end_datetime = goal_end_datetime;
    }

    public String getGoal_start_datetime ()
    {
        return goal_start_datetime;
    }

    public void setGoal_start_datetime (String goal_start_datetime)
    {
        this.goal_start_datetime = goal_start_datetime;
    }

    public String getGoal_frequency ()
    {
        return goal_frequency;
    }

    public void setGoal_frequency (String goal_frequency)
    {
        this.goal_frequency = goal_frequency;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getNotes ()
    {
        return notes;
    }

    public void setNotes (String notes)
    {
        this.notes = notes;
    }

    public String getRecur_years ()
    {
        return recur_years;
    }

    public void setRecur_years (String recur_years)
    {
        this.recur_years = recur_years;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [goal_cat_lev1_id = "+goal_cat_lev1_id+", goal_cat_lev3_id = "+goal_cat_lev3_id+", goal_recurrence = "+goal_recurrence+", other_category = "+other_category+", goal_priority = "+goal_priority+", status = "+status+", recur_months = "+recur_months+", goal_years = "+goal_years+", goal_duration = "+goal_duration+", cost_of_goal = "+cost_of_goal+", id = "+id+", goal_imp = "+goal_imp+", expected_increment = "+expected_increment+", goal_cat_lev2_id = "+goal_cat_lev2_id+", belongs_to_id = "+belongs_to_id+", goal_name = "+goal_name+", goal_end_datetime = "+goal_end_datetime+", goal_start_datetime = "+goal_start_datetime+", goal_frequency = "+goal_frequency+", user_id = "+user_id+", notes = "+notes+", recur_years = "+recur_years+"]";
    }
}

