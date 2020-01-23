package com.purplepath.purplepath.schedule.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 06-07-2017.
 */

public class Liab implements Serializable {

    private String id;

    private String loan_amt;

    private String liab_name;

    private String flag;

    private String outst_bal;

    private String user_id;

    private String next_due_date;

    private String type;

    private String current_emi;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLoan_amt ()
    {
        return loan_amt;
    }

    public void setLoan_amt (String loan_amt)
    {
        this.loan_amt = loan_amt;
    }

    public String getLiab_name ()
    {
        return liab_name;
    }

    public void setLiab_name (String liab_name)
    {
        this.liab_name = liab_name;
    }

    public String getFlag ()
    {
        return flag;
    }

    public void setFlag (String flag)
    {
        this.flag = flag;
    }

    public String getOutst_bal ()
    {
        return outst_bal;
    }

    public void setOutst_bal (String outst_bal)
    {
        this.outst_bal = outst_bal;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getNext_due_date ()
    {
        return next_due_date;
    }

    public void setNext_due_date (String next_due_date)
    {
        this.next_due_date = next_due_date;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getCurrent_emi ()
    {
        return current_emi;
    }

    public void setCurrent_emi (String current_emi)
    {
        this.current_emi = current_emi;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", loan_amt = "+loan_amt+", liab_name = "+liab_name+", flag = "+flag+", outst_bal = "+outst_bal+", user_id = "+user_id+", next_due_date = "+next_due_date+", type = "+type+", current_emi = "+current_emi+"]";
    }
}
