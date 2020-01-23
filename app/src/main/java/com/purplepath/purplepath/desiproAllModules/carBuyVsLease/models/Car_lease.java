package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 23/01/18.
 */

public class Car_lease implements Serializable{
    private String total_lease_years;

    private String overall_lease_value;

    private ArrayList<Cashflowlease>  cashflow;

    public ArrayList<Cashflowlease> getCashflow() {
        return cashflow;
    }

    public void setCashflow(ArrayList<Cashflowlease> cashflow) {
        this.cashflow = cashflow;
    }

//    public String getTotal_cash_years ()
//    {
//        return total_cash_years;
//    }
//
//    public void setTotal_cash_years (String total_cash_years)
//    {
//        this.total_cash_years = total_cash_years;
//    }

//    public String getOverall_cash_value ()
//    {
//        return overall_lease_value;
//    }
//
//    public void setOverall_cash_value (String overall_cash_value)
//    {
//        this.overall_lease_value = overall_cash_value;
//    }

//    public Cashflowlease[] getCashflow ()
//    {
//        return cashflow;
//    }
//
//    public void setCashflow (Cashflowlease[] cashflow)
//    {
//        this.cashflow = cashflow;
//    }


    public String getTotal_lease_years() {
        return total_lease_years;
    }

    public void setTotal_lease_years(String total_lease_years) {
        this.total_lease_years = total_lease_years;
    }

    public String getOverall_lease_value() {
        return overall_lease_value;
    }

    public void setOverall_lease_value(String overall_lease_value) {
        this.overall_lease_value = overall_lease_value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total_cash_years = "+total_lease_years+", overall_cash_value = "+overall_lease_value+", cashflow = "+cashflow+"]";
    }
}
