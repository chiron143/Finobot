package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models;

import java.io.Serializable;

/**
 * Created by dinesh on 23/01/18.
 */

public class Cashflow_carcash implements Serializable{

    private String cash_insurance;

    private String cash_payment;

    private String tenure;

    private String tot_cost_buying_cash;

    private String cash_fuel_run_exp;

    private String cash_foregone_int;

    private String cash_main_repair;

    private String lease_cash_difference;

    private String loan_cash_difference;

    public String getLease_cash_difference() {
        return lease_cash_difference;
    }

    public void setLease_cash_difference(String lease_cash_difference) {
        this.lease_cash_difference = lease_cash_difference;
    }

    public String getLoan_cash_difference() {
        return loan_cash_difference;
    }

    public void setLoan_cash_difference(String loan_cash_difference) {
        this.loan_cash_difference = loan_cash_difference;
    }

    public String getCash_insurance ()
    {
        return cash_insurance;
    }

    public void setCash_insurance (String cash_insurance)
    {
        this.cash_insurance = cash_insurance;
    }

    public String getCash_payment ()
    {
        return cash_payment;
    }

    public void setCash_payment (String cash_payment)
    {
        this.cash_payment = cash_payment;
    }

    public String getTenure ()
    {
        return tenure;
    }

    public void setTenure (String tenure)
    {
        this.tenure = tenure;
    }

    public String getTot_cost_buying_cash ()
    {
        return tot_cost_buying_cash;
    }

    public void setTot_cost_buying_cash (String tot_cost_buying_cash)
    {
        this.tot_cost_buying_cash = tot_cost_buying_cash;
    }

    public String getCash_fuel_run_exp ()
    {
        return cash_fuel_run_exp;
    }

    public void setCash_fuel_run_exp (String cash_fuel_run_exp)
    {
        this.cash_fuel_run_exp = cash_fuel_run_exp;
    }

    public String getCash_foregone_int ()
    {
        return cash_foregone_int;
    }

    public void setCash_foregone_int (String cash_foregone_int)
    {
        this.cash_foregone_int = cash_foregone_int;
    }

    public String getCash_main_repair ()
    {
        return cash_main_repair;
    }

    public void setCash_main_repair (String cash_main_repair)
    {
        this.cash_main_repair = cash_main_repair;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cash_insurance = "+cash_insurance+", cash_payment = "+cash_payment+", tenure = "+tenure+", tot_cost_buying_cash = "+tot_cost_buying_cash+", cash_fuel_run_exp = "+cash_fuel_run_exp+", cash_foregone_int = "+cash_foregone_int+", cash_main_repair = "+cash_main_repair+"]";
    }
}
