package com.purplepath.purplepath.settings.UpdateModels;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 12-01-2017.
 */

public class Updated_details implements Serializable{
    private String spouse_life_expectancy_age;

    private String debt_ratio;

    private String tax_cons;

    private String output_report;

    private String exp_tax_per;

    private String duration;

    private String planned_retirement_age;

    private String processing;

    private String user_id;

    private String first_home;

    private String life_expectancy_age;

    private String spouse_retirement_age;

    private String input_details;

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

    public String getOutput_report ()
    {
        return output_report;
    }

    public void setOutput_report (String output_report)
    {
        this.output_report = output_report;
    }

    public String getExp_tax_per ()
    {
        return exp_tax_per;
    }

    public void setExp_tax_per (String exp_tax_per)
    {
        this.exp_tax_per = exp_tax_per;
    }

    public String getDuration ()
    {
        return duration;
    }

    public void setDuration (String duration)
    {
        this.duration = duration;
    }

    public String getPlanned_retirement_age ()
    {
        return planned_retirement_age;
    }

    public void setPlanned_retirement_age (String planned_retirement_age)
    {
        this.planned_retirement_age = planned_retirement_age;
    }

    public String getProcessing ()
    {
        return processing;
    }

    public void setProcessing (String processing)
    {
        this.processing = processing;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getFirst_home ()
    {
        return first_home;
    }

    public void setFirst_home (String first_home)
    {
        this.first_home = first_home;
    }

    public String getLife_expectancy_age ()
    {
        return life_expectancy_age;
    }

    public void setLife_expectancy_age (String life_expectancy_age)
    {
        this.life_expectancy_age = life_expectancy_age;
    }

    public String getSpouse_retirement_age ()
    {
        return spouse_retirement_age;
    }

    public void setSpouse_retirement_age (String spouse_retirement_age)
    {
        this.spouse_retirement_age = spouse_retirement_age;
    }

    public String getInput_details ()
    {
        return input_details;
    }

    public void setInput_details (String input_details)
    {
        this.input_details = input_details;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [spouse_life_expectancy_age = "+spouse_life_expectancy_age+", debt_ratio = "+debt_ratio+", tax_cons = "+tax_cons+", output_report = "+output_report+", exp_tax_per = "+exp_tax_per+", duration = "+duration+", planned_retirement_age = "+planned_retirement_age+", processing = "+processing+", user_id = "+user_id+", first_home = "+first_home+", life_expectancy_age = "+life_expectancy_age+", spouse_retirement_age = "+spouse_retirement_age+", input_details = "+input_details+"]";
    }

}
