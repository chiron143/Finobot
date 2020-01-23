package com.purplepath.purplepath.networthanalysis.model;

import java.io.Serializable;

/**
 * Created by dinesh on 17/10/16.
 */
public class Rf_dep implements Serializable {
    private String val;

    private String[] link_to;

    public String getVal ()
    {
        return val;
    }

    public void setVal (String val)
    {
        this.val = val;
    }

    public String[] getLink_to ()
    {
        return link_to;
    }

    public void setLink_to (String[] link_to)
    {
        this.link_to = link_to;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [val = "+val+", link_to = "+link_to+"]";
    }
}
