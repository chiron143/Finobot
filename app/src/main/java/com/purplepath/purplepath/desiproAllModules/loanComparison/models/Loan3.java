package com.purplepath.purplepath.desiproAllModules.loanComparison.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 16-06-2017.
 */

public class Loan3 implements Serializable{

    private String stat_rate;

    private String irr_rate;

    private String tot_int_pay;

    private String tot_prin_pay;

    private String tot_pay;

    private String tot_pay_with_assoc_charge;

    private String eff_rate;

    public String getStat_rate ()
    {
        return stat_rate;
    }

    public void setStat_rate (String stat_rate)
    {
        this.stat_rate = stat_rate;
    }

    public String getIrr_rate ()
    {
        return irr_rate;
    }

    public void setIrr_rate (String irr_rate)
    {
        this.irr_rate = irr_rate;
    }

    public String getTot_int_pay ()
    {
        return tot_int_pay;
    }

    public void setTot_int_pay (String tot_int_pay)
    {
        this.tot_int_pay = tot_int_pay;
    }

    public String getTot_prin_pay ()
    {
        return tot_prin_pay;
    }

    public void setTot_prin_pay (String tot_prin_pay)
    {
        this.tot_prin_pay = tot_prin_pay;
    }

    public String getTot_pay ()
    {
        return tot_pay;
    }

    public void setTot_pay (String tot_pay)
    {
        this.tot_pay = tot_pay;
    }

    public String getTot_pay_with_assoc_charge ()
    {
        return tot_pay_with_assoc_charge;
    }

    public void setTot_pay_with_assoc_charge (String tot_pay_with_assoc_charge)
    {
        this.tot_pay_with_assoc_charge = tot_pay_with_assoc_charge;
    }

    public String getEff_rate ()
    {
        return eff_rate;
    }

    public void setEff_rate (String eff_rate)
    {
        this.eff_rate = eff_rate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [stat_rate = "+stat_rate+", irr_rate = "+irr_rate+", tot_int_pay = "+tot_int_pay+", tot_prin_pay = "+tot_prin_pay+", tot_pay = "+tot_pay+", tot_pay_with_assoc_charge = "+tot_pay_with_assoc_charge+", eff_rate = "+eff_rate+"]";
    }
}
