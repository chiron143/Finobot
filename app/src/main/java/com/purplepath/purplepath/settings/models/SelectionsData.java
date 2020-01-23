package com.purplepath.purplepath.settings.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 11-01-2017.
 */

public class SelectionsData implements Serializable {

    private String message;

    private ArrayList<User_per_det_Selec> user_per_det;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<User_per_det_Selec> getUser_per_det_Selec ()
    {
        return user_per_det;
    }

    public void setUser_per_det_Selec (ArrayList<User_per_det_Selec> user_per_det)
    {
        this.user_per_det = user_per_det;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_per_det = "+user_per_det+"]";
    }
}
