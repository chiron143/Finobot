package com.purplepath.purplepath.desiproAllModules.homeLoanSwitch.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 03-04-2017.
 */

public class Data implements Serializable{
    private String message;

    private String bal_ten;

    private String int_rate;

    private String emi;

    private String outst_bal;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getBal_ten ()
    {
        return bal_ten;
    }

    public void setBal_ten (String bal_ten)
    {
        this.bal_ten = bal_ten;
    }

    public String getInt_rate ()
    {
        return int_rate;
    }

    public void setInt_rate (String int_rate)
    {
        this.int_rate = int_rate;
    }

    public String getEmi ()
    {
        return emi;
    }

    public void setEmi (String emi)
    {
        this.emi = emi;
    }

    public String getOutst_bal ()
    {
        return outst_bal;
    }

    public void setOutst_bal (String outst_bal)
    {
        this.outst_bal = outst_bal;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", bal_ten = "+bal_ten+", int_rate = "+int_rate+", emi = "+emi+", outst_bal = "+outst_bal+"]";
    }
}
