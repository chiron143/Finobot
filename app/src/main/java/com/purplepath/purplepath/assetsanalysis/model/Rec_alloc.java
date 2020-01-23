package com.purplepath.purplepath.assetsanalysis.model;

import java.io.Serializable;

/**
 * Created by vishnu on 28-09-2016.
 */
public class Rec_alloc implements Serializable{
    private Rec_alloc_det rec_alloc_det;

    public Rec_alloc_det getRec_alloc_det ()
    {
        return rec_alloc_det;
    }

    public void setRec_alloc_det (Rec_alloc_det rec_alloc_det)
    {
        this.rec_alloc_det = rec_alloc_det;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [rec_alloc_det = "+rec_alloc_det+"]";
    }
}
