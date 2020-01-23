package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by Bert on 18-Jun-16.
 */
public class AddAndUpdateGoalData implements Serializable
{
    private String message;

    private GoalDetailsModel goal_details;

    private String goal_id;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public GoalDetailsModel getGoal_details ()
    {
        return goal_details;
    }

    public void setGoal_details (GoalDetailsModel goal_details)
    {
        this.goal_details = goal_details;
    }

    public String getGoal_id ()
    {
        return goal_id;
    }

    public void setGoal_id (String goal_id)
    {
        this.goal_id = goal_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", goal_details = "+goal_details+", goal_id = "+goal_id+"]";
    }
}
