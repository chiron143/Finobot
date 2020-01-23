package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models;

import java.io.Serializable;

/**
 * Created by dinesh on 23/01/18.
 */

public class Cashflowlease implements Serializable{

    private String tot_lease_exp;

    private String lease_foregone_int;

    private String lease_insurance;

    private String lease_tax;

    private String yearly_lease_amt;

    private String tenure;

    private String lease_fuel_run_exp;

    private String upfront_expense;

    private String lease_ter_exp;

    public String getTot_lease_exp ()
    {
        return tot_lease_exp;
    }

    public void setTot_lease_exp (String tot_lease_exp)
    {
        this.tot_lease_exp = tot_lease_exp;
    }

    public String getLease_foregone_int ()
    {
        return lease_foregone_int;
    }

    public void setLease_foregone_int (String lease_foregone_int)
    {
        this.lease_foregone_int = lease_foregone_int;
    }

    public String getLease_insurance ()
    {
        return lease_insurance;
    }

    public void setLease_insurance (String lease_insurance)
    {
        this.lease_insurance = lease_insurance;
    }

    public String getLease_tax ()
    {
        return lease_tax;
    }

    public void setLease_tax (String lease_tax)
    {
        this.lease_tax = lease_tax;
    }

    public String getYearly_lease_amt ()
    {
        return yearly_lease_amt;
    }

    public void setYearly_lease_amt (String yearly_lease_amt)
    {
        this.yearly_lease_amt = yearly_lease_amt;
    }

    public String getTenure ()
    {
        return tenure;
    }

    public void setTenure (String tenure)
    {
        this.tenure = tenure;
    }

    public String getLease_fuel_run_exp ()
    {
        return lease_fuel_run_exp;
    }

    public void setLease_fuel_run_exp (String lease_fuel_run_exp)
    {
        this.lease_fuel_run_exp = lease_fuel_run_exp;
    }

    public String getUpfront_expense ()
    {
        return upfront_expense;
    }

    public void setUpfront_expense (String upfront_expense)
    {
        this.upfront_expense = upfront_expense;
    }

    public String getLease_ter_exp ()
    {
        return lease_ter_exp;
    }

    public void setLease_ter_exp (String lease_ter_exp)
    {
        this.lease_ter_exp = lease_ter_exp;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tot_lease_exp = "+tot_lease_exp+", lease_foregone_int = "+lease_foregone_int+", lease_insurance = "+lease_insurance+", lease_tax = "+lease_tax+", yearly_lease_amt = "+yearly_lease_amt+", tenure = "+tenure+", lease_fuel_run_exp = "+lease_fuel_run_exp+", upfront_expense = "+upfront_expense+", lease_ter_exp = "+lease_ter_exp+"]";
    }
}
