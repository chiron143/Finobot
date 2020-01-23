package com.purplepath.purplepath.goal;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 23-Jun-16.
 */
public class GoalCategoriesData implements Serializable
{
    private String message;
    private ArrayList<GoalCategoriesLevelThree> goal_cat_lev3;

    private ArrayList<GoalCategoriesLevelTwo> goal_cat_lev2;

    private ArrayList<GoalCategoriesLevelOne> goal_cat_lev1;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<GoalCategoriesLevelThree> getGoal_cat_lev3 ()
    {
        return goal_cat_lev3;
    }

    public void setGoal_cat_lev3 (ArrayList<GoalCategoriesLevelThree> goal_cat_lev3)
    {
        this.goal_cat_lev3 = goal_cat_lev3;
    }

    public ArrayList<GoalCategoriesLevelTwo> getGoal_cat_lev2 ()
    {
        return goal_cat_lev2;
    }

    public void setGoal_cat_lev2 (ArrayList<GoalCategoriesLevelTwo> goal_cat_lev2)
    {
        this.goal_cat_lev2 = goal_cat_lev2;
    }

    public ArrayList<GoalCategoriesLevelOne> getGoal_cat_lev1 ()
    {
        return goal_cat_lev1;
    }

    public void setGoal_cat_lev1 (ArrayList<GoalCategoriesLevelOne> goal_cat_lev1)
    {
        this.goal_cat_lev1 = goal_cat_lev1;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", goal_cat_lev3 = "+goal_cat_lev3+", goal_cat_lev2 = "+goal_cat_lev2+", goal_cat_lev1 = "+goal_cat_lev1+"]";
    }
}
