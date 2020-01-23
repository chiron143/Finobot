package com.purplepath.purplepath.recommendation.getnetworthmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 7/17/17.
 */

public class Assets implements Serializable {
    private String fix_inc_weigh_avg;

    private String hou_ass_weigh_avg;

    private String emp_ben_weigh_avg;

    private String comm_gold_weigh_avg;

    private String oth_asst_weigh_avg;

    private String equ_weigh_avg;

    private String liq_weigh_avg;

    private String real_prop_weigh_avg;

    private String over_all_weigh_avg;

    public String getFix_inc_weigh_avg ()
    {
        return fix_inc_weigh_avg;
    }

    public void setFix_inc_weigh_avg (String fix_inc_weigh_avg)
    {
        this.fix_inc_weigh_avg = fix_inc_weigh_avg;
    }

    public String getHou_ass_weigh_avg ()
    {
        return hou_ass_weigh_avg;
    }

    public void setHou_ass_weigh_avg (String hou_ass_weigh_avg)
    {
        this.hou_ass_weigh_avg = hou_ass_weigh_avg;
    }

    public String getEmp_ben_weigh_avg ()
    {
        return emp_ben_weigh_avg;
    }

    public void setEmp_ben_weigh_avg (String emp_ben_weigh_avg)
    {
        this.emp_ben_weigh_avg = emp_ben_weigh_avg;
    }

    public String getComm_gold_weigh_avg ()
    {
        return comm_gold_weigh_avg;
    }

    public void setComm_gold_weigh_avg (String comm_gold_weigh_avg)
    {
        this.comm_gold_weigh_avg = comm_gold_weigh_avg;
    }

    public String getOth_asst_weigh_avg ()
    {
        return oth_asst_weigh_avg;
    }

    public void setOth_asst_weigh_avg (String oth_asst_weigh_avg)
    {
        this.oth_asst_weigh_avg = oth_asst_weigh_avg;
    }

    public String getEqu_weigh_avg ()
    {
        return equ_weigh_avg;
    }

    public void setEqu_weigh_avg (String equ_weigh_avg)
    {
        this.equ_weigh_avg = equ_weigh_avg;
    }

    public String getLiq_weigh_avg ()
    {
        return liq_weigh_avg;
    }

    public void setLiq_weigh_avg (String liq_weigh_avg)
    {
        this.liq_weigh_avg = liq_weigh_avg;
    }

    public String getReal_prop_weigh_avg ()
    {
        return real_prop_weigh_avg;
    }

    public void setReal_prop_weigh_avg (String real_prop_weigh_avg)
    {
        this.real_prop_weigh_avg = real_prop_weigh_avg;
    }

    public String getOver_all_weigh_avg ()
    {
        return over_all_weigh_avg;
    }

    public void setOver_all_weigh_avg (String over_all_weigh_avg)
    {
        this.over_all_weigh_avg = over_all_weigh_avg;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [fix_inc_weigh_avg = "+fix_inc_weigh_avg+", hou_ass_weigh_avg = "+hou_ass_weigh_avg+", emp_ben_weigh_avg = "+emp_ben_weigh_avg+", comm_gold_weigh_avg = "+comm_gold_weigh_avg+", oth_asst_weigh_avg = "+oth_asst_weigh_avg+", equ_weigh_avg = "+equ_weigh_avg+", liq_weigh_avg = "+liq_weigh_avg+", real_prop_weigh_avg = "+real_prop_weigh_avg+", over_all_weigh_avg = "+over_all_weigh_avg+"]";
    }
}

