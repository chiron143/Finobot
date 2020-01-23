package com.purplepath.purplepath.taxprompt.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 12/22/17.
 */

public class Data implements Serializable {

    private String message;

    private String property_status;

    public String getProperty_status() {
        return property_status;
    }

    public void setProperty_status(String property_status) {
        this.property_status = property_status;
    }

    private ArrayList<Overall_questions> overall_questions;

    public ArrayList<Overall_questions> getOverall_questions() {
        return overall_questions;
    }

    public void setOverall_questions(ArrayList<Overall_questions> overall_questions) {
        this.overall_questions = overall_questions;
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
        return "ClassPojo [message = "+message+", property_status = "+property_status+", overall_questions = "+overall_questions+"]";
    }
}

