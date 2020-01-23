package com.purplepath.purplepath.AppManagement.Payment.getcommonpaymentmodel;


import java.io.Serializable;

/**
 * Created by pravinr on 4/20/18.
 */

public class Data implements Serializable {

    private String message;

    private User_payment_details user_payment_details;

    private String payment_made;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public User_payment_details getUser_payment_details ()
    {
        return user_payment_details;
    }

    public void setUser_payment_details (User_payment_details user_payment_details)
    {
        this.user_payment_details = user_payment_details;
    }

    public String getPayment_made ()
    {
        return payment_made;
    }

    public void setPayment_made (String payment_made)
    {
        this.payment_made = payment_made;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_payment_details = "+user_payment_details+", payment_made = "+payment_made+"]";
    }
}

