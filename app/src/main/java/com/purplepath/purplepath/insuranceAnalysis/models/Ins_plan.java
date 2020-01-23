package com.purplepath.purplepath.insuranceAnalysis.models;

import java.io.Serializable;

/**
 * Created by Suresh on 04/01/17.
 */

public class Ins_plan implements Serializable{
    private String recom_cov_req;

    private String curr_obli;

    private String tot_asset_liq;

    private String curr_exp;

    private String curr_contr;

    private String req_cov_req;

    private String recom_cov_need;

    private String req_cov_need;

    private String tot_asset_long;

    private String tot_asset;

    private String hlv;

    private String fut_req;

    private String min_sur_cov_req;

    private String tot_liab;

    private String min_sur_cov_need;

    private String curr_commit;

    private String curr_ins;

    public String getRecom_cov_req ()
    {
        return recom_cov_req;
    }

    public void setRecom_cov_req (String recom_cov_req)
    {
        this.recom_cov_req = recom_cov_req;
    }

    public String getCurr_obli ()
    {
        return curr_obli;
    }

    public void setCurr_obli (String curr_obli)
    {
        this.curr_obli = curr_obli;
    }

    public String getTot_asset_liq ()
    {
        return tot_asset_liq;
    }

    public void setTot_asset_liq (String tot_asset_liq)
    {
        this.tot_asset_liq = tot_asset_liq;
    }

    public String getCurr_exp ()
    {
        return curr_exp;
    }

    public void setCurr_exp (String curr_exp)
    {
        this.curr_exp = curr_exp;
    }

    public String getCurr_contr ()
    {
        return curr_contr;
    }

    public void setCurr_contr (String curr_contr)
    {
        this.curr_contr = curr_contr;
    }

    public String getReq_cov_req ()
    {
        return req_cov_req;
    }

    public void setReq_cov_req (String req_cov_req)
    {
        this.req_cov_req = req_cov_req;
    }

    public String getRecom_cov_need ()
    {
        return recom_cov_need;
    }

    public void setRecom_cov_need (String recom_cov_need)
    {
        this.recom_cov_need = recom_cov_need;
    }

    public String getReq_cov_need ()
    {
        return req_cov_need;
    }

    public void setReq_cov_need (String req_cov_need)
    {
        this.req_cov_need = req_cov_need;
    }

    public String getTot_asset_long ()
    {
        return tot_asset_long;
    }

    public void setTot_asset_long (String tot_asset_long)
    {
        this.tot_asset_long = tot_asset_long;
    }

    public String getTot_asset ()
    {
        return tot_asset;
    }

    public void setTot_asset (String tot_asset)
    {
        this.tot_asset = tot_asset;
    }

    public String getHlv ()
    {
        return hlv;
    }

    public void setHlv (String hlv)
    {
        this.hlv = hlv;
    }

    public String getFut_req ()
    {
        return fut_req;
    }

    public void setFut_req (String fut_req)
    {
        this.fut_req = fut_req;
    }

    public String getMin_sur_cov_req ()
    {
        return min_sur_cov_req;
    }

    public void setMin_sur_cov_req (String min_sur_cov_req)
    {
        this.min_sur_cov_req = min_sur_cov_req;
    }

    public String getTot_liab ()
    {
        return tot_liab;
    }

    public void setTot_liab (String tot_liab)
    {
        this.tot_liab = tot_liab;
    }

    public String getMin_sur_cov_need ()
    {
        return min_sur_cov_need;
    }

    public void setMin_sur_cov_need (String min_sur_cov_need)
    {
        this.min_sur_cov_need = min_sur_cov_need;
    }

    public String getCurr_commit ()
    {
        return curr_commit;
    }

    public void setCurr_commit (String curr_commit)
    {
        this.curr_commit = curr_commit;
    }

    public String getCurr_ins ()
    {
        return curr_ins;
    }

    public void setCurr_ins (String curr_ins)
    {
        this.curr_ins = curr_ins;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [recom_cov_req = "+recom_cov_req+", curr_obli = "+curr_obli+", tot_asset_liq = "+tot_asset_liq+", curr_exp = "+curr_exp+", curr_contr = "+curr_contr+", req_cov_req = "+req_cov_req+", recom_cov_need = "+recom_cov_need+", req_cov_need = "+req_cov_need+", tot_asset_long = "+tot_asset_long+", tot_asset = "+tot_asset+", hlv = "+hlv+", fut_req = "+fut_req+", min_sur_cov_req = "+min_sur_cov_req+", tot_liab = "+tot_liab+", min_sur_cov_need = "+min_sur_cov_need+", curr_commit = "+curr_commit+", curr_ins = "+curr_ins+"]";
    }
}