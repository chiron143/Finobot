package com.purplepath.purplepath.recommendation.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Ins_plan implements Serializable {
    private String hlv;

    private String min_cov_need;

    private String min_tot_ins;

    private String status;

    private String max_tot_ins;

   // private Plan_res[] plan_res;
   public ArrayList<Plan_res> plan_res;

    public ArrayList<Plan_res> getPlan_res() {
        return plan_res;
    }

    public void setPlan_res(ArrayList<Plan_res> plan_res) {
        this.plan_res = plan_res;
    }

    private String min_cur_ins_cov;

    private String max_cov_need;

    private String max_cur_ins_cov;

    public String getHlv ()
    {
        return hlv;
    }

    public void setHlv (String hlv)
    {
        this.hlv = hlv;
    }

    public String getMin_cov_need ()
    {
        return min_cov_need;
    }

    public void setMin_cov_need (String min_cov_need)
    {
        this.min_cov_need = min_cov_need;
    }

    public String getMin_tot_ins ()
    {
        return min_tot_ins;
    }

    public void setMin_tot_ins (String min_tot_ins)
    {
        this.min_tot_ins = min_tot_ins;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    public String getMax_tot_ins ()
    {
        return max_tot_ins;
    }

    public void setMax_tot_ins (String max_tot_ins)
    {
        this.max_tot_ins = max_tot_ins;
    }

    /*public Plan_res[] getPlan_res ()
    {
        return plan_res;
    }

    public void setPlan_res (Plan_res[] plan_res)
    {
        this.plan_res = plan_res;
    }*/

    public String getMin_cur_ins_cov ()
    {
        return min_cur_ins_cov;
    }

    public void setMin_cur_ins_cov (String min_cur_ins_cov)
    {
        this.min_cur_ins_cov = min_cur_ins_cov;
    }

    public String getMax_cov_need ()
    {
        return max_cov_need;
    }

    public void setMax_cov_need (String max_cov_need)
    {
        this.max_cov_need = max_cov_need;
    }

    public String getMax_cur_ins_cov ()
    {
        return max_cur_ins_cov;
    }

    public void setMax_cur_ins_cov (String max_cur_ins_cov)
    {
        this.max_cur_ins_cov = max_cur_ins_cov;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [hlv = "+hlv+", min_cov_need = "+min_cov_need+", min_tot_ins = "+min_tot_ins+", status = "+status+", max_tot_ins = "+max_tot_ins+", plan_res = "+plan_res+", min_cur_ins_cov = "+min_cur_ins_cov+", max_cov_need = "+max_cov_need+", max_cur_ins_cov = "+max_cur_ins_cov+"]";
    }
}

