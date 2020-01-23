package com.purplepath.purplepath.schedule.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 06-07-2017.
 */

public class Pending implements Serializable {
    private ArrayList<Ins> ins;

    private ArrayList<Liab> liab;

    public ArrayList<Ins> getIns ()
    {
        return ins;
    }

    public void setIns (ArrayList<Ins> ins)
    {
        this.ins = ins;
    }

    public ArrayList<Liab> getLiab ()
    {
        return liab;
    }

    public void setLiab (ArrayList<Liab> liab)
    {
        this.liab = liab;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ins = "+ins+", liab = "+liab+"]";
    }
}
