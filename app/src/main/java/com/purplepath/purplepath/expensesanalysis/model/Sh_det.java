package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class Sh_det implements Serializable
{
    private String sh_hm_maint;

    private String sh_rent_per;

    private String sh_hm_maint_per;

    private String sh_rent;

    public String getSh_hm_maint ()
{
    return sh_hm_maint;
}

    public void setSh_hm_maint (String sh_hm_maint)
    {
        this.sh_hm_maint = sh_hm_maint;
    }

    public String getSh_rent_per ()
    {
        return sh_rent_per;
    }

    public void setSh_rent_per (String sh_rent_per)
    {
        this.sh_rent_per = sh_rent_per;
    }

    public String getSh_hm_maint_per ()
    {
        return sh_hm_maint_per;
    }

    public void setSh_hm_maint_per (String sh_hm_maint_per)
    {
        this.sh_hm_maint_per = sh_hm_maint_per;
    }

    public String getSh_rent ()
{
    return sh_rent;
}

    public void setSh_rent (String sh_rent)
    {
        this.sh_rent = sh_rent;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sh_hm_maint = "+sh_hm_maint+", sh_rent_per = "+sh_rent_per+", sh_hm_maint_per = "+sh_hm_maint_per+", sh_rent = "+sh_rent+"]";
    }
}