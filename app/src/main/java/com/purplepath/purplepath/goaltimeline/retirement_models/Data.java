package com.purplepath.purplepath.goaltimeline.retirement_models;

import com.purplepath.purplepath.goalanalysis.model.Short_goal;
import com.purplepath.purplepath.recommendation.getgoalmodel.User_goals;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bertrandrussellsakthees on 11/10/17.
 */

public class Data implements Serializable{
    private String message;

//    private User_goals[] user_goals;

    private ArrayList<Short_goal> user_goals;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Short_goal> getUser_goals() {
        return user_goals;
    }

    public void setUser_goals(ArrayList<Short_goal> user_goals) {
        this.user_goals = user_goals;
    }

    //    public User_goals[] getUser_goals ()
//    {
//        return user_goals;
//    }
//
//    public void setUser_goals (User_goals[] user_goals)
//    {
//        this.user_goals = user_goals;
//    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_goals = "+user_goals+"]";
    }
}