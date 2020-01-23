package com.purplepath.purplepath.propertyinsurance.healthinsurancemodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 11/7/17.
 */

public class Individual_health_plan implements Serializable {

    private ArrayList<Available> available;

    private ArrayList<Suggested> suggested;

    public ArrayList<Available> getAvailable() {
        return available;
    }

    public void setAvailable(ArrayList<Available> available) {
        this.available = available;
    }

    public ArrayList<Suggested> getSuggested() {
        return suggested;
    }

    public void setSuggested(ArrayList<Suggested> suggested) {
        this.suggested = suggested;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [available = "+available+", suggested = "+suggested+"]";
    }
}



