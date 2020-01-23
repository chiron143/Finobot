package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 23/01/18.
 */

public class Car_cash  implements Serializable{

    private String total_cash_years;

    private String overall_cash_value;

    private ArrayList<Cashflow_carcash> cashflow;

    public String getTotal_cash_years ()
    {
        return total_cash_years;
    }

    public void setTotal_cash_years (String total_cash_years)
    {
        this.total_cash_years = total_cash_years;
    }

    public String getOverall_cash_value ()
    {
        return overall_cash_value;
    }

    public void setOverall_cash_value (String overall_cash_value)
    {
        this.overall_cash_value = overall_cash_value;
    }

    public ArrayList<Cashflow_carcash> getCashflow() {
        return cashflow;
    }

    public void setCashflow(ArrayList<Cashflow_carcash> cashflow) {
        this.cashflow = cashflow;
    }
//    public Cashflow_carcash[] getCashflow ()
//    {
//        return cashflow;
//    }
//
//    public void setCashflow (Cashflow_carcash[] cashflow)
//    {
//        this.cashflow = cashflow;
//    }

    @Override
    public String toString()
    {
        return "ClassPojo [total_cash_years = "+total_cash_years+", overall_cash_value = "+overall_cash_value+", cashflow = "+cashflow+"]";
    }
}
