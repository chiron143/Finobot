package com.purplepath.purplepath.assetsanalysis.model;

import java.io.Serializable;

/**
 * Created by vishnu on 28-09-2016.
 */
public class Cur_alloc implements Serializable{
    private Cur_alloc_det cur_alloc_det;

    public Cur_alloc_det getCur_alloc_det ()
    {
        return cur_alloc_det;
    }

    public void setCur_alloc_det (Cur_alloc_det cur_alloc_det)
    {
        this.cur_alloc_det = cur_alloc_det;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cur_alloc_det = "+cur_alloc_det+"]";
    }
}