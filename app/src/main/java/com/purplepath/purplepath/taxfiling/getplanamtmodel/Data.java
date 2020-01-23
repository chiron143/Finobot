package com.purplepath.purplepath.taxfiling.getplanamtmodel;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 6/22/18.
 */

public class Data implements Serializable {

    private String message;

    private ArrayList<Response_array> response_array;

    public ArrayList<Response_array> getResponse_array() {
        return response_array;
    }

    public void setResponse_array(ArrayList<Response_array> response_array) {
        this.response_array = response_array;
    }

    private String data_plan;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }


    public String getData_plan ()
    {
        return data_plan;
    }

    public void setData_plan (String data_plan)
    {
        this.data_plan = data_plan;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", response_array = "+response_array+", data_plan = "+data_plan+"]";
    }
}

