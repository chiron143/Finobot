package com.purplepath.purplepath.taxprepaid.gettaxprepaidmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 1/10/18.
 */

public class Data implements Serializable {
    private String message;

    private ArrayList<Pre_tax> pre_tax;

    public ArrayList<Pre_tax> getPre_tax() {
        return pre_tax;
    }

    public void setPre_tax(ArrayList<Pre_tax> pre_tax) {
        this.pre_tax = pre_tax;
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
        return "ClassPojo [message = "+message+", pre_tax = "+pre_tax+"]";
    }
}

