package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 25-09-2017.
 */

public class Cashflow implements Serializable {
    private String tax_savings;

    private String foregone_inerest;

    private String tenure;

    private String rent_cash_difference;

    private String tax_savings_deductions;

    private String net_cost_of_buying;

    private String loan_cash_difference;

    private String gross_cost_of_buying;

    public String getTax_savings ()
    {
        return tax_savings;
    }

    public void setTax_savings (String tax_savings)
    {
        this.tax_savings = tax_savings;
    }

    public String getForegone_inerest ()
    {
        return foregone_inerest;
    }

    public void setForegone_inerest (String foregone_inerest)
    {
        this.foregone_inerest = foregone_inerest;
    }

    public String getTenure ()
    {
        return tenure;
    }

    public void setTenure (String tenure)
    {
        this.tenure = tenure;
    }

    public String getRent_cash_difference ()
    {
        return rent_cash_difference;
    }

    public void setRent_cash_difference (String rent_cash_difference)
    {
        this.rent_cash_difference = rent_cash_difference;
    }

    public String getTax_savings_deductions ()
    {
        return tax_savings_deductions;
    }

    public void setTax_savings_deductions (String tax_savings_deductions)
    {
        this.tax_savings_deductions = tax_savings_deductions;
    }

    public String getNet_cost_of_buying ()
    {
        return net_cost_of_buying;
    }

    public void setNet_cost_of_buying (String net_cost_of_buying)
    {
        this.net_cost_of_buying = net_cost_of_buying;
    }

    public String getLoan_cash_difference ()
    {
        return loan_cash_difference;
    }

    public void setLoan_cash_difference (String loan_cash_difference)
    {
        this.loan_cash_difference = loan_cash_difference;
    }

    public String getGross_cost_of_buying ()
    {
        return gross_cost_of_buying;
    }

    public void setGross_cost_of_buying (String gross_cost_of_buying)
    {
        this.gross_cost_of_buying = gross_cost_of_buying;
    }
}
