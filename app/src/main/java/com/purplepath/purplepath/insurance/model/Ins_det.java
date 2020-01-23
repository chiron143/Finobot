package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;

/**
 * Created by dinesh on 26/09/17.
 */

public class Ins_det implements Serializable{
    private String ins_prod_cat;

    private String sum_assured;

    private String ins_prod_type;

    public String getIns_prod_cat ()
    {
        return ins_prod_cat;
    }

    public void setIns_prod_cat (String ins_prod_cat)
    {
        this.ins_prod_cat = ins_prod_cat;
    }

    public String getSum_assured ()
    {
        return sum_assured;
    }

    public void setSum_assured (String sum_assured)
    {
        this.sum_assured = sum_assured;
    }

    public String getIns_prod_type ()
    {
        return ins_prod_type;
    }

    public void setIns_prod_type (String ins_prod_type)
    {
        this.ins_prod_type = ins_prod_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ins_prod_cat = "+ins_prod_cat+", sum_assured = "+sum_assured+", ins_prod_type = "+ins_prod_type+"]";
    }
}
