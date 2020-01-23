package com.purplepath.purplepath.liabilitiesanaysis.model;

import java.io.Serializable;

/**
 * @author Bert on 27-Apr-17.
 */

public class Liab_analysis implements Serializable {
    private String ln_off;

    private String ln;

    private String ln_off_per;

    private String over_loan_amt;

    private String unpd_bills;

    private String oth_liab_per;

    private String ln_per;

    private String unpd_bills_per;

    private String rf_dep_per;

    private String rf_dep;

    private String oth_liab;

    private String ccard_per;

    private String ccard;

    public String getLn_off ()
    {
        return ln_off;
    }

    public void setLn_off (String ln_off)
    {
        this.ln_off = ln_off;
    }

    public String getLn ()
    {
        return ln;
    }

    public void setLn (String ln)
    {
        this.ln = ln;
    }

    public String getLn_off_per ()
    {
        return ln_off_per;
    }

    public void setLn_off_per (String ln_off_per)
    {
        this.ln_off_per = ln_off_per;
    }

    public String getOver_loan_amt ()
    {
        return over_loan_amt;
    }

    public void setOver_loan_amt (String over_loan_amt)
    {
        this.over_loan_amt = over_loan_amt;
    }

    public String getUnpd_bills ()
    {
        return unpd_bills;
    }

    public void setUnpd_bills (String unpd_bills)
    {
        this.unpd_bills = unpd_bills;
    }

    public String getOth_liab_per ()
    {
        return oth_liab_per;
    }

    public void setOth_liab_per (String oth_liab_per)
    {
        this.oth_liab_per = oth_liab_per;
    }

    public String getLn_per ()
    {
        return ln_per;
    }

    public void setLn_per (String ln_per)
    {
        this.ln_per = ln_per;
    }

    public String getUnpd_bills_per ()
    {
        return unpd_bills_per;
    }

    public void setUnpd_bills_per (String unpd_bills_per)
    {
        this.unpd_bills_per = unpd_bills_per;
    }

    public String getRf_dep_per ()
    {
        return rf_dep_per;
    }

    public void setRf_dep_per (String rf_dep_per)
    {
        this.rf_dep_per = rf_dep_per;
    }

    public String getRf_dep ()
    {
        return rf_dep;
    }

    public void setRf_dep (String rf_dep)
    {
        this.rf_dep = rf_dep;
    }

    public String getOth_liab ()
    {
        return oth_liab;
    }

    public void setOth_liab (String oth_liab)
    {
        this.oth_liab = oth_liab;
    }

    public String getCcard_per ()
    {
        return ccard_per;
    }

    public void setCcard_per (String ccard_per)
    {
        this.ccard_per = ccard_per;
    }

    public String getCcard ()
    {
        return ccard;
    }

    public void setCcard (String ccard)
    {
        this.ccard = ccard;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ln_off = "+ln_off+", ln = "+ln+", ln_off_per = "+ln_off_per+", over_loan_amt = "+over_loan_amt+", unpd_bills = "+unpd_bills+", oth_liab_per = "+oth_liab_per+", ln_per = "+ln_per+", unpd_bills_per = "+unpd_bills_per+", rf_dep_per = "+rf_dep_per+", rf_dep = "+rf_dep+", oth_liab = "+oth_liab+", ccard_per = "+ccard_per+", ccard = "+ccard+"]";
    }
}
