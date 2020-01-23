package com.purplepath.purplepath.networthanalysis.model;

import java.io.Serializable;

/**
 * Created by dinesh on 29/09/16.
 */
public class Liab_det implements Serializable{

    private Ln ln;

    private Ln_off ln_off;

    private Unpd_bills unpd_bills;

    private String over_loan_amt;

    private Oth_liab oth_liab;

    private Rf_dep rf_dep;

    private Ccard ccard;

    public Ln getLn ()
    {
        return ln;
    }

    public void setLn (Ln ln)
    {
        this.ln = ln;
    }

    public Ln_off getLn_off ()
    {
        return ln_off;
    }

    public void setLn_off (Ln_off ln_off)
    {
        this.ln_off = ln_off;
    }

    public Unpd_bills getUnpd_bills ()
    {
        return unpd_bills;
    }

    public void setUnpd_bills (Unpd_bills unpd_bills)
    {
        this.unpd_bills = unpd_bills;
    }

    public String getOver_loan_amt ()
    {
        return over_loan_amt;
    }

    public void setOver_loan_amt (String over_loan_amt)
    {
        this.over_loan_amt = over_loan_amt;
    }

    public Oth_liab getOth_liab ()
    {
        return oth_liab;
    }

    public void setOth_liab (Oth_liab oth_liab)
    {
        this.oth_liab = oth_liab;
    }

    public Rf_dep getRf_dep ()
    {
        return rf_dep;
    }

    public void setRf_dep (Rf_dep rf_dep)
    {
        this.rf_dep = rf_dep;
    }

    public Ccard getCcard ()
    {
        return ccard;
    }

    public void setCcard (Ccard ccard)
    {
        this.ccard = ccard;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ln = "+ln+", ln_off = "+ln_off+", unpd_bills = "+unpd_bills+", over_loan_amt = "+over_loan_amt+", oth_liab = "+oth_liab+", rf_dep = "+rf_dep+", ccard = "+ccard+"]";
    }
}
