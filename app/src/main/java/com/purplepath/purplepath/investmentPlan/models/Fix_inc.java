package com.purplepath.purplepath.investmentPlan.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 20-07-2017.
 */

public class Fix_inc implements Serializable {

    private String cumulative_actual;

    private String periodic_actual;

    private String periodic_variance_per;

    private String cumulative_variance_val;

    private String cumulative_variance_per;

    private String cumulative_plan;

    private String periodic_plan;

    private String periodic_variance_val;

    public String getCumulative_actual ()
    {
        return cumulative_actual;
    }

    public void setCumulative_actual (String cumulative_actual)
    {
        this.cumulative_actual = cumulative_actual;
    }

    public String getPeriodic_actual ()
    {
        return periodic_actual;
    }

    public void setPeriodic_actual (String periodic_actual)
    {
        this.periodic_actual = periodic_actual;
    }

    public String getPeriodic_variance_per ()
    {
        return periodic_variance_per;
    }

    public void setPeriodic_variance_per (String periodic_variance_per)
    {
        this.periodic_variance_per = periodic_variance_per;
    }

    public String getCumulative_variance_val ()
    {
        return cumulative_variance_val;
    }

    public void setCumulative_variance_val (String cumulative_variance_val)
    {
        this.cumulative_variance_val = cumulative_variance_val;
    }

    public String getCumulative_variance_per ()
    {
        return cumulative_variance_per;
    }

    public void setCumulative_variance_per (String cumulative_variance_per)
    {
        this.cumulative_variance_per = cumulative_variance_per;
    }

    public String getCumulative_plan ()
    {
        return cumulative_plan;
    }

    public void setCumulative_plan (String cumulative_plan)
    {
        this.cumulative_plan = cumulative_plan;
    }

    public String getPeriodic_plan ()
    {
        return periodic_plan;
    }

    public void setPeriodic_plan (String periodic_plan)
    {
        this.periodic_plan = periodic_plan;
    }

    public String getPeriodic_variance_val ()
    {
        return periodic_variance_val;
    }

    public void setPeriodic_variance_val (String periodic_variance_val)
    {
        this.periodic_variance_val = periodic_variance_val;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cumulative_actual = "+cumulative_actual+", periodic_actual = "+periodic_actual+", periodic_variance_per = "+periodic_variance_per+", cumulative_variance_val = "+cumulative_variance_val+", cumulative_variance_per = "+cumulative_variance_per+", cumulative_plan = "+cumulative_plan+", periodic_plan = "+periodic_plan+", periodic_variance_val = "+periodic_variance_val+"]";
    }
}
