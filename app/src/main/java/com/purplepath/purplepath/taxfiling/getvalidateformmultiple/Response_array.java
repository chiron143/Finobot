package com.purplepath.purplepath.taxfiling.getvalidateformmultiple;

import java.io.Serializable;

/**
 * Created by pravinr on 7/5/18.
 */

public class Response_array implements Serializable {
    private String AssessmentYear;

    private String PartB;

    private String PartA;

    public String getAssessmentYear ()
    {
        return AssessmentYear;
    }

    public void setAssessmentYear (String AssessmentYear)
    {
        this.AssessmentYear = AssessmentYear;
    }

    public String getPartB ()
    {
        return PartB;
    }

    public void setPartB (String PartB)
    {
        this.PartB = PartB;
    }

    public String getPartA ()
    {
        return PartA;
    }

    public void setPartA (String PartA)
    {
        this.PartA = PartA;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [AssessmentYear = "+AssessmentYear+", PartB = "+PartB+", PartA = "+PartA+"]";
    }
}

