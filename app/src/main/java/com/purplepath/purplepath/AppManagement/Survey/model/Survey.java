package com.purplepath.purplepath.AppManagement.Survey.model;

import java.io.Serializable;

/**
 * Created by pravinr on 6/14/17.
 */

public class Survey implements Serializable {
    private String survey_ques_id;

    private String category;

    private String option_2;

    private String option_3;



    private String option_1;

    private String question;

    public String getSurvey_ques_id ()
    {
        return survey_ques_id;
    }

    public void setSurvey_ques_id (String survey_ques_id)
    {
        this.survey_ques_id = survey_ques_id;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getOption_2 ()
    {
        return option_2;
    }

    public void setOption_2 (String option_2)
    {
        this.option_2 = option_2;
    }

    public String getOption_3 ()
    {
        return option_3;
    }

    public void setOption_3 (String option_3)
    {
        this.option_3 = option_3;
    }









    public String getOption_1 ()
    {
        return option_1;
    }

    public void setOption_1 (String option_1)
    {
        this.option_1 = option_1;
    }

    public String getQuestion ()
    {
        return question;
    }

    public void setQuestion (String question)
    {
        this.question = question;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [survey_ques_id = "+survey_ques_id+", category = "+category+", option_2 = "+option_2+", option_3 = "+option_3+", option_1 = "+option_1+", question = "+question+"]";
    }
}

