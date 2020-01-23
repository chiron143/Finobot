package com.purplepath.purplepath.riskAssesment.models;

import java.io.Serializable;

/**
 * Created by bertrandrussellsakthees on 24/02/17.
 */
public class Selected_options implements Serializable {

    private String selected_option_score;

    private String risk_ques_id;

    private String selected_option;

    public String getSelected_option_score ()
    {
        return selected_option_score;
    }

    public void setSelected_option_score (String selected_option_score)
    {
        this.selected_option_score = selected_option_score;
    }

    public String getRisk_ques_id ()
    {
        return risk_ques_id;
    }

    public void setRisk_ques_id (String risk_ques_id)
    {
        this.risk_ques_id = risk_ques_id;
    }

    public String getSelected_option ()
    {
        return selected_option;
    }

    public void setSelected_option (String selected_option)
    {
        this.selected_option = selected_option;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [selected_option_score = "+selected_option_score+", risk_ques_id = "+risk_ques_id+", selected_option = "+selected_option+"]";
    }


}
