package com.purplepath.purplepath.incomedetails.fragment.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 30/06/16.
 */
public class Income_details implements Serializable{
    private ArrayList<Inc_det> inc_det;

    public ArrayList<Inc_det> getInc_det ()
    {
        return inc_det;
    }

    public void setInc_det (ArrayList<Inc_det> inc_det)
    {
        this.inc_det = inc_det;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [inc_det = "+inc_det+"]";
    }
}

