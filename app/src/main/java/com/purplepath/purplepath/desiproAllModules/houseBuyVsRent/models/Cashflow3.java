package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 25-09-2017.
 */

public class Cashflow3 implements Serializable
{
    private String tenure;
    private String property_price;
    private String property_value;
    private String mortage_payment;
    private String mortage_interest;
    private String tax_24;

    private String mortage_principal;//
    private String tax_80c;

    private String tot_int_on_mortgage;
    private String tax_savings;

    private String net_annu_cost_of_buying;//
    private String rent_loan_difference;//

    public String getTenure() { return this.tenure; }

    public void setTenure(String tenure) { this.tenure = tenure; }



    public String getPropertyPrice() { return this.property_price; }

    public void setPropertyPrice(String property_price) { this.property_price = property_price; }



    public String getPropertyValue() { return this.property_value; }

    public void setPropertyValue(String property_value) { this.property_value = property_value; }



    public String getMortageInterest() { return this.mortage_interest; }

    public void setMortageInterest(String mortage_interest) { this.mortage_interest = mortage_interest; }



    public String getTotIntOnMortgage() { return this.tot_int_on_mortgage; }

    public void setTotIntOnMortgage(String tot_int_on_mortgage) { this.tot_int_on_mortgage = tot_int_on_mortgage; }


    public String getTaxSavings() { return this.tax_savings; }

    public void setTaxSavings(String tax_savings) { this.tax_savings = tax_savings; }



    public String getMortagePrincipal() { return this.mortage_principal; }

    public void setMortagePrincipal(String mortage_principal) { this.mortage_principal = mortage_principal; }



    public String getNetAnnuCostOfBuying() { return this.net_annu_cost_of_buying; }

    public void setNetAnnuCostOfBuying(String net_annu_cost_of_buying) { this.net_annu_cost_of_buying = net_annu_cost_of_buying; }


    public  String getMortagePayment() { return this.mortage_payment; }

    public void setMortagePayment(String mortage_payment) { this.mortage_payment = mortage_payment; }



    public String getTax24() { return this.tax_24; }

    public void setTax24(String tax_24) { this.tax_24 = tax_24; }



    public String getTax80c() { return this.tax_80c; }

    public void setTax80c(String tax_80c) { this.tax_80c = tax_80c; }



    public  String getRentLoanDifference() { return this.rent_loan_difference; }

    public void setRentLoanDifference(String rent_loan_difference) { this.rent_loan_difference = rent_loan_difference; }
}