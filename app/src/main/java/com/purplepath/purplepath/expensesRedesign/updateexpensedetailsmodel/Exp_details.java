package com.purplepath.purplepath.expensesRedesign.updateexpensedetailsmodel;

/**
 * Created by srinivasan on 9/1/2016.
 */
public class Exp_details
{
    private Exp_det[] exp_det;

    public Exp_det[] getExp_det ()
    {
        return exp_det;
    }

    public void setExp_det (Exp_det[] exp_det)
    {
        this.exp_det = exp_det;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [exp_det = "+exp_det+"]";
    }
}