package com.purplepath.purplepath.AppManagement.Payment.promocodemodel;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable {
    private String message;

    private ArrayList<Promo_details> promo_details;

    public ArrayList<Promo_details> getPromo_details() {
        return promo_details;
    }

    public void setPromo_details(ArrayList<Promo_details> promo_details) {
        this.promo_details = promo_details;
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
        return "ClassPojo [message = "+message+", promo_details = "+promo_details+"]";
    }
}

