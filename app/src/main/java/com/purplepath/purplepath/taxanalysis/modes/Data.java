package com.purplepath.purplepath.taxanalysis.modes;

import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.User_tax;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Suresh on 07/01/17.
 */

public class Data implements Serializable {

    private String message;

//    private Tax_plan[] tax_plan;

    private ArrayList<Tax_plan> tax_plan;

    public ArrayList<Tax_plan> getTax_plan() {
        return tax_plan;
    }

    public void setTax_plan(ArrayList<Tax_plan> tax_plan) {
        this.tax_plan = tax_plan;
    }

    public String getMessage ()
    {
        return message;

    }

    public void setMessage (String message)
    {
        this.message = message;
    }

//    public User_tax[] getUser_tax ()
//    {
//        return user_tax;
//    }
//
//    public void setUser_tax (User_tax[] user_tax)
//    {
//        this.user_tax = user_tax;
//    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", tax_plan = "+tax_plan+"]";
    }
}
