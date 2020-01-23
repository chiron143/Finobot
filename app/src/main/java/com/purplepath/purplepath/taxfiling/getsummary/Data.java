package com.purplepath.purplepath.taxfiling.getsummary;

import com.purplepath.purplepath.taxfiling.getchecklist.Result;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 4/9/18.
 */

public class Data implements Serializable{
    private String message;

    private ArrayList<Summary_result> summary_result;

    public ArrayList<Summary_result> getSummary_result() {
        return summary_result;
    }

    public void setSummary_result(ArrayList<Summary_result> summary_result) {
        this.summary_result = summary_result;
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
        return "ClassPojo [message = "+message+", summary_result = "+summary_result+"]";
    }
}

