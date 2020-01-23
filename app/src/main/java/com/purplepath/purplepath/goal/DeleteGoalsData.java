package com.purplepath.purplepath.goal;

import java.io.Serializable;

/**
 * Created by Bert on 18-Jun-16.
 */
public class DeleteGoalsData implements Serializable
{
    private String message;

    private DeleteGoalDetails goal_details;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public DeleteGoalDetails getGoal_details ()
    {
        return goal_details;
    }

    public void setGoal_details (DeleteGoalDetails goal_details)
    {
        this.goal_details = goal_details;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", goal_details = "+goal_details+"]";
    }
}