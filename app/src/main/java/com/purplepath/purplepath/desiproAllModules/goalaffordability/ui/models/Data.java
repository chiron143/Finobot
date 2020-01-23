package com.purplepath.purplepath.desiproAllModules.goalaffordability.ui.models;

import java.io.Serializable;

/**
 * Created by bertrandrussellsakthees on 17/08/17.
 */

public class Data implements Serializable {

    private String message;

    private String result;

    private String fv;

    private String tenure;

    private String pmt;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getResult ()
    {
        return result;
    }

    public void setResult (String result)
    {
        this.result = result;
    }

    public String getFv ()
    {
        return fv;
    }

    public void setFv (String fv)
    {
        this.fv = fv;
    }

    public String getTenure ()
    {
        return tenure;
    }

    public void setTenure (String tenure)
    {
        this.tenure = tenure;
    }

    public String getPmt ()
    {
        return pmt;
    }

    public void setPmt (String pmt)
    {
        this.pmt = pmt;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", result = "+result+", fv = "+fv+", tenure = "+tenure+", pmt = "+pmt+"]";
    }
}
