package com.purplepath.purplepath.taxanalysis.modes;

import java.io.Serializable;

/**
 * Created by Suresh on 07/01/17.
 */

public class Tax_plan implements Serializable{

    private String total_limit;

    private String section;

    public String getTotal_limit ()
    {
        return total_limit;
    }

    public void setTotal_limit (String total_limit)
    {
        this.total_limit = total_limit;
    }

    public String getSection ()
    {
        return section;
    }

    public void setSection (String section)
    {
        this.section = section;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total_limit = "+total_limit+", section = "+section+"]";
    }
}
