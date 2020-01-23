package com.purplepath.purplepath.riskAssesment.models;

import com.purplepath.purplepath.taxanalysis.modes.Tax_plan;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bertrandrussellsakthees on 12/01/17.
 */

public class Data  implements Serializable{

    private String message;

    private ArrayList<Selected_options> selected_options;

    private ArrayList<Risk_Profile_Questionnaries> Risk_Profile_Questionnaries;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Risk_Profile_Questionnaries> getRisk_Profile_Questionnaries() {
        return Risk_Profile_Questionnaries;
    }

    public void setRisk_Profile_Questionnaries(ArrayList<Risk_Profile_Questionnaries> risk_Profile_Questionnaries) {
        Risk_Profile_Questionnaries = risk_Profile_Questionnaries;
    }

    public ArrayList<Selected_options> getSelected_options() {
        return selected_options;
    }

    public void setSelected_options(ArrayList<Selected_options> selected_options) {
        this.selected_options = selected_options;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", Risk_Profile_Questionnaries = "+Risk_Profile_Questionnaries+", selected_options = "+selected_options+"]";
    }
}
