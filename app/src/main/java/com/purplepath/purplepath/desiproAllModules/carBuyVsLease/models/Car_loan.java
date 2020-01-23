package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 23/01/18.
 */

public class Car_loan implements Serializable {
    private String overall_loan_value;

    private String total_loan_years;

    private ArrayList<Cashflowloan> cashflow;

    public String getOverall_loan_value ()
    {
        return overall_loan_value;
    }

    public void setOverall_loan_value (String overall_loan_value)
    {
        this.overall_loan_value = overall_loan_value;
    }

    public String getTotal_loan_years ()
    {
        return total_loan_years;
    }

    public void setTotal_loan_years (String total_loan_years)
    {
        this.total_loan_years = total_loan_years;
    }

//    public Cashflowloan[] getCashflow ()
//    {
//        return cashflow;
//    }
//
//    public void setCashflow (Cashflowloan[] cashflow)
//    {
//        this.cashflow = cashflow;
//    }

    public ArrayList<Cashflowloan> getCashflow() {
        return cashflow;
    }

    public void setCashflow(ArrayList<Cashflowloan> cashflow) {
        this.cashflow = cashflow;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [overall_loan_value = "+overall_loan_value+", total_loan_years = "+total_loan_years+", cashflow = "+cashflow+"]";
    }
}
