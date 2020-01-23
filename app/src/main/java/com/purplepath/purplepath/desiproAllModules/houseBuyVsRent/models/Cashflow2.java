package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models;

/**
 * Created by Pratheep.S on 25-09-2017.
 */

public class Cashflow2 {
    private String foregone_interest;

    private String tenure;

    private String tot_annu_cost_rent;

    public String getForegone_interest ()
    {
        return foregone_interest;
    }

    public void setForegone_interest (String foregone_interest)
    {
        this.foregone_interest = foregone_interest;
    }

    public String getTenure ()
    {
        return tenure;
    }

    public void setTenure (String tenure)
    {
        this.tenure = tenure;
    }

    public String getTot_annu_cost_rent ()
    {
        return tot_annu_cost_rent;
    }

    public void setTot_annu_cost_rent (String tot_annu_cost_rent)
    {
        this.tot_annu_cost_rent = tot_annu_cost_rent;
    }

}
