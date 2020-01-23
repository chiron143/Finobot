package com.purplepath.purplepath.AppManagement.Knowledge.model;

import com.purplepath.purplepath.AppManagement.Links.model.Links;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 8/5/17.
 */

public class Data implements Serializable {
    private String message;

    private ArrayList<Knowledge> knowledge;

    public ArrayList<Knowledge> getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(ArrayList<Knowledge> knowledge) {
        this.knowledge = knowledge;
    }
    // private Knowledge[] knowledge;

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
        return "ClassPojo [message = "+message+", knowledge = "+knowledge+"]";
    }
}

