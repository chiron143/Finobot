package com.purplepath.purplepath.taxfiling.getdeclarationmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 3/22/18.
 */

public class Data implements Serializable{

    private String message;

    private ArrayList<Dec_details> dec_details;

    public ArrayList<Dec_details> getDec_details() {
        return dec_details;
    }

    public void setDec_details(ArrayList<Dec_details> dec_details) {
        this.dec_details = dec_details;
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
        return "ClassPojo [message = "+message+", dec_details = "+dec_details+"]";
    }
}

