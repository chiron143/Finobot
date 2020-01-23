package com.purplepath.purplepath.cashflowmanagmentchart.model;

import java.io.Serializable;

/**
 * Created by dinesh on 01/12/16.
 */
public class Fin_cash_flow implements Serializable {
    private String years_remain;

    private String years_pass;

    private String cash_age;

    private String out_flow;

    private String cash_flow;

    private String in_flow;

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

    public String getOut_flow ()
    {
        return out_flow;
    }

    public void setOut_flow (String out_flow)
    {
        this.out_flow = out_flow;
    }

    public String getCash_flow ()
    {
        return cash_flow;
    }

    public void setCash_flow (String cash_flow)
    {
        this.cash_flow = cash_flow;
    }

    public String getIn_flow ()
    {
        return in_flow;
    }

    public void setIn_flow (String in_flow)
    {
        this.in_flow = in_flow;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [years_remain = "+years_remain+", years_pass = "+years_pass+", cash_age = "+cash_age+", out_flow = "+out_flow+", cash_flow = "+cash_flow+", in_flow = "+in_flow+"]";
    }
}
