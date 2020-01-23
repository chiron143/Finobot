package com.purplepath.purplepath.propertyinsurance.model;

import com.purplepath.purplepath.propertyinsurance.healthinsurancemodel.Health_ins_plan;
import com.purplepath.purplepath.propertyinsurance.motorinsurancemodel.Motor_ins_plan;
import com.purplepath.purplepath.propertyinsurance.propertymodel.Property_ins_plan;

import java.io.Serializable;


/**
 * Created by pravinr on 10/27/17.
 */

public class Data implements Serializable{
    private String message;

    private Motor_ins_plan motor_ins_plan;

    public Property_ins_plan getProp_ins_plan() {
        return prop_ins_plan;
    }

    public void setProp_ins_plan(Property_ins_plan prop_ins_plan) {
        this.prop_ins_plan = prop_ins_plan;
    }

    private Health_ins_plan health_ins_plan;

    private Property_ins_plan prop_ins_plan;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Motor_ins_plan getMotor_ins_plan ()
    {
        return motor_ins_plan;
    }

    public void setMotor_ins_plan (Motor_ins_plan motor_ins_plan)
    {
        this.motor_ins_plan = motor_ins_plan;
    }

    public Health_ins_plan getHealth_ins_plan ()
    {
        return health_ins_plan;
    }

    public void setHealth_ins_plan (Health_ins_plan health_ins_plan)
    {
        this.health_ins_plan = health_ins_plan;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", motor_ins_plan = "+motor_ins_plan+", " +
                "health_ins_plan = "+health_ins_plan+",prop_ins_plan = "+prop_ins_plan+"]";
    }
}

