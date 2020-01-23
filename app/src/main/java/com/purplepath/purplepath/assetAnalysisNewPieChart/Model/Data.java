package com.purplepath.purplepath.assetAnalysisNewPieChart.Model;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 03-05-2017.
 */

public class Data implements Serializable {

    private String message;

    private Asst_analysis asst_analysis;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Asst_analysis getAsst_analysis ()
    {
        return asst_analysis;
    }

    public void setAsst_analysis (Asst_analysis asst_analysis)
    {
        this.asst_analysis = asst_analysis;
    }

}
