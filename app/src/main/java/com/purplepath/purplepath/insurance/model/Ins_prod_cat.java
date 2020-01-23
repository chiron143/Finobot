package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;

/**
 * Created by dinesh on 30/08/17.
 */

public class Ins_prod_cat implements Serializable {
    private String prod_cat;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProd_cat ()
    {
        return prod_cat;
    }

    public void setProd_cat (String prod_cat)
    {
        this.prod_cat = prod_cat;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", prod_cat = "+prod_cat+"]";
    }
}
