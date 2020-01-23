package com.purplepath.purplepath.AppManagement.Survey.model;

import java.io.Serializable;

/**
 * Created by marut on 18-08-2017.
 */

public class Surveyto implements Serializable {

    private String user_id;

    private String survey_id;

    private String option;

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getSurvey_id ()
    {
        return survey_id;
    }

    public void setSurvey_id (String survey_id)
    {
        this.survey_id = survey_id;
    }

    public String getOption ()
    {
        return option;
    }

    public void setOption (String option)
    {
        this.option = option;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user_id = "+user_id+", survey_id = "+survey_id+", option = "+option+"]";
    }
}
