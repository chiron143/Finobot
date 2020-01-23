package com.purplepath.purplepath.recommendation.model;



import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable
{
    private String message;

    public ArrayList<Motor_ins_plan> motor_ins_plan;

    public ArrayList<Motor_ins_plan> getMotor_ins_plan() {
        return motor_ins_plan;
    }

    public void setMotor_ins_plan(ArrayList<Motor_ins_plan> motor_ins_plan) {
        this.motor_ins_plan = motor_ins_plan;
    }

    public ArrayList<Prop_ins_plan> getProp_ins_plan() {
        return prop_ins_plan;
    }

    public void setProp_ins_plan(ArrayList<Prop_ins_plan> prop_ins_plan) {
        this.prop_ins_plan = prop_ins_plan;
    }

    public ArrayList<Health_ins_plan> getHealth_ins_plan() {
        return health_ins_plan;
    }

    public void setHealth_ins_plan(ArrayList<Health_ins_plan> health_ins_plan) {
        this.health_ins_plan = health_ins_plan;
    }

    private Emg_fund emg_fund;

    public ArrayList<Health_ins_plan> health_ins_plan;

    private Ins_plan ins_plan;

    public ArrayList<Prop_ins_plan> prop_ins_plan;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }



    public Emg_fund getEmg_fund ()
    {
        return emg_fund;
    }

    public void setEmg_fund (Emg_fund emg_fund)
    {
        this.emg_fund = emg_fund;
    }

    public Ins_plan getIns_plan ()
    {
        return ins_plan;
    }

    public void setIns_plan (Ins_plan ins_plan)
    {
        this.ins_plan = ins_plan;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", motor_ins_plan = "+motor_ins_plan+", emg_fund = "+emg_fund+", health_ins_plan = "+health_ins_plan+", ins_plan = "+ins_plan+", prop_ins_plan = "+prop_ins_plan+"]";
    }
}

