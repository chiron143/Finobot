package com.purplepath.purplepath.AppManagement.Glossaries.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 19-05-2017.
 */

public class Data implements Serializable {
    private String message;

    private ArrayList<Glossaries> Glossaries;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Glossaries> getGlossaries ()
    {
        return Glossaries;
    }

    public void setGlossaries (ArrayList<Glossaries> Glossaries)
    {
        this.Glossaries = Glossaries;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", Glossaries = "+Glossaries+"]";
    }
}
