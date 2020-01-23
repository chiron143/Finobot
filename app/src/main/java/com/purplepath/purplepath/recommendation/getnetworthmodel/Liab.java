package com.purplepath.purplepath.recommendation.getnetworthmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 7/17/17.
 */

public class Liab implements Serializable {
    private String over_loan_amt_weigh_avg;

    private String ccard_weigh_avg;

    private String rf_dep_weigh_avg;

    private String ln_weigh_avg;

    private String oth_liab_weigh_avg;

    private String unpd_bills_inc_weigh_avg;

    private String ln_off_weigh_avg;

    public String getOver_loan_amt_weigh_avg ()
    {
        return over_loan_amt_weigh_avg;
    }

    public void setOver_loan_amt_weigh_avg (String over_loan_amt_weigh_avg)
    {
        this.over_loan_amt_weigh_avg = over_loan_amt_weigh_avg;
    }

    public String getCcard_weigh_avg ()
    {
        return ccard_weigh_avg;
    }

    public void setCcard_weigh_avg (String ccard_weigh_avg)
    {
        this.ccard_weigh_avg = ccard_weigh_avg;
    }

    public String getRf_dep_weigh_avg ()
    {
        return rf_dep_weigh_avg;
    }

    public void setRf_dep_weigh_avg (String rf_dep_weigh_avg)
    {
        this.rf_dep_weigh_avg = rf_dep_weigh_avg;
    }

    public String getLn_weigh_avg ()
    {
        return ln_weigh_avg;
    }

    public void setLn_weigh_avg (String ln_weigh_avg)
    {
        this.ln_weigh_avg = ln_weigh_avg;
    }

    public String getOth_liab_weigh_avg ()
    {
        return oth_liab_weigh_avg;
    }

    public void setOth_liab_weigh_avg (String oth_liab_weigh_avg)
    {
        this.oth_liab_weigh_avg = oth_liab_weigh_avg;
    }

    public String getUnpd_bills_inc_weigh_avg ()
    {
        return unpd_bills_inc_weigh_avg;
    }

    public void setUnpd_bills_inc_weigh_avg (String unpd_bills_inc_weigh_avg)
    {
        this.unpd_bills_inc_weigh_avg = unpd_bills_inc_weigh_avg;
    }

    public String getLn_off_weigh_avg ()
    {
        return ln_off_weigh_avg;
    }

    public void setLn_off_weigh_avg (String ln_off_weigh_avg)
    {
        this.ln_off_weigh_avg = ln_off_weigh_avg;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [over_loan_amt_weigh_avg = "+over_loan_amt_weigh_avg+", ccard_weigh_avg = "+ccard_weigh_avg+", rf_dep_weigh_avg = "+rf_dep_weigh_avg+", ln_weigh_avg = "+ln_weigh_avg+", oth_liab_weigh_avg = "+oth_liab_weigh_avg+", unpd_bills_inc_weigh_avg = "+unpd_bills_inc_weigh_avg+", ln_off_weigh_avg = "+ln_off_weigh_avg+"]";
    }
}

