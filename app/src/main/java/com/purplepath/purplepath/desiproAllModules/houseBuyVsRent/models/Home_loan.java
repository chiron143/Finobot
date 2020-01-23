package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 25-09-2017.
 */

public class Home_loan implements Serializable {
    private String total_years;

    private String tot_net_annu_cost_of_buying;

    private ArrayList<Cashflow3> cashflow;

    public String getTotal_years ()
    {
        return total_years;
    }

    public void setTotal_years (String total_years)
    {
        this.total_years = total_years;
    }

    public String getTot_net_annu_cost_of_buying ()
    {
        return tot_net_annu_cost_of_buying;
    }

    public void setTot_net_annu_cost_of_buying (String tot_net_annu_cost_of_buying)
    {
        this.tot_net_annu_cost_of_buying = tot_net_annu_cost_of_buying;
    }

    public ArrayList<Cashflow3> getCashflow ()
    {
        return cashflow;
    }

    public void setCashflow (ArrayList<Cashflow3> cashflow)
    {
        this.cashflow = cashflow;
    }
}
