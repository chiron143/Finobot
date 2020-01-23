package com.purplepath.purplepath.goal;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 18-Jun-16.
 */
public class GetGoalsData implements Serializable
{
    private String message;

    private ArrayList<GetGoalsUserData> user_goals;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<GetGoalsUserData> getUser_goals ()
    {
        return user_goals;
    }

    public void setUser_goals (ArrayList<GetGoalsUserData> user_goals)
    {
        this.user_goals = user_goals;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_goals = "+user_goals+"]";
    }
}
