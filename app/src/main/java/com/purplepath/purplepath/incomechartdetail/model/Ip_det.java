package com.purplepath.purplepath.incomechartdetail.model;

import java.io.Serializable;

/**
 * Created by dinesh on 18/07/16.
 */
public class Ip_det implements Serializable {
    private String ip_rent_inc2_per;

    private String ip_rent_inc1_per;

    private String ip_rent_inc1;

    private String ip_rent_inc2;

    public String getIp_rent_inc2_per ()
    {
        return ip_rent_inc2_per;
    }

    public void setIp_rent_inc2_per (String ip_rent_inc2_per)
    {
        this.ip_rent_inc2_per = ip_rent_inc2_per;
    }

    public String getIp_rent_inc1_per ()
    {
        return ip_rent_inc1_per;
    }

    public void setIp_rent_inc1_per (String ip_rent_inc1_per)
    {
        this.ip_rent_inc1_per = ip_rent_inc1_per;
    }

    public String getIp_rent_inc1 ()
    {
        return ip_rent_inc1;
    }

    public void setIp_rent_inc1 (String ip_rent_inc1)
    {
        this.ip_rent_inc1 = ip_rent_inc1;
    }

    public String getIp_rent_inc2 ()
    {
        return ip_rent_inc2;
    }

    public void setIp_rent_inc2 (String ip_rent_inc2)
    {
        this.ip_rent_inc2 = ip_rent_inc2;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ip_rent_inc2_per = "+ip_rent_inc2_per+", ip_rent_inc1_per = "+ip_rent_inc1_per+", ip_rent_inc1 = "+ip_rent_inc1+", ip_rent_inc2 = "+ip_rent_inc2+"]";
    }
}
