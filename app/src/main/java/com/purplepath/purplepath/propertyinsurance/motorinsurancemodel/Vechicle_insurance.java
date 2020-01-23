package com.purplepath.purplepath.propertyinsurance.motorinsurancemodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 11/22/17.
 */

public class Vechicle_insurance implements Serializable {



    private ArrayList<Ins_by_motor_type> ins_by_motor_type;

    public ArrayList<Ins_by_motor_type> getIns_by_motor_type() {
        return ins_by_motor_type;
    }

    public void setIns_by_motor_type(ArrayList<Ins_by_motor_type> ins_by_motor_type) {
        this.ins_by_motor_type = ins_by_motor_type;
    }

    private String overall_value;


    public String getOverall_value ()
    {
        return overall_value;
    }

    public void setOverall_value (String overall_value)
    {
        this.overall_value = overall_value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ins_by_motor_type = "+ins_by_motor_type+", overall_value = "+overall_value+"]";
    }
}

