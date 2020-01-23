package com.purplepath.purplepath.settings.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 05-01-2017.
 */

public class Sas_assumptions implements Serializable
{
    private String guideline_value;

    private String assumed_value;

    private String category;

    private String published_value;

    public String getGuideline_value ()
    {
        return guideline_value;
    }

    public void setGuideline_value (String guideline_value)
    {
        this.guideline_value = guideline_value;
    }

    public String getAssumed_value ()
    {
        return assumed_value;
    }

    public void setAssumed_value (String assumed_value)
    {
        this.assumed_value = assumed_value;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setClass (String category)
    {
        this.category = category;
    }

    public String getPublished_value ()
    {
        return published_value;
    }

    public void setPublished_value (String published_value)
    {
        this.published_value = published_value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [guideline_value = "+guideline_value+", assumed_value = "+assumed_value+", category = "+category+", published_value = "+published_value+"]";
    }
}

