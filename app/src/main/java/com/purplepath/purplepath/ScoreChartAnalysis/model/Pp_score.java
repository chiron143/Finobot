package com.purplepath.purplepath.ScoreChartAnalysis.model;

import java.io.Serializable;

/**
 * Created by dinesh on 30/09/16.
 */
public class Pp_score implements Serializable {
    private String asst_score;

    private String liq_score;

    private String ins_score;

    private String pp_score;

    private String ret_score;

    private String liab_score;

    private String tax_score;

    private String goal_score;

    private String invest_score;

    private String exp_score;

    private String inc_score;

    public String getAsst_score ()
    {
        return asst_score;
    }

    public void setAsst_score (String asst_score)
    {
        this.asst_score = asst_score;
    }

    public String getLiq_score ()
    {
        return liq_score;
    }

    public void setLiq_score (String liq_score)
    {
        this.liq_score = liq_score;
    }

    public String getIns_score ()
    {
        return ins_score;
    }

    public void setIns_score (String ins_score)
    {
        this.ins_score = ins_score;
    }

    public String getPp_score ()
    {
        return pp_score;
    }

    public void setPp_score (String pp_score)
    {
        this.pp_score = pp_score;
    }

    public String getRet_score ()
    {
        return ret_score;
    }

    public void setRet_score (String ret_score)
    {
        this.ret_score = ret_score;
    }

    public String getLiab_score ()
    {
        return liab_score;
    }

    public void setLiab_score (String liab_score)
    {
        this.liab_score = liab_score;
    }

    public String getTax_score ()
    {
        return tax_score;
    }

    public void setTax_score (String tax_score)
    {
        this.tax_score = tax_score;
    }

    public String getGoal_score ()
    {
        return goal_score;
    }

    public void setGoal_score (String goal_score)
    {
        this.goal_score = goal_score;
    }

    public String getInvest_score ()
    {
        return invest_score;
    }

    public void setInvest_score (String invest_score)
    {
        this.invest_score = invest_score;
    }

    public String getExp_score ()
    {
        return exp_score;
    }

    public void setExp_score (String exp_score)
    {
        this.exp_score = exp_score;
    }

    public String getInc_score ()
    {
        return inc_score;
    }

    public void setInc_score (String inc_score)
    {
        this.inc_score = inc_score;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [asst_score = "+asst_score+", liq_score = "+liq_score+", ins_score = "+ins_score+", pp_score = "+pp_score+", ret_score = "+ret_score+", liab_score = "+liab_score+", tax_score = "+tax_score+", goal_score = "+goal_score+", invest_score = "+invest_score+", exp_score = "+exp_score+", inc_score = "+inc_score+"]";
    }
}
