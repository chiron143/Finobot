package com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bertrandrussellsakthees on 07/01/17.
 */

public class Data implements Serializable {

    private String message;

    private ArrayList<User_tax> user_tax;

    private Tax_calc tax_calc;

    public Tax_calc getTax_calc() {
        return tax_calc;
    }

    public void setTax_calc(Tax_calc tax_calc) {
        this.tax_calc = tax_calc;
    }

    private ArrayList<Ded_by_prod> ded_by_prod;

    public ArrayList<Ded_by_prod> getDed_by_prod() {
        return ded_by_prod;
    }

    public void setDed_by_prod(ArrayList<Ded_by_prod> ded_by_prod) {
        this.ded_by_prod = ded_by_prod;
    }

    public String getMessage ()
    {
        return message;
    }

    public ArrayList<User_tax> getUser_tax() {
        return user_tax;
    }

    public void setUser_tax(ArrayList<User_tax> user_tax) {
        this.user_tax = user_tax;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_tax = "+user_tax+"]";
    }
}
