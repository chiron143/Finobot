package com.purplepath.purplepath.taxfiling.getsuccessparsemodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 6/11/18.
 */

public class Data implements Serializable {

    private String message;


    private ArrayList<Tax_file_state> tax_file_state;

    public ArrayList<Tax_file_state> getTax_file_state() {
        return tax_file_state;
    }

    public void setTax_file_state(ArrayList<Tax_file_state> tax_file_state) {
        this.tax_file_state = tax_file_state;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", tax_file_state = "+tax_file_state+"]";
    }
}

