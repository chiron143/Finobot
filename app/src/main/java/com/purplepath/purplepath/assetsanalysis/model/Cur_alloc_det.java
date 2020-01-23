package com.purplepath.purplepath.assetsanalysis.model;

import java.io.Serializable;

/**
 * Created by vishnu on 28-09-2016.
 */
public class Cur_alloc_det implements Serializable{
    private String emp_ben;

    private String liq;

    private String fix_inc;

    private String real_prop;

    private String equ;

    private String comm_gold;

    private String hou_asst;

    private String oth_asst;

    public String getEmp_ben ()
    {
        return emp_ben;
    }

    public void setEmp_ben (String emp_ben)
    {
        this.emp_ben = emp_ben;
    }

    public String getLiq ()
    {
        return liq;
    }

    public void setLiq (String liq)
    {
        this.liq = liq;
    }

    public String getFix_inc ()
    {
        return fix_inc;
    }

    public void setFix_inc (String fix_inc)
    {
        this.fix_inc = fix_inc;
    }

    public String getReal_prop ()
    {
        return real_prop;
    }

    public void setReal_prop (String real_prop)
    {
        this.real_prop = real_prop;
    }

    public String getEqu ()
    {
        return equ;
    }

    public void setEqu (String equ)
    {
        this.equ = equ;
    }

    public String getComm_gold ()
    {
        return comm_gold;
    }

    public void setComm_gold (String comm_gold)
    {
        this.comm_gold = comm_gold;
    }

    public String getHou_asst ()
    {
        return hou_asst;
    }

    public void setHou_asst (String hou_asst)
    {
        this.hou_asst = hou_asst;
    }

    public String getOth_asst ()
    {
        return oth_asst;
    }

    public void setOth_asst (String oth_asst)
    {
        this.oth_asst = oth_asst;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [emp_ben = "+emp_ben+", liq = "+liq+", fix_inc = "+fix_inc+", real_prop = "+real_prop+", equ = "+equ+", comm_gold = "+comm_gold+", hou_asst = "+hou_asst+", oth_asst = "+oth_asst+"]";
    }
}