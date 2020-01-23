package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models;

import java.io.Serializable;

/**
 * Created by dinesh on 23/01/18.
 */

public class Cashflowloan implements Serializable {

    private String loan_payment;

    private String loan_principal;

    private String down_pay;

    private String loan_insurance;

    private String loan_interest;

    private String loan_foregone_int;

    private String loan_out_bal;

    private String tenure;

    private String loan_fuel_run_exp;

    private String tot_cost_buying_loan;

    private String loan_main_repair;

    private String lease_loan_difference;

    public String getLease_loan_difference() {
        return lease_loan_difference;
    }

    public void setLease_loan_difference(String lease_loan_difference) {
        this.lease_loan_difference = lease_loan_difference;
    }

    public String getLoan_payment ()
    {
        return loan_payment;
    }

    public void setLoan_payment (String loan_payment)
    {
        this.loan_payment = loan_payment;
    }

    public String getLoan_principal ()
    {
        return loan_principal;
    }

    public void setLoan_principal (String loan_principal)
    {
        this.loan_principal = loan_principal;
    }

    public String getDown_pay ()
    {
        return down_pay;
    }

    public void setDown_pay (String down_pay)
    {
        this.down_pay = down_pay;
    }

    public String getLoan_insurance ()
    {
        return loan_insurance;
    }

    public void setLoan_insurance (String loan_insurance)
    {
        this.loan_insurance = loan_insurance;
    }

    public String getLoan_interest ()
    {
        return loan_interest;
    }

    public void setLoan_interest (String loan_interest)
    {
        this.loan_interest = loan_interest;
    }

    public String getLoan_foregone_int ()
    {
        return loan_foregone_int;
    }

    public void setLoan_foregone_int (String loan_foregone_int)
    {
        this.loan_foregone_int = loan_foregone_int;
    }

    public String getLoan_out_bal ()
    {
        return loan_out_bal;
    }

    public void setLoan_out_bal (String loan_out_bal)
    {
        this.loan_out_bal = loan_out_bal;
    }

    public String getTenure ()
    {
        return tenure;
    }

    public void setTenure (String tenure)
    {
        this.tenure = tenure;
    }

    public String getLoan_fuel_run_exp ()
    {
        return loan_fuel_run_exp;
    }

    public void setLoan_fuel_run_exp (String loan_fuel_run_exp)
    {
        this.loan_fuel_run_exp = loan_fuel_run_exp;
    }

    public String getTot_cost_buying_loan ()
    {
        return tot_cost_buying_loan;
    }

    public void setTot_cost_buying_loan (String tot_cost_buying_loan)
    {
        this.tot_cost_buying_loan = tot_cost_buying_loan;
    }

    public String getLoan_main_repair ()
    {
        return loan_main_repair;
    }

    public void setLoan_main_repair (String loan_main_repair)
    {
        this.loan_main_repair = loan_main_repair;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [loan_payment = "+loan_payment+", loan_principal = "+loan_principal+", down_pay = "+down_pay+", loan_insurance = "+loan_insurance+", loan_interest = "+loan_interest+", loan_foregone_int = "+loan_foregone_int+", loan_out_bal = "+loan_out_bal+", tenure = "+tenure+", loan_fuel_run_exp = "+loan_fuel_run_exp+", tot_cost_buying_loan = "+tot_cost_buying_loan+", loan_main_repair = "+loan_main_repair+"]";
    }
}
