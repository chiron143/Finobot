package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 25-09-2017.
 */

public class Home_rent implements Serializable {
    private String total_years;

    private ArrayList<Cashflow2> cashflow;

    private String overall_renting_cost;

    public String getTotal_years ()
    {
        return total_years;
    }

    public void setTotal_years (String total_years)
    {
        this.total_years = total_years;
    }

    public ArrayList<Cashflow2> getCashflow ()
    {
        return cashflow;
    }

    public void setCashflow (ArrayList<Cashflow2> cashflow)
    {
        this.cashflow = cashflow;
    }

    public String getOverall_renting_cost ()
    {
        return overall_renting_cost;
    }

    public void setOverall_renting_cost (String overall_renting_cost)
    {
        this.overall_renting_cost = overall_renting_cost;
    }
}
