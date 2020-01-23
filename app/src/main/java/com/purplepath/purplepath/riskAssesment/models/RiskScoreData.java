package com.purplepath.purplepath.riskAssesment.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 26-01-2017.
 */

public class RiskScoreData implements Serializable {
    private String message;

    private Risk_capacity risk_capacity;

    private Risk_appetite risk_appetite;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Risk_capacity getRisk_capacity ()
    {
        return risk_capacity;
    }

    public void setRisk_capacity (Risk_capacity risk_capacity)
    {
        this.risk_capacity = risk_capacity;
    }

    public Risk_appetite getRisk_appetite ()
    {
        return risk_appetite;
    }

    public void setRisk_appetite (Risk_appetite risk_appetite)
    {
        this.risk_appetite = risk_appetite;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", risk_capacity = "+risk_capacity+", risk_appetite = "+risk_appetite+"]";
    }
}
