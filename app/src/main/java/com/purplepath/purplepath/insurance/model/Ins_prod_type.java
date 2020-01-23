package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;

/**
 * Created by dinesh on 30/08/17.
 */

public class Ins_prod_type implements Serializable {
    private String id;

    private String ins_prod_cat_id;

    private String prod_type;

    private String ins_sub_type_id;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIns_prod_cat_id ()
    {
        return ins_prod_cat_id;
    }

    public void setIns_prod_cat_id (String ins_prod_cat_id)
    {
        this.ins_prod_cat_id = ins_prod_cat_id;
    }

    public String getProd_type ()
    {
        return prod_type;
    }

    public void setProd_type (String prod_type)
    {
        this.prod_type = prod_type;
    }

    public String getIns_sub_type_id ()
    {
        return ins_sub_type_id;
    }

    public void setIns_sub_type_id (String ins_sub_type_id)
    {
        this.ins_sub_type_id = ins_sub_type_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", ins_prod_cat_id = "+ins_prod_cat_id+", prod_type = "+prod_type+", ins_sub_type_id = "+ins_sub_type_id+"]";
    }
}
