package com.purplepath.purplepath.settings.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 11-01-2017.
 */

public class User_per_det_Selec implements Serializable {
    private String duration;

    private String spouse_life_expectancy_age;

    private String debt_ratio;

    private String tax_cons;

    private String planned_retirement_age;

    private String output_report;

    private String processing;

    private String first_home;

    private String spouse_retirement_age;

    private String life_expectancy_age;

    private String input_details;

    private String exp_tax_per;

    public String getDuration ()
    {
        return duration;
    }

    public void setDuration (String duration)
    {
        this.duration = duration;
    }

    public String getSpouse_life_expectancy_age ()
    {
        return spouse_life_expectancy_age;
    }

    public void setSpouse_life_expectancy_age (String spouse_life_expectancy_age)
    {
        this.spouse_life_expectancy_age = spouse_life_expectancy_age;
    }

    public String getDebt_ratio ()
    {
        return debt_ratio;
    }

    public void setDebt_ratio (String debt_ratio)
    {
        this.debt_ratio = debt_ratio;
    }

    public String getTax_cons ()
    {
        return tax_cons;
    }

    public void setTax_cons (String tax_cons)
    {
        this.tax_cons = tax_cons;
    }

    public String getPlanned_retirement_age ()
    {
        return planned_retirement_age;
    }

    public void setPlanned_retirement_age (String planned_retirement_age)
    {
        this.planned_retirement_age = planned_retirement_age;
    }

    public String getOutput_report ()
    {
        return output_report;
    }

    public void setOutput_report (String output_report)
    {
        this.output_report = output_report;
    }

    public String getProcessing ()
    {
        return processing;
    }

    public void setProcessing (String processing)
    {
        this.processing = processing;
    }

    public String getFirst_home ()
    {
        return first_home;
    }

    public void setFirst_home (String first_home)
    {
        this.first_home = first_home;
    }

    public String getSpouse_retirement_age ()
    {
        return spouse_retirement_age;
    }

    public void setSpouse_retirement_age (String spouse_retirement_age)
    {
        this.spouse_retirement_age = spouse_retirement_age;
    }

    public String getLife_expectancy_age ()
    {
        return life_expectancy_age;
    }

    public void setLife_expectancy_age (String life_expectancy_age)
    {
        this.life_expectancy_age = life_expectancy_age;
    }

    public String getInput_details ()
    {
        return input_details;
    }

    public void setInput_details (String input_details)
    {
        this.input_details = input_details;
    }

    public String getExp_tax_per ()
    {
        return exp_tax_per;
    }

    public void setExp_tax_per (String exp_tax_per)
    {
        this.exp_tax_per = exp_tax_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [duration = "+duration+", spouse_life_expectancy_age = "+spouse_life_expectancy_age+", debt_ratio = "+debt_ratio+", tax_cons = "+tax_cons+", planned_retirement_age = "+planned_retirement_age+", output_report = "+output_report+", processing = "+processing+", first_home = "+first_home+", spouse_retirement_age = "+spouse_retirement_age+", life_expectancy_age = "+life_expectancy_age+", input_details = "+input_details+", exp_tax_per = "+exp_tax_per+"]";
    }
}
