package com.purplepath.purplepath.taxprompt.gettaxpromptmodel;



import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 1/22/18.
 */

public class Data implements Serializable{

    private String message;

    private Tax_calcs tax_calc;

    private ArrayList<Tax_products> tax_products;

    private ArrayList<Ded_by_prods> ded_by_prod;

    private String summary_flag;

    private String summary_text;

    private String disability_flag;

    private ArrayList<Ded_by_other_prod> ded_by_other_prod;

    public String getSummary_flag() {
        return summary_flag;
    }

    public void setSummary_flag(String summary_flag) {
        this.summary_flag = summary_flag;
    }

    public String getSummary_text() {
        return summary_text;
    }

    public void setSummary_text(String summary_text) {
        this.summary_text = summary_text;
    }

    public String getDisability_flag() {
        return disability_flag;
    }

    public void setDisability_flag(String disability_flag) {
        this.disability_flag = disability_flag;
    }

    public ArrayList<Ded_by_other_prod> getDed_by_other_prod() {
        return ded_by_other_prod;
    }

    public void setDed_by_other_prod(ArrayList<Ded_by_other_prod> ded_by_other_prod) {
        this.ded_by_other_prod = ded_by_other_prod;
    }

    public ArrayList<Tax_products> getTax_products() {
        return tax_products;
    }

    public void setTax_products(ArrayList<Tax_products> tax_products) {
        this.tax_products = tax_products;
    }

    public ArrayList<Ded_by_prods> getDed_by_prod() {
        return ded_by_prod;
    }

    public void setDed_by_prod(ArrayList<Ded_by_prods> ded_by_prod) {
        this.ded_by_prod = ded_by_prod;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Tax_calcs getTax_calc ()
    {
        return tax_calc;
    }

    public void setTax_calc (Tax_calcs tax_calc)
    {
        this.tax_calc = tax_calc;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", ded_by_other_prod = "+ded_by_other_prod+", tax_calc = "+tax_calc+"," +
                " summary_flag = "+summary_flag+", summary_text = "+summary_text+", tax_products = "+tax_products+", " +
                "ded_by_prod = "+ded_by_prod+", disability_flag = "+disability_flag+"]";
    }
}

