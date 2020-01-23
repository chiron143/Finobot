package com.purplepath.purplepath.model.riskmodel;

import com.purplepath.purplepath.riskAssesment.models.Risk_appetite;
import com.purplepath.purplepath.riskAssesment.models.Risk_capacity;

import java.io.Serializable;

/**
 * Created by dinesh on 09/09/17.
 */

public class Data implements Serializable{
    private String message;

    private Risk_capacity risk_capacity;

    private Risk_appetite risk_appetite;

    private String is_version_update;

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

    public String getIs_version_update ()
    {
        return is_version_update;
    }

    public void setIs_version_update (String is_version_update)
    {
        this.is_version_update = is_version_update;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", risk_capacity = "+risk_capacity+", risk_appetite = "+risk_appetite+", is_version_update = "+is_version_update+"]";
    }
}
