package com.purplepath.purplepath.desiproAllModules.amortizationSchedule.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 30-05-2017.
 */

public class Amtz_sch implements Serializable {

    private String cum_principal;

    private String out_bal_start;

    private String loan_principal;

    private String loan_interest;

    private String cum_payment;

    private String emi;

    private String out_bal_end;

    private String tenure;

    private String cum_interest;

    private String service_tax;

    private String tot_payment;

    public String getCum_tax() {
        return cum_tax;
    }

    public void setCum_tax(String cum_tax) {
        this.cum_tax = cum_tax;
    }

    private String cum_tax;

    public String getCum_principal ()
    {
        return cum_principal;
    }

    public void setCum_principal (String cum_principal)
    {
        this.cum_principal = cum_principal;
    }

    public String getOut_bal_start ()
    {
        return out_bal_start;
    }

    public void setOut_bal_start (String out_bal_start)
    {
        this.out_bal_start = out_bal_start;
    }

    public String getLoan_principal ()
    {
        return loan_principal;
    }

    public void setLoan_principal (String loan_principal)
    {
        this.loan_principal = loan_principal;
    }

    public String getLoan_interest ()
    {
        return loan_interest;
    }

    public void setLoan_interest (String loan_interest)
    {
        this.loan_interest = loan_interest;
    }

    public String getCum_payment ()
    {
        return cum_payment;
    }

    public void setCum_payment (String cum_payment)
    {
        this.cum_payment = cum_payment;
    }

    public String getEmi ()
    {
        return emi;
    }

    public void setEmi (String emi)
    {
        this.emi = emi;
    }

    public String getOut_bal_end ()
    {
        return out_bal_end;
    }

    public void setOut_bal_end (String out_bal_end)
    {
        this.out_bal_end = out_bal_end;
    }

    public String getTenure ()
    {
        return tenure;
    }

    public void setTenure (String tenure)
    {
        this.tenure = tenure;
    }

    public String getCum_interest ()
    {
        return cum_interest;
    }

    public void setCum_interest (String cum_interest)
    {
        this.cum_interest = cum_interest;
    }

    public String getService_tax ()
    {
        return service_tax;
    }

    public void setService_tax (String service_tax)
    {
        this.service_tax = service_tax;
    }

    public String getTot_payment ()
    {
        return tot_payment;
    }

    public void setTot_payment (String tot_payment)
    {
        this.tot_payment = tot_payment;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cum_principal = "+cum_principal+", out_bal_start = "+out_bal_start+", loan_principal = "+loan_principal+", loan_interest = "+loan_interest+", cum_payment = "+cum_payment+", emi = "+emi+", out_bal_end = "+out_bal_end+", tenure = "+tenure+", cum_interest = "+cum_interest+", service_tax = "+service_tax+", tot_payment = "+tot_payment+"]";
    }
}
