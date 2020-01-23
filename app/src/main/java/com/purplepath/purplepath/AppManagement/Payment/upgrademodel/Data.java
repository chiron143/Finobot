package com.purplepath.purplepath.AppManagement.Payment.upgrademodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 4/23/18.
 */

public class Data implements Serializable {
    private String message;


    private ArrayList<Payment_details> payment_details;

    public ArrayList<Payment_details> getPayment_details() {
        return payment_details;
    }

    public void setPayment_details(ArrayList<Payment_details> payment_details) {
        this.payment_details = payment_details;
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
        return "ClassPojo [message = "+message+", payment_details = "+payment_details+"]";
    }
}

