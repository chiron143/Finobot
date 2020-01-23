package com.purplepath.purplepath.recommendation.getsaveinvestmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 7/29/17.
 */

public class Data implements Serializable {

    private String message;

    public ArrayList<User_invst_goals> user_invst_goals;

    public ArrayList<User_invst_goals> getUser_invst_goals() {
        return user_invst_goals;
    }

    public void setUser_invst_goals(ArrayList<User_invst_goals> user_invst_goals) {
        this.user_invst_goals = user_invst_goals;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

   /* public User_invst_goals[] getUser_invst_goals ()
    {
        return user_invst_goals;
    }

    public void setUser_invst_goals (User_invst_goals[] user_invst_goals)
    {
        this.user_invst_goals = user_invst_goals;
    }*/

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_invst_goals = "+user_invst_goals+"]";
    }
}

