package com.purplepath.purplepath.propertyinsurance.healthinsurancemodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 11/7/17.
 */

public class Family_floater_plan implements Serializable {

    public ArrayList<Available> getAvailable() {
        return available;
    }

    public void setAvailable(ArrayList<Available> available) {
        this.available = available;
    }

    private ArrayList<Available> available;

    private String suggested;



    public String getSuggested ()
    {
        return suggested;
    }

    public void setSuggested (String suggested)
    {
        this.suggested = suggested;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [available = "+available+", suggested = "+suggested+"]";
    }
}

