package com.purplepath.purplepath.emergencyfundAnalysis.model;

import java.io.Serializable;

/**
 * Created by dinesh on 12/10/16.
 */
public class Ef_act implements Serializable {
    private String act_savings_acc_per;

    private String act_fix_recc_dep_per;

    private String act_term_dep_per;

    private String act_curr_acc_per;

    private String act_overall_per;

    private String act_cash_per;

    public String getAct_savings_acc_per ()
    {
        return act_savings_acc_per;
    }

    public void setAct_savings_acc_per (String act_savings_acc_per)
    {
        this.act_savings_acc_per = act_savings_acc_per;
    }

    public String getAct_fix_recc_dep_per ()
    {
        return act_fix_recc_dep_per;
    }

    public void setAct_fix_recc_dep_per (String act_fix_recc_dep_per)
    {
        this.act_fix_recc_dep_per = act_fix_recc_dep_per;
    }

    public String getAct_term_dep_per ()
    {
        return act_term_dep_per;
    }

    public void setAct_term_dep_per (String act_term_dep_per)
    {
        this.act_term_dep_per = act_term_dep_per;
    }

    public String getAct_curr_acc_per ()
    {
        return act_curr_acc_per;
    }

    public void setAct_curr_acc_per (String act_curr_acc_per)
    {
        this.act_curr_acc_per = act_curr_acc_per;
    }

    public String getAct_overall_per ()
    {
        return act_overall_per;
    }

    public void setAct_overall_per (String act_overall_per)
    {
        this.act_overall_per = act_overall_per;
    }

    public String getAct_cash_per ()
    {
        return act_cash_per;
    }

    public void setAct_cash_per (String act_cash_per)
    {
        this.act_cash_per = act_cash_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [act_savings_acc_per = "+act_savings_acc_per+", act_fix_recc_dep_per = "+act_fix_recc_dep_per+", act_term_dep_per = "+act_term_dep_per+", act_curr_acc_per = "+act_curr_acc_per+", act_overall_per = "+act_overall_per+", act_cash_per = "+act_cash_per+"]";
    }
}
