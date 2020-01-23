package com.purplepath.purplepath.incomechartdetail.model;

import java.io.Serializable;

/**
 * Created by dinesh on 18/07/16.
 */
public class Fam_det implements Serializable {
    private Ib_det ib_det;

    private Ip_det ip_det;

    private String si_percent;

    private String ip_percent;

    private String si_total;

    private String ifs_total;

    private String ib_total;

    private Cg_det cg_det;

    private Si_det si_det;

    private String cg_total;

    private Ifs_det ifs_det;

    private String ip_total;

    private String ifs_percent;

    private String cg_percent;

    private String ib_percent;

    public Ib_det getIb_det ()
    {
        return ib_det;
    }

    public void setIb_det (Ib_det ib_det)
    {
        this.ib_det = ib_det;
    }

    public Ip_det getIp_det ()
    {
        return ip_det;
    }

    public void setIp_det (Ip_det ip_det)
    {
        this.ip_det = ip_det;
    }

    public String getSi_percent ()
    {
        return si_percent;
    }

    public void setSi_percent (String si_percent)
    {
        this.si_percent = si_percent;
    }

    public String getIp_percent ()
    {
        return ip_percent;
    }

    public void setIp_percent (String ip_percent)
    {
        this.ip_percent = ip_percent;
    }

    public String getSi_total ()
    {
        return si_total;
    }

    public void setSi_total (String si_total)
    {
        this.si_total = si_total;
    }

    public String getIfs_total ()
    {
        return ifs_total;
    }

    public void setIfs_total (String ifs_total)
    {
        this.ifs_total = ifs_total;
    }

    public String getIb_total ()
    {
        return ib_total;
    }

    public void setIb_total (String ib_total)
    {
        this.ib_total = ib_total;
    }

    public Cg_det getCg_det ()
    {
        return cg_det;
    }

    public void setCg_det (Cg_det cg_det)
    {
        this.cg_det = cg_det;
    }

    public Si_det getSi_det ()
    {
        return si_det;
    }

    public void setSi_det (Si_det si_det)
    {
        this.si_det = si_det;
    }

    public String getCg_total ()
    {
        return cg_total;
    }

    public void setCg_total (String cg_total)
    {
        this.cg_total = cg_total;
    }

    public Ifs_det getIfs_det ()
    {
        return ifs_det;
    }

    public void setIfs_det (Ifs_det ifs_det)
    {
        this.ifs_det = ifs_det;
    }

    public String getIp_total ()
    {
        return ip_total;
    }

    public void setIp_total (String ip_total)
    {
        this.ip_total = ip_total;
    }

    public String getIfs_percent ()
    {
        return ifs_percent;
    }

    public void setIfs_percent (String ifs_percent)
    {
        this.ifs_percent = ifs_percent;
    }

    public String getCg_percent ()
    {
        return cg_percent;
    }

    public void setCg_percent (String cg_percent)
    {
        this.cg_percent = cg_percent;
    }

    public String getIb_percent ()
    {
        return ib_percent;
    }

    public void setIb_percent (String ib_percent)
    {
        this.ib_percent = ib_percent;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ib_det = "+ib_det+", ip_det = "+ip_det+", si_percent = "+si_percent+", ip_percent = "+ip_percent+", si_total = "+si_total+", ifs_total = "+ifs_total+", ib_total = "+ib_total+", cg_det = "+cg_det+", si_det = "+si_det+", cg_total = "+cg_total+", ifs_det = "+ifs_det+", ip_total = "+ip_total+", ifs_percent = "+ifs_percent+", cg_percent = "+cg_percent+", ib_percent = "+ib_percent+"]";
    }
}
