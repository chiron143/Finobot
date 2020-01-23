package com.purplepath.purplepath.taxfiling.getformparse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 5/24/18.
 */

public class Data implements Serializable {

    private String message;

    private ArrayList<String> failure_array;

    public ArrayList<String> getFailure_array() {
        return failure_array;
    }

    public void setFailure_array(ArrayList<String> failure_array) {
        this.failure_array = failure_array;
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
        return "ClassPojo [message = "+message+", failure_array = "+failure_array+"]";
    }
}

