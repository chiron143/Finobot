package com.purplepath.purplepath.goal;

import java.io.Serializable;

/**
 * Created by Bert on 23-Jun-16.
 */
public class GoalCategoriesLevelOne implements Serializable {
    private String lev1_name;
    private String id;
    public String getLev1_name ()
    {
        return lev1_name;
    }

    public void setLev1_name (String lev1_name)
    {
        this.lev1_name = lev1_name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lev1_name = "+lev1_name+", id = "+id+"]";
    }
}

