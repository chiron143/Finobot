package com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 05-04-2017.
 */

public class Option2 implements Serializable {
    private String real_savings;

    private String inst_saved_in_ten;

    private String overall_savings;

    private String pre_val_savings;

    private String home_loan_switch;

    private String add_exp;

    public String getReal_savings ()
    {
        return real_savings;
    }

    public void setReal_savings (String real_savings)
    {
        this.real_savings = real_savings;
    }

    public String getInst_saved_in_ten ()
    {
        return inst_saved_in_ten;
    }

    public void setInst_saved_in_ten (String inst_saved_in_ten)
    {
        this.inst_saved_in_ten = inst_saved_in_ten;
    }

    public String getOverall_savings ()
    {
        return overall_savings;
    }

    public void setOverall_savings (String overall_savings)
    {
        this.overall_savings = overall_savings;
    }

    public String getPre_val_savings ()
    {
        return pre_val_savings;
    }

    public void setPre_val_savings (String pre_val_savings)
    {
        this.pre_val_savings = pre_val_savings;
    }

    public String getHome_loan_switch ()
    {
        return home_loan_switch;
    }

    public void setHome_loan_switch (String home_loan_switch)
    {
        this.home_loan_switch = home_loan_switch;
    }

    public String getAdd_exp ()
    {
        return add_exp;
    }

    public void setAdd_exp (String add_exp)
    {
        this.add_exp = add_exp;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [real_savings = "+real_savings+", inst_saved_in_ten = "+inst_saved_in_ten+", overall_savings = "+overall_savings+", pre_val_savings = "+pre_val_savings+", home_loan_switch = "+home_loan_switch+", add_exp = "+add_exp+"]";
    }
}
