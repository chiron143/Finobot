package com.purplepath.purplepath.recommendation.getgoalmodel;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 9/5/17.
 */

public class Data implements Serializable {
    private String message;

    public ArrayList<User_goals> getUser_goals() {
        return user_goals;
    }

    public void setUser_goals(ArrayList<User_goals> user_goals) {
        this.user_goals = user_goals;
    }

    public ArrayList<User_goals> user_goals;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_goals = "+user_goals+"]";
    }
}

