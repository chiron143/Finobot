package com.purplepath.purplepath.cashflowmanagmentchart.model;



import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 26-05-2017.
 */

public class IncomeExpenseData implements Serializable {
    private String message;

    private ArrayList<Cash_mang_det> cash_mang_det;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Cash_mang_det> getCash_mang_det ()
    {
        return cash_mang_det;
    }

    public void setCash_mang_det (ArrayList<Cash_mang_det> cash_mang_det)
    {
        this.cash_mang_det = cash_mang_det;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", cash_mang_det = "+cash_mang_det+"]";
    }
}
