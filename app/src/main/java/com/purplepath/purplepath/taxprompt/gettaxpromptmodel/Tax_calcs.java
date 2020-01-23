package com.purplepath.purplepath.taxprompt.gettaxpromptmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 1/22/18.
 */

public class Tax_calcs implements Serializable {
    private String exemptions;

    private ArrayList<Tax_classification> tax_classification;

    private String prepaid_tax;

    private String surage;

    private String si_total;

    private String ifs_int;

    private String gti_income;

    private String taxable_income;

    private String allowed_deduction;

    private String cess;

    private String ifs_divids;

    private String tot_income_before_gti;

    private String avg_tax_rate;

    private String rebates;

    private String ip_total;

    private String marg_tax_rate;

    private String total_tax_payable;

    private String  tax_flag;

    private String total_tax_refund;

    public String getPrepaid_tax() {
        return prepaid_tax;
    }

    public void setPrepaid_tax(String prepaid_tax) {
        this.prepaid_tax = prepaid_tax;
    }

    public String getTotal_tax_refund() {
        return total_tax_refund;
    }

    public void setTotal_tax_refund(String total_tax_refund) {
        this.total_tax_refund = total_tax_refund;
    }

    public String getTax_flag() {
        return tax_flag;
    }

    public void setTax_flag(String tax_flag) {
        this.tax_flag = tax_flag;
    }

    public String getExemptions ()
    {
        return exemptions;
    }

    public void setExemptions (String exemptions)
    {
        this.exemptions = exemptions;
    }

    public ArrayList<Tax_classification> getTax_classification() {
        return tax_classification;
    }

    public void setTax_classification(ArrayList<Tax_classification> tax_classification) {
        this.tax_classification = tax_classification;
    }

    public String getSurage ()
    {
        return surage;
    }

    public void setSurage (String surage)
    {
        this.surage = surage;
    }

    public String getSi_total ()
    {
        return si_total;
    }

    public void setSi_total (String si_total)
    {
        this.si_total = si_total;
    }

    public String getIfs_int ()
    {
        return ifs_int;
    }

    public void setIfs_int (String ifs_int)
    {
        this.ifs_int = ifs_int;
    }

    public String getGti_income ()
    {
        return gti_income;
    }

    public void setGti_income (String gti_income)
    {
        this.gti_income = gti_income;
    }

    public String getTaxable_income ()
    {
        return taxable_income;
    }

    public void setTaxable_income (String taxable_income)
    {
        this.taxable_income = taxable_income;
    }

    public String getAllowed_deduction ()
    {
        return allowed_deduction;
    }

    public void setAllowed_deduction (String allowed_deduction)
    {
        this.allowed_deduction = allowed_deduction;
    }

    public String getCess ()
    {
        return cess;
    }

    public void setCess (String cess)
    {
        this.cess = cess;
    }

    public String getIfs_divids ()
    {
        return ifs_divids;
    }

    public void setIfs_divids (String ifs_divids)
    {
        this.ifs_divids = ifs_divids;
    }

    public String getTot_income_before_gti ()
    {
        return tot_income_before_gti;
    }

    public void setTot_income_before_gti (String tot_income_before_gti)
    {
        this.tot_income_before_gti = tot_income_before_gti;
    }

    public String getAvg_tax_rate ()
    {
        return avg_tax_rate;
    }

    public void setAvg_tax_rate (String avg_tax_rate)
    {
        this.avg_tax_rate = avg_tax_rate;
    }

    public String getRebates ()
    {
        return rebates;
    }

    public void setRebates (String rebates)
    {
        this.rebates = rebates;
    }

    public String getIp_total ()
    {
        return ip_total;
    }

    public void setIp_total (String ip_total)
    {
        this.ip_total = ip_total;
    }

    public String getMarg_tax_rate ()
    {
        return marg_tax_rate;
    }

    public void setMarg_tax_rate (String marg_tax_rate)
    {
        this.marg_tax_rate = marg_tax_rate;
    }

    public String getTotal_tax_payable ()
    {
        return total_tax_payable;
    }

    public void setTotal_tax_payable (String total_tax_payable)
    {
        this.total_tax_payable = total_tax_payable;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [exemptions = "+exemptions+", tax_classification = "+tax_classification+", surage = "+surage+", si_total = "+si_total+", ifs_int = "+ifs_int+", prepaid_tax = "+prepaid_tax+", gti_income = "+gti_income+", taxable_income = "+taxable_income+", allowed_deduction = "+allowed_deduction+", cess = "+cess+", ifs_divids = "+ifs_divids+", tot_income_before_gti = "+tot_income_before_gti+", avg_tax_rate = "+avg_tax_rate+", rebates = "+rebates+", ip_total = "+ip_total+", marg_tax_rate = "+marg_tax_rate+", total_tax_payable = "+total_tax_payable+"]";
    }
}

