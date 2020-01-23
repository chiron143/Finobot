package com.purplepath.purplepath.retirementbenefits.model;

import java.io.Serializable;

/**
 * Created by dinesh on 14/07/16.
 */
public class Input implements Serializable{
    private String leave_encash;

    private String others;

    private String annuity_pension;

    private String gratuity;

    private String retren_comp;

    private String saf;

    private String user_id;

    private String vr_comp;

    private String notes;

    public String getLeave_encash ()
    {
        return leave_encash;
    }

    public void setLeave_encash (String leave_encash)
    {
        this.leave_encash = leave_encash;
    }

    public String getOthers ()
    {
        return others;
    }

    public void setOthers (String others)
    {
        this.others = others;
    }

    public String getAnnuity_pension ()
    {
        return annuity_pension;
    }

    public void setAnnuity_pension (String annuity_pension)
    {
        this.annuity_pension = annuity_pension;
    }

    public String getGratuity ()
    {
        return gratuity;
    }

    public void setGratuity (String gratuity)
    {
        this.gratuity = gratuity;
    }

    public String getRetren_comp ()
    {
        return retren_comp;
    }

    public void setRetren_comp (String retren_comp)
    {
        this.retren_comp = retren_comp;
    }

    public String getSaf ()
    {
        return saf;
    }

    public void setSaf (String saf)
    {
        this.saf = saf;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getVr_comp ()
    {
        return vr_comp;
    }

    public void setVr_comp (String vr_comp)
    {
        this.vr_comp = vr_comp;
    }

    public String getNotes ()
    {
        return notes;
    }

    public void setNotes (String notes)
    {
        this.notes = notes;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [leave_encash = "+leave_encash+", others = "+others+", annuity_pension = "+annuity_pension+", gratuity = "+gratuity+", retren_comp = "+retren_comp+", saf = "+saf+", user_id = "+user_id+", vr_comp = "+vr_comp+", notes = "+notes+"]";
    }
}
