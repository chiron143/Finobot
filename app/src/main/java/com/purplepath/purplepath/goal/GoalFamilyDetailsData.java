package com.purplepath.purplepath.goal;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 20-Jun-16.
 */
public class GoalFamilyDetailsData implements Serializable
{
    private String message;

    private ArrayList<GoalFamilyDetails> family_details;

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }

    public ArrayList<GoalFamilyDetails> getFamily_details () {
        return family_details;
    }

    public void setFamily_details (ArrayList<GoalFamilyDetails> family_details) {
        this.family_details = family_details;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = "+message+", family_details = "+family_details+"]";
    }
}