package com.purplepath.purplepath.propertyinsurance.healthinsurancemodel;

import java.io.Serializable;

/**
 * Created by pravinr on 11/7/17.
 */

public class Health_ins_plan implements Serializable {
    private Family_floater_plan family_floater_plan;

    private Individual_health_plan individual_health_plan;

    public Family_floater_plan getFamily_floater_plan ()
    {
        return family_floater_plan;
    }

    public void setFamily_floater_plan (Family_floater_plan family_floater_plan)
    {
        this.family_floater_plan = family_floater_plan;
    }

    public Individual_health_plan getIndividual_health_plan ()
    {
        return individual_health_plan;
    }

    public void setIndividual_health_plan (Individual_health_plan individual_health_plan)
    {
        this.individual_health_plan = individual_health_plan;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [family_floater_plan = "+family_floater_plan+", individual_health_plan = "+individual_health_plan+"]";
    }
}

