package com.purplepath.purplepath.goal;

import java.io.Serializable;

/**
 * Created by Bert on 18-Jun-16.
 */
public class DeleteGoalDetails implements Serializable
{
    private String goal_id;

    private String user_id;

    public String getGoal_id ()
    {
        return goal_id;
    }

    public void setGoal_id (String goal_id)
    {
        this.goal_id = goal_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [goal_id = "+goal_id+", user_id = "+user_id+"]";
    }
}