package com.purplepath.purplepath.liabilities.model;

import java.io.Serializable;

/**
 * Created by dinesh on 11/07/16.
 */
public class Input implements Serializable {
    private String end_year;

    private String start_year;

    private String type;

    private String cat_lev1_id;

    private String loan_amt;

    private String liab_name;

    private String insured;

    private String cat_lev3_id;

    private String outst_bal;

    private String whose_name;

    private String cat_lev2_id;

    private String user_id;

    private String balance_tenure;

    private String total_tenure;

    private String lender;

    private String current_emi;

    private String interest_rate;

    public String getEnd_year ()
    {
        return end_year;
    }

    public void setEnd_year (String end_year)
    {
        this.end_year = end_year;
    }

    public String getStart_year ()
    {
        return start_year;
    }

    public void setStart_year (String start_year)
    {
        this.start_year = start_year;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getCat_lev1_id ()
    {
        return cat_lev1_id;
    }

    public void setCat_lev1_id (String cat_lev1_id)
    {
        this.cat_lev1_id = cat_lev1_id;
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

    public String getInsured ()
    {
        return insured;
    }

    public void setInsured (String insured)
    {
        this.insured = insured;
    }

    public String getCat_lev3_id ()
    {
        return cat_lev3_id;
    }

    public void setCat_lev3_id (String cat_lev3_id)
    {
        this.cat_lev3_id = cat_lev3_id;
    }

    public String getOutst_bal ()
    {
        return outst_bal;
    }

    public void setOutst_bal (String outst_bal)
    {
        this.outst_bal = outst_bal;
    }

    public String getWhose_name ()
    {
        return whose_name;
    }

    public void setWhose_name (String whose_name)
    {
        this.whose_name = whose_name;
    }

    public String getCat_lev2_id ()
    {
        return cat_lev2_id;
    }

    public void setCat_lev2_id (String cat_lev2_id)
    {
        this.cat_lev2_id = cat_lev2_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getBalance_tenure ()
    {
        return balance_tenure;
    }

    public void setBalance_tenure (String balance_tenure)
    {
        this.balance_tenure = balance_tenure;
    }

    public String getTotal_tenure ()
    {
        return total_tenure;
    }

    public void setTotal_tenure (String total_tenure)
    {
        this.total_tenure = total_tenure;
    }

    public String getLender ()
    {
        return lender;
    }

    public void setLender (String lender)
    {
        this.lender = lender;
    }

    public String getCurrent_emi ()
    {
        return current_emi;
    }

    public void setCurrent_emi (String current_emi)
    {
        this.current_emi = current_emi;
    }

    public String getInterest_rate ()
    {
        return interest_rate;
    }

    public void setInterest_rate (String interest_rate)
    {
        this.interest_rate = interest_rate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [end_year = "+end_year+", start_year = "+start_year+", type = "+type+", cat_lev1_id = "+cat_lev1_id+", loan_amt = "+loan_amt+", liab_name = "+liab_name+", insured = "+insured+", cat_lev3_id = "+cat_lev3_id+", outst_bal = "+outst_bal+", whose_name = "+whose_name+", cat_lev2_id = "+cat_lev2_id+", user_id = "+user_id+", balance_tenure = "+balance_tenure+", total_tenure = "+total_tenure+", lender = "+lender+", current_emi = "+current_emi+", interest_rate = "+interest_rate+"]";
    }
}
