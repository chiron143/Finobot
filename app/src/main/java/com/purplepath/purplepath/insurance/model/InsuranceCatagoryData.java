package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 30/08/17.
 */

public class InsuranceCatagoryData implements Serializable{
    private String message;

    private ArrayList<Ins_prod_cat> ins_prod_cat;

    private ArrayList<Ins_sub_type> ins_sub_type;

    private ArrayList<Ins_prod_type> ins_prod_type;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Ins_prod_cat> getIns_prod_cat ()
    {
        return ins_prod_cat;
    }

    public void setIns_prod_cat (ArrayList<Ins_prod_cat> ins_prod_cat)
    {
        this.ins_prod_cat = ins_prod_cat;
    }

    public ArrayList<Ins_sub_type> getIns_sub_type ()
    {
        return ins_sub_type;
    }

    public void setIns_sub_type (ArrayList<Ins_sub_type> ins_sub_type)
    {
        this.ins_sub_type = ins_sub_type;
    }

    public ArrayList<Ins_prod_type> getIns_prod_type ()
    {
        return ins_prod_type;
    }

    public void setIns_prod_type (ArrayList<Ins_prod_type> ins_prod_type)
    {
        this.ins_prod_type = ins_prod_type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", ins_prod_cat = "+ins_prod_cat+", ins_sub_type = "+ins_sub_type+", ins_prod_type = "+ins_prod_type+"]";
    }
}
