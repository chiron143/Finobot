package com.purplepath.purplepath.cashflowmanagmentchart.model;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 26-05-2017.
 */

public class Cash_mang_det implements Serializable {

    private String years_remain;

    private String years_pass;

    private String cash_age;

    private String deflict;

    private String over_all_inc;

    private String si_total;

    private String ib_total;

    private String ifs_total;

    private String over_all_exp;

    private String tot_exp;

    private String over_all_contr;

    private String cg_total;

    private String over_all_commt;

    private String over_all_obli;

    private String ip_total;

    public String getYears_remain ()
    {
        return years_remain;
    }

    public void setYears_remain (String years_remain)
    {
        this.years_remain = years_remain;
    }

    public String getYears_pass ()
    {
        return years_pass;
    }

    public void setYears_pass (String years_pass)
    {
        this.years_pass = years_pass;
    }

    public String getCash_age ()
    {
        return cash_age;
    }

    public void setCash_age (String cash_age)
    {
        this.cash_age = cash_age;
    }

    public String getDeflict ()
    {
        return deflict;
    }

    public void setDeflict (String deflict)
    {
        this.deflict = deflict;
    }

    public String getOver_all_inc ()
    {
        return over_all_inc;
    }

    public void setOver_all_inc (String over_all_inc)
    {
        this.over_all_inc = over_all_inc;
    }

    public String getSi_total ()
    {
        return si_total;
    }

    public void setSi_total (String si_total)
    {
        this.si_total = si_total;
    }

    public String getIb_total ()
    {
        return ib_total;
    }

    public void setIb_total (String ib_total)
    {
        this.ib_total = ib_total;
    }

    public String getIfs_total ()
    {
        return ifs_total;
    }

    public void setIfs_total (String ifs_total)
    {
        this.ifs_total = ifs_total;
    }

    public String getOver_all_exp ()
    {
        return over_all_exp;
    }

    public void setOver_all_exp (String over_all_exp)
    {
        this.over_all_exp = over_all_exp;
    }

    public String getTot_exp ()
    {
        return tot_exp;
    }

    public void setTot_exp (String tot_exp)
    {
        this.tot_exp = tot_exp;
    }

    public String getOver_all_contr ()
    {
        return over_all_contr;
    }

    public void setOver_all_contr (String over_all_contr)
    {
        this.over_all_contr = over_all_contr;
    }

    public String getCg_total ()
    {
        return cg_total;
    }

    public void setCg_total (String cg_total)
    {
        this.cg_total = cg_total;
    }

    public String getOver_all_commt ()
    {
        return over_all_commt;
    }

    public void setOver_all_commt (String over_all_commt)
    {
        this.over_all_commt = over_all_commt;
    }

    public String getOver_all_obli ()
    {
        return over_all_obli;
    }

    public void setOver_all_obli (String over_all_obli)
    {
        this.over_all_obli = over_all_obli;
    }

    public String getIp_total ()
    {
        return ip_total;
    }

    public void setIp_total (String ip_total)
    {
        this.ip_total = ip_total;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [years_remain = "+years_remain+", years_pass = "+years_pass+", cash_age = "+cash_age+", deflict = "+deflict+", over_all_inc = "+over_all_inc+", si_total = "+si_total+", ib_total = "+ib_total+", ifs_total = "+ifs_total+", over_all_exp = "+over_all_exp+", tot_exp = "+tot_exp+", over_all_contr = "+over_all_contr+", cg_total = "+cg_total+", over_all_commt = "+over_all_commt+", over_all_obli = "+over_all_obli+", ip_total = "+ip_total+"]";
    }
}
