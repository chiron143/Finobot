package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class Trans_det implements Serializable
{
    private String trans_park_misce_per;

    private String trans_park_misce;

    private String trans_trav_per;

    private String trans_trav;

    private String trans_veh_maint;

    private String trans_commute;

    private String trans_pet_diesel;

    private String trans_pet_diesel_per;

    private String trans_commute_per;

    public String getTrans_park_misce_per ()
    {
        return trans_park_misce_per;
    }

    public void setTrans_park_misce_per (String trans_park_misce_per)
    {
        this.trans_park_misce_per = trans_park_misce_per;
    }

    public String getTrans_park_misce ()
{
    return trans_park_misce;
}

    public void setTrans_park_misce (String trans_park_misce)
    {
        this.trans_park_misce = trans_park_misce;
    }

    public String getTrans_trav_per ()
    {
        return trans_trav_per;
    }

    public void setTrans_trav_per (String trans_trav_per)
    {
        this.trans_trav_per = trans_trav_per;
    }

    public String getTrans_trav ()
{
    return trans_trav;
}

    public void setTrans_trav (String trans_trav)
    {
        this.trans_trav = trans_trav;
    }

    public String getTrans_veh_maint ()
    {
        return trans_veh_maint;
    }

    public void setTrans_veh_maint (String trans_veh_maint)
    {
        this.trans_veh_maint = trans_veh_maint;
    }

    public String getTrans_commute ()
{
    return trans_commute;
}

    public void setTrans_commute (String trans_commute)
    {
        this.trans_commute = trans_commute;
    }

    public String getTrans_pet_diesel ()
{
    return trans_pet_diesel;
}

    public void setTrans_pet_diesel (String trans_pet_diesel)
    {
        this.trans_pet_diesel = trans_pet_diesel;
    }

    public String getTrans_pet_diesel_per ()
    {
        return trans_pet_diesel_per;
    }

    public void setTrans_pet_diesel_per (String trans_pet_diesel_per)
    {
        this.trans_pet_diesel_per = trans_pet_diesel_per;
    }

    public String getTrans_commute_per ()
    {
        return trans_commute_per;
    }

    public void setTrans_commute_per (String trans_commute_per)
    {
        this.trans_commute_per = trans_commute_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [trans_park_misce_per = "+trans_park_misce_per+", trans_park_misce = "+trans_park_misce+", trans_trav_per = "+trans_trav_per+", trans_trav = "+trans_trav+", trans_veh_maint = "+trans_veh_maint+", trans_commute = "+trans_commute+", trans_pet_diesel = "+trans_pet_diesel+", trans_pet_diesel_per = "+trans_pet_diesel_per+", trans_commute_per = "+trans_commute_per+"]";
    }
}
