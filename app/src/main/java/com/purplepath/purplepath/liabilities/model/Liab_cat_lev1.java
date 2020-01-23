package com.purplepath.purplepath.liabilities.model;

import java.io.Serializable;

/**
 * Created by dinesh on 08/07/16.
 */
public class Liab_cat_lev1 implements Serializable {
    private String lev1_name;

    private String type;

    private String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLev1_name ()
    {
        return lev1_name;
    }

    public void setLev1_name (String lev1_name)
    {
        this.lev1_name = lev1_name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lev1_name = "+lev1_name+", id = "+id+"]";
    }

}
