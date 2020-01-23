package com.purplepath.purplepath.networthanalysis.model;

import java.io.Serializable;

/**
 * Created by dinesh on 29/09/16.
 */
public class Net_worth implements Serializable {
    private Liab_det liab_det;

    private Asst_det asst_det;

    public Liab_det getLiab_det ()
    {
        return liab_det;
    }

    public void setLiab_det (Liab_det liab_det)
    {
        this.liab_det = liab_det;
    }

    public Asst_det getAsst_det ()
    {
        return asst_det;
    }

    public void setAsst_det (Asst_det asst_det)
    {
        this.asst_det = asst_det;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [liab_det = "+liab_det+", asst_det = "+asst_det+"]";
    }
}
