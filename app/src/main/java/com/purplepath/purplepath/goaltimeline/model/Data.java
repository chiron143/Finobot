package com.purplepath.purplepath.goaltimeline.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 13/11/16.
 */
public class Data implements Serializable {
    private String message;

    private ArrayList<Goal_tmln> goal_tmln;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Goal_tmln> getGoal_tmln ()
    {
        return goal_tmln;
    }

    public void setGoal_tmln (ArrayList<Goal_tmln> goal_tmln)
    {
        this.goal_tmln = goal_tmln;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", goal_tmln = "+goal_tmln+"]";
    }
}
