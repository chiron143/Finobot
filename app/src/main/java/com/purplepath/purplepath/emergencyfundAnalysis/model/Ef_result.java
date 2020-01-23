package com.purplepath.purplepath.emergencyfundAnalysis.model;

import java.io.Serializable;

/**
 * Created by dinesh on 12/10/16.
 */
public class Ef_result implements Serializable {
    private String em_fund_stat;

    private Ef_act ef_act;

    private Ef_plan ef_plan;

    public String getEm_fund_stat ()
    {
        return em_fund_stat;
    }

    public void setEm_fund_stat (String em_fund_stat)
    {
        this.em_fund_stat = em_fund_stat;
    }

    public Ef_act getEf_act ()
    {
        return ef_act;
    }

    public void setEf_act (Ef_act ef_act)
    {
        this.ef_act = ef_act;
    }

    public Ef_plan getEf_plan ()
    {
        return ef_plan;
    }

    public void setEf_plan (Ef_plan ef_plan)
    {
        this.ef_plan = ef_plan;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [em_fund_stat = "+em_fund_stat+", ef_act = "+ef_act+", ef_plan = "+ef_plan+"]";
    }
}
