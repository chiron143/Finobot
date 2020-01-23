package com.purplepath.purplepath.desiproAllModules.depositcomparison.model;

import java.io.Serializable;

/**
 * Created by dinesh on 28/06/17.
 */

public class Dep1 implements Serializable {
    private String tot_inst_ret;

    private String stat_int_rate;

    private String nom_int_rate;

    private String tot_ret;

    private String tot_inst;

    public String getTot_inst_ret ()
    {
        return tot_inst_ret;
    }

    public void setTot_inst_ret (String tot_inst_ret)
    {
        this.tot_inst_ret = tot_inst_ret;
    }

    public String getStat_int_rate ()
    {
        return stat_int_rate;
    }

    public void setStat_int_rate (String stat_int_rate)
    {
        this.stat_int_rate = stat_int_rate;
    }

    public String getNom_int_rate ()
    {
        return nom_int_rate;
    }

    public void setNom_int_rate (String nom_int_rate)
    {
        this.nom_int_rate = nom_int_rate;
    }

    public String getTot_ret ()
    {
        return tot_ret;
    }

    public void setTot_ret (String tot_ret)
    {
        this.tot_ret = tot_ret;
    }

    public String getTot_inst ()
    {
        return tot_inst;
    }

    public void setTot_inst (String tot_inst)
    {
        this.tot_inst = tot_inst;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tot_inst_ret = "+tot_inst_ret+", stat_int_rate = "+stat_int_rate+", nom_int_rate = "+nom_int_rate+", tot_ret = "+tot_ret+", tot_inst = "+tot_inst+"]";
    }
}
