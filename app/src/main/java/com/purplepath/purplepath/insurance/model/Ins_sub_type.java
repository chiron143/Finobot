package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;

/**
 * Created by dinesh on 30/08/17.
 */

public class Ins_sub_type implements Serializable {

    private String id;

    private String ins_type;

    private String ins_sub_type;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIns_type ()
    {
        return ins_type;
    }

    public void setIns_type (String ins_type)
    {
        this.ins_type = ins_type;
    }

    public String getIns_sub_type ()
    {
        return ins_sub_type;
    }

    public void setIns_sub_type (String ins_sub_type)
    {
        this.ins_sub_type = ins_sub_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", ins_type = "+ins_type+", ins_sub_type = "+ins_sub_type+"]";
    }
}
