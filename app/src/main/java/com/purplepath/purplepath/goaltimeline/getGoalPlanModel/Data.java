package com.purplepath.purplepath.goaltimeline.getGoalPlanModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Suresh on 25/07/17.
 */

public class Data  implements Serializable {

    private String message;

//    private Goal_plan[] goal_plan;


    private ArrayList<Goal_plan> goal_plan;

    private Goal_plan_cal goal_plan_cal;


    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Goal_plan> getGoal_plan() {
        return goal_plan;
    }

    public void setGoal_plan(ArrayList<Goal_plan> goal_plan) {
        this.goal_plan = goal_plan;
    }

    //    public Goal_plan[] getGoal_plan ()
//    {
//        return goal_plan;
//    }
//
//    public void setGoal_plan (Goal_plan[] goal_plan)
//    {
//        this.goal_plan = goal_plan;
//    }


    public Goal_plan_cal getGoal_plan_cal() {
        return goal_plan_cal;
    }

    public void setGoal_plan_cal(Goal_plan_cal goal_plan_cal) {
        this.goal_plan_cal = goal_plan_cal;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", goal_plan_cal = "+goal_plan_cal+", goal_plan = "+goal_plan+"]";
    }
}
