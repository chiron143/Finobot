package com.purplepath.purplepath.lifeevent.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 3/7/18.
 */

public class Data implements Serializable {

    private String message;

    private ArrayList<Life_events> life_events;

    public ArrayList<Life_events> getLife_events() {
        return life_events;
    }

    public void setLife_events(ArrayList<Life_events> life_events) {
        this.life_events = life_events;
    }

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
        return "ClassPojo [message = "+message+", life_events = "+life_events+"]";
    }
}

