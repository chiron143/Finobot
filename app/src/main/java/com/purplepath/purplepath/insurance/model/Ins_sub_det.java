package com.purplepath.purplepath.insurance.model;

import java.io.Serializable;

/**
 * Created by dinesh on 26/09/17.
 */

public class Ins_sub_det implements Serializable {
    private Ins_det[] ins_det;

    public Ins_det[] getIns_det ()
    {
        return ins_det;
    }

    public void setIns_det (Ins_det[] ins_det)
    {
        this.ins_det = ins_det;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ins_det = "+ins_det+"]";
    }
}
