package com.purplepath.purplepath.riskAssesment.models;

import java.io.Serializable;

/**
 * Created by bertrandrussellsakthees on 12/01/17.
 */

public class Risk_Profile_Questionnaries implements Serializable {


        private String option_2;

        private String option_3;

        private String option_4;

        private String option_5;

        private String option_1;

        private String point_4;

        private String point_3;

        private String category;

        private String point_5;

        private String point_2;

        private String created_datetime;

        private String point_1;

        private String active;

        private String question;

        private String modified_datetime;

        private String risk_ques_id;

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

    public String getOption_4 ()
    {
        return option_4;
    }

    public void setOption_4 (String option_4)
    {
        this.option_4 = option_4;
    }

    public String getOption_5 ()
    {
        return option_5;
    }

    public void setOption_5 (String option_5)
    {
        this.option_5 = option_5;
    }

    public String getOption_1 ()
    {
        return option_1;
    }

    public void setOption_1 (String option_1)
    {
        this.option_1 = option_1;
    }

    public String getPoint_4 ()
    {
        return point_4;
    }

    public void setPoint_4 (String point_4)
    {
        this.point_4 = point_4;
    }

    public String getPoint_3 ()
    {
        return point_3;
    }

    public void setPoint_3 (String point_3)
    {
        this.point_3 = point_3;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getPoint_5 ()
    {
        return point_5;
    }

    public void setPoint_5 (String point_5)
    {
        this.point_5 = point_5;
    }

    public String getPoint_2 ()
    {
        return point_2;
    }

    public void setPoint_2 (String point_2)
    {
        this.point_2 = point_2;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getPoint_1 ()
    {
        return point_1;
    }

    public void setPoint_1 (String point_1)
    {
        this.point_1 = point_1;
    }

    public String getActive ()
    {
        return active;
    }

    public void setActive (String active)
    {
        this.active = active;
    }

    public String getQuestion ()
    {
        return question;
    }

    public void setQuestion (String question)
    {
        this.question = question;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getRisk_ques_id ()
    {
        return risk_ques_id;
    }

    public void setRisk_ques_id (String risk_ques_id)
    {
        this.risk_ques_id = risk_ques_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [option_2 = "+option_2+", option_3 = "+option_3+", option_4 = "+option_4+", option_5 = "+option_5+", option_1 = "+option_1+", point_4 = "+point_4+", point_3 = "+point_3+", category = "+category+", point_5 = "+point_5+", point_2 = "+point_2+", created_datetime = "+created_datetime+", point_1 = "+point_1+", active = "+active+", question = "+question+", modified_datetime = "+modified_datetime+", risk_ques_id = "+risk_ques_id+"]";
    }
}
