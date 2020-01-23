package com.purplepath.purplepath.ScoreChartAnalysis.model;

import java.io.Serializable;

/**
 * Created by dinesh on 30/09/16.
 */
public class Data implements Serializable{

    private String message;

    private Pp_score pp_score;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Pp_score getPp_score ()
    {
        return pp_score;
    }

    public void setPp_score (Pp_score pp_score)
    {
        this.pp_score = pp_score;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", pp_score = "+pp_score+"]";
    }
}
