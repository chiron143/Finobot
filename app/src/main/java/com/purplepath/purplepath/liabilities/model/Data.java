package com.purplepath.purplepath.liabilities.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 08/07/16.
 */
public class Data implements Serializable {
    private String message;

    private ArrayList<Liab_cat_lev1> liab_cat_lev1;

    private ArrayList<Liab_cat_lev2> liab_cat_lev2;

    private ArrayList<Liab_cat_lev3> liab_cat_lev3;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Liab_cat_lev1> getLiab_cat_lev1 ()
    {
        return liab_cat_lev1;
    }

    public void setLiab_cat_lev1 (ArrayList<Liab_cat_lev1> liab_cat_lev1)
    {
        this.liab_cat_lev1 = liab_cat_lev1;
    }

    public ArrayList<Liab_cat_lev2> getLiab_cat_lev2 ()
    {
        return liab_cat_lev2;
    }

    public void setLiab_cat_lev2 (ArrayList<Liab_cat_lev2> liab_cat_lev2)
    {
        this.liab_cat_lev2 = liab_cat_lev2;
    }

    public ArrayList<Liab_cat_lev3> getLiab_cat_lev3 ()
    {
        return liab_cat_lev3;
    }

    public void setLiab_cat_lev3 (ArrayList<Liab_cat_lev3> liab_cat_lev3)
    {
        this.liab_cat_lev3 = liab_cat_lev3;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", liab_cat_lev1 = "+liab_cat_lev1+", liab_cat_lev2 = "+liab_cat_lev2+", liab_cat_lev3 = "+liab_cat_lev3+"]";
    }
}
