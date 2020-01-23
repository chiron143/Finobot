package com.purplepath.purplepath.taxfiling.getdialogforminstruction;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 7/6/18.
 */

public class Data implements Serializable {
    private String message;

    private ArrayList<Samples> samples;

    public ArrayList<Samples> getSamples() {
        return samples;
    }

    public void setSamples(ArrayList<Samples> samples) {
        this.samples = samples;
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
        return "ClassPojo [message = "+message+", samples = "+samples+"]";
    }
}

