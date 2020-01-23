package com.purplepath.purplepath.propertyinsurance.motorinsurancemodel;

import java.io.Serializable;

/**
 * Created by pravinr on 10/27/17.
 */

public class Motor_ins_plan implements Serializable {

        private Vechicle_asset vechicle_asset;

        private Vechicle_insurance vechicle_insurance;

    public Vechicle_asset getVechicle_asset ()
    {
        return vechicle_asset;
    }

    public void setVechicle_asset (Vechicle_asset vechicle_asset)
    {
        this.vechicle_asset = vechicle_asset;
    }

    public Vechicle_insurance getVechicle_insurance ()
    {
        return vechicle_insurance;
    }

    public void setVechicle_insurance (Vechicle_insurance vechicle_insurance)
    {
        this.vechicle_insurance = vechicle_insurance;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [vechicle_asset = "+vechicle_asset+", vechicle_insurance = "+vechicle_insurance+"]";
    }
}

