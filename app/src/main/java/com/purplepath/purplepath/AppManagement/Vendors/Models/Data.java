package com.purplepath.purplepath.AppManagement.Vendors.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 18-05-2017.
 */

public class Data implements Serializable {

    private String message;

    private ArrayList<Vendors> Vendors;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public  ArrayList<Vendors> getVendors ()
    {
        return  Vendors;
    }

    public void setVendors ( ArrayList<Vendors> Vendors)
    {
        this.Vendors = Vendors;
    }


}
