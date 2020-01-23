package com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bertrandrussellsakthees on 07/01/17.
 */

public class User_tax implements Serializable {


    private String total_savings;

    private String years_remain;

    private String actual_tax;

//    private Sections sections;

    private ArrayList<Sections> sections;

    private String years_pass;

    private String surage;

    private String cess;

    private String tax_age;

    private String total_tax_payable;

    public ArrayList<Sections> getSections() {
        return sections;
    }

    public void setSections(ArrayList<Sections> sections) {
        this.sections = sections;
    }

    public String getTotal_savings ()
    {
        return total_savings;
    }

    public void setTotal_savings (String total_savings)
    {
        this.total_savings = total_savings;
    }

    public String getYears_remain ()
    {
        return years_remain;
    }

    public void setYears_remain (String years_remain)
    {
        this.years_remain = years_remain;
    }

    public String getActual_tax ()
    {
        return actual_tax;
    }

    public void setActual_tax (String actual_tax)
    {
        this.actual_tax = actual_tax;
    }

//    public Sections getSections ()
//    {
//        return sections;
//    }
//
//    public void setSections (Sections sections)
//    {
//        this.sections = sections;
//    }

    public String getYears_pass ()
    {
        return years_pass;
    }

    public void setYears_pass (String years_pass)
    {
        this.years_pass = years_pass;
    }

    public String getSurage ()
    {
        return surage;
    }

    public void setSurage (String surage)
    {
        this.surage = surage;
    }

    public String getCess ()
    {
        return cess;
    }

    public void setCess (String cess)
    {
        this.cess = cess;
    }

    public String getTax_age ()
    {
        return tax_age;
    }

    public void setTax_age (String tax_age)
    {
        this.tax_age = tax_age;
    }

    public String getTotal_tax_payable ()
    {
        return total_tax_payable;
    }

    public void setTotal_tax_payable (String total_tax_payable)
    {
        this.total_tax_payable = total_tax_payable;
    }

//    @Override
//    public String toString()
//    {
////        return "ClassPojo [total_savings = "+total_savings+", years_remain = "+years_remain+", actual_tax = "+actual_tax+", sections = "+sections+", years_pass = "+years_pass+", surage = "+surage+", cess = "+cess+", tax_age = "+tax_age+", total_tax_payable = "+total_tax_payable+"]";
//    }
}
