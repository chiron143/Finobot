package com.purplepath.purplepath.AppManagement.Links.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 5/25/17.
 */

public class Linksdata implements Serializable {
    private String message;

    //private Links[] links;

    private ArrayList<Links> links;

    public ArrayList<Links> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Links> links) {
        this.links = links;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

   /* public Links[] getLinks ()
    {
        return links;
    }

    public void setLinks (Links[] links)
    {
        this.links = links;
    }*/

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", links = "+links+"]";
    }
}

