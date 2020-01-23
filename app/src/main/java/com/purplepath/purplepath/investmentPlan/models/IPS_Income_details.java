package com.purplepath.purplepath.investmentPlan.models;

import java.io.Serializable;

public class IPS_Income_details implements Serializable {

   private double si_total=0,ip_total=0,ib_total=0,cg_total=0,ifs_total=0;

    public double getSi_total() {
        return si_total;
    }

    public void setSi_total(double si_total) {
        this.si_total = si_total;
    }

    public double getIp_total() {
        return ip_total;
    }

    public void setIp_total(double ip_total) {
        this.ip_total = ip_total;
    }

    public double getIb_total() {
        return ib_total;
    }

    public void setIb_total(double ib_total) {
        this.ib_total = ib_total;
    }

    public double getCg_total() {
        return cg_total;
    }

    public void setCg_total(double cg_total) {
        this.cg_total = cg_total;
    }

    public double getIfs_total() {
        return ifs_total;
    }

    public void setIfs_total(double ifs_total) {
        this.ifs_total = ifs_total;
    }
}
