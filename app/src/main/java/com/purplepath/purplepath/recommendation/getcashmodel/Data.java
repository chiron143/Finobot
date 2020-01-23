package com.purplepath.purplepath.recommendation.getcashmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 9/4/17.
 */

public class Data implements Serializable {
    private String message;

    private Cash_mang_det cash_mang_det;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Cash_mang_det getCash_mang_det ()
    {
        return cash_mang_det;
    }

    public void setCash_mang_det (Cash_mang_det cash_mang_det)
    {
        this.cash_mang_det = cash_mang_det;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", cash_mang_det = "+cash_mang_det+"]";
    }
}

