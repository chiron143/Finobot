package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class He_det implements Serializable
{
    private String he_dental_per;

    private String he_med;

    private String he_vision_per;

    private String he_med_per;

    private String he_dental;

    private String he_vision;

    public String getHe_dental_per ()
    {
        return he_dental_per;
    }

    public void setHe_dental_per (String he_dental_per)
    {
        this.he_dental_per = he_dental_per;
    }

    public String getHe_med ()
{
    return he_med;
}

    public void setHe_med (String he_med)
    {
        this.he_med = he_med;
    }

    public String getHe_vision_per ()
    {
        return he_vision_per;
    }

    public void setHe_vision_per (String he_vision_per)
    {
        this.he_vision_per = he_vision_per;
    }

    public String getHe_med_per ()
    {
        return he_med_per;
    }

    public void setHe_med_per (String he_med_per)
    {
        this.he_med_per = he_med_per;
    }

    public String getHe_dental ()
{
    return he_dental;
}

    public void setHe_dental (String he_dental)
    {
        this.he_dental = he_dental;
    }

    public String getHe_vision ()
{
    return he_vision;
}

    public void setHe_vision (String he_vision)
    {
        this.he_vision = he_vision;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [he_dental_per = "+he_dental_per+", he_med = "+he_med+", he_vision_per = "+he_vision_per+", he_med_per = "+he_med_per+", he_dental = "+he_dental+", he_vision = "+he_vision+"]";
    }
}

