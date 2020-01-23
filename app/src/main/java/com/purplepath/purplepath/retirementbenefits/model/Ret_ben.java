package com.purplepath.purplepath.retirementbenefits.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 24/08/16.
 */
public class Ret_ben implements Serializable {
    private String leave_encash;

    private String id;

    private String others;

    private String annuity_pension;

    private String gratuity;

    private String created_datetime;

    private String retren_comp;

    private String saf;

    private String user_id;

    private String vr_comp;

    private String modified_datetime;

    private String notes;

    public ArrayList<String> getEmpty_flds() {
        return empty_flds;
    }

    public void setEmpty_flds(ArrayList<String> empty_flds) {
        this.empty_flds = empty_flds;
    }

    private ArrayList<String> empty_flds;

    public String getLeave_encash ()
    {
        return leave_encash;
    }

    public void setLeave_encash (String leave_encash)
    {
        this.leave_encash = leave_encash;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
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

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
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

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
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
        return "ClassPojo [leave_encash = "+leave_encash+", id = "+id+", others = "+others+", annuity_pension = "+annuity_pension+", gratuity = "+gratuity+", created_datetime = "+created_datetime+", retren_comp = "+retren_comp+", saf = "+saf+", user_id = "+user_id+", vr_comp = "+vr_comp+", modified_datetime = "+modified_datetime+", notes = "+notes+"]";
    }
}