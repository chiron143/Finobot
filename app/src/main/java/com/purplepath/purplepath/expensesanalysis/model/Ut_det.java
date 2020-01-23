package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class Ut_det implements Serializable
{
    private String ut_gas;

    private String ut_elec;

    private String ut_mb_ph;

    private String ut_water;

    private String ut_mb_ph_per;

    private String ut_gas_per;

    private String ut_water_per;

    private String ut_tel_per;

    private String ut_elec_per;

    private String ut_cab_sat;

    private String ut_cab_sat_per;

    private String ut_int_board;

    private String ut_tel;

    private String ut_int_board_per;

    public String getUt_gas ()
    {
        return ut_gas;
    }

    public void setUt_gas (String ut_gas)
    {
        this.ut_gas = ut_gas;
    }

    public String getUt_elec ()
    {
        return ut_elec;
    }

    public void setUt_elec (String ut_elec)
    {
        this.ut_elec = ut_elec;
    }

    public String getUt_mb_ph ()
    {
        return ut_mb_ph;
    }

    public void setUt_mb_ph (String ut_mb_ph)
    {
        this.ut_mb_ph = ut_mb_ph;
    }

    public String getUt_water ()
    {
        return ut_water;
    }

    public void setUt_water (String ut_water)
    {
        this.ut_water = ut_water;
    }

    public String getUt_mb_ph_per ()
    {
        return ut_mb_ph_per;
    }

    public void setUt_mb_ph_per (String ut_mb_ph_per)
    {
        this.ut_mb_ph_per = ut_mb_ph_per;
    }

    public String getUt_gas_per ()
    {
        return ut_gas_per;
    }

    public void setUt_gas_per (String ut_gas_per)
    {
        this.ut_gas_per = ut_gas_per;
    }

    public String getUt_water_per ()
    {
        return ut_water_per;
    }

    public void setUt_water_per (String ut_water_per)
    {
        this.ut_water_per = ut_water_per;
    }

    public String getUt_tel_per ()
    {
        return ut_tel_per;
    }

    public void setUt_tel_per (String ut_tel_per)
    {
        this.ut_tel_per = ut_tel_per;
    }

    public String getUt_elec_per ()
    {
        return ut_elec_per;
    }

    public void setUt_elec_per (String ut_elec_per)
    {
        this.ut_elec_per = ut_elec_per;
    }

    public String getUt_cab_sat ()
    {
        return ut_cab_sat;
    }

    public void setUt_cab_sat (String ut_cab_sat)
    {
        this.ut_cab_sat = ut_cab_sat;
    }

    public String getUt_cab_sat_per ()
    {
        return ut_cab_sat_per;
    }

    public void setUt_cab_sat_per (String ut_cab_sat_per)
    {
        this.ut_cab_sat_per = ut_cab_sat_per;
    }

    public String getUt_int_board ()
    {
        return ut_int_board;
    }

    public void setUt_int_board (String ut_int_board)
    {
        this.ut_int_board = ut_int_board;
    }

    public String getUt_tel ()
    {
        return ut_tel;
    }

    public void setUt_tel (String ut_tel)
    {
        this.ut_tel = ut_tel;
    }

    public String getUt_int_board_per ()
    {
        return ut_int_board_per;
    }

    public void setUt_int_board_per (String ut_int_board_per)
    {
        this.ut_int_board_per = ut_int_board_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [ut_gas = "+ut_gas+", ut_elec = "+ut_elec+", ut_mb_ph = "+ut_mb_ph+", ut_water = "+ut_water+", ut_mb_ph_per = "+ut_mb_ph_per+", ut_gas_per = "+ut_gas_per+", ut_water_per = "+ut_water_per+", ut_tel_per = "+ut_tel_per+", ut_elec_per = "+ut_elec_per+", ut_cab_sat = "+ut_cab_sat+", ut_cab_sat_per = "+ut_cab_sat_per+", ut_int_board = "+ut_int_board+", ut_tel = "+ut_tel+", ut_int_board_per = "+ut_int_board_per+"]";
    }
}

