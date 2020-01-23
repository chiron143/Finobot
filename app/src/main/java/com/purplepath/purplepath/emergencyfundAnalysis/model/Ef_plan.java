package com.purplepath.purplepath.emergencyfundAnalysis.model;

import java.io.Serializable;

/**
 * Created by dinesh on 12/10/16.
 */
public class Ef_plan implements Serializable {
    private String plan_savings_acc_per;

    private String plan_curr_acc_per;

    private String plan_overall_per;

    private String plan_fix_recc_dep_per;

    private String plan_cash_per;

    private String plan_term_dep_per;

    public String getPlan_savings_acc_per ()
    {
        return plan_savings_acc_per;
    }

    public void setPlan_savings_acc_per (String plan_savings_acc_per)
    {
        this.plan_savings_acc_per = plan_savings_acc_per;
    }

    public String getPlan_curr_acc_per ()
    {
        return plan_curr_acc_per;
    }

    public void setPlan_curr_acc_per (String plan_curr_acc_per)
    {
        this.plan_curr_acc_per = plan_curr_acc_per;
    }

    public String getPlan_overall_per ()
    {
        return plan_overall_per;
    }

    public void setPlan_overall_per (String plan_overall_per)
    {
        this.plan_overall_per = plan_overall_per;
    }

    public String getPlan_fix_recc_dep_per ()
    {
        return plan_fix_recc_dep_per;
    }

    public void setPlan_fix_recc_dep_per (String plan_fix_recc_dep_per)
    {
        this.plan_fix_recc_dep_per = plan_fix_recc_dep_per;
    }

    public String getPlan_cash_per ()
    {
        return plan_cash_per;
    }

    public void setPlan_cash_per (String plan_cash_per)
    {
        this.plan_cash_per = plan_cash_per;
    }

    public String getPlan_term_dep_per ()
    {
        return plan_term_dep_per;
    }

    public void setPlan_term_dep_per (String plan_term_dep_per)
    {
        this.plan_term_dep_per = plan_term_dep_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [plan_savings_acc_per = "+plan_savings_acc_per+", plan_curr_acc_per = "+plan_curr_acc_per+", plan_overall_per = "+plan_overall_per+", plan_fix_recc_dep_per = "+plan_fix_recc_dep_per+", plan_cash_per = "+plan_cash_per+", plan_term_dep_per = "+plan_term_dep_per+"]";
    }
}
