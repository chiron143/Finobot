package com.purplepath.purplepath.riskAssesment.scoreModels;

import java.io.Serializable;

/**
 * Created by bertrandrussellsakthees on 16/01/17.
 */

public class Message implements Serializable {

    private String risk_details;

    public String getRisk_details ()
    {
        return risk_details;
    }

    public void setRisk_details (String risk_details)
    {
        this.risk_details = risk_details;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [risk_details = "+risk_details+"]";
    }
}
