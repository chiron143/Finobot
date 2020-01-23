package com.purplepath.purplepath.taxfiling.gettaxconformationcheckbox;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 5/22/18.
 */

public class Data implements Serializable{
    private String message;

    //private Tax_conf_details[] tax_conf_details;

    private ArrayList<Tax_conf_details> tax_conf_details;

    public ArrayList<Tax_conf_details> getTax_conf_details() {
        return tax_conf_details;
    }

    public void setTax_conf_details(ArrayList<Tax_conf_details> tax_conf_details) {
        this.tax_conf_details = tax_conf_details;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }
//
//    public Tax_conf_details[] getTax_conf_details ()
//    {
//        return tax_conf_details;
//    }
//
//    public void setTax_conf_details (Tax_conf_details[] tax_conf_details)
//    {
//        this.tax_conf_details = tax_conf_details;
//    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", tax_conf_details = "+tax_conf_details+"]";
    }
}

