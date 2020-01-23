package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class Purch_det implements Serializable
{
    private String purch_houhold_items_per;

    private String purch_houhold_items;

    private String purch_appar_cloth_per;

    private String purch_appar_cloth;

    public String getPurch_houhold_items_per ()
    {
        return purch_houhold_items_per;
    }

    public void setPurch_houhold_items_per (String purch_houhold_items_per)
    {
        this.purch_houhold_items_per = purch_houhold_items_per;
    }

    public String getPurch_houhold_items ()
{
    return purch_houhold_items;
}

    public void setPurch_houhold_items (String purch_houhold_items)
    {
        this.purch_houhold_items = purch_houhold_items;
    }

    public String getPurch_appar_cloth_per ()
    {
        return purch_appar_cloth_per;
    }

    public void setPurch_appar_cloth_per (String purch_appar_cloth_per)
    {
        this.purch_appar_cloth_per = purch_appar_cloth_per;
    }

    public String getPurch_appar_cloth ()
{
    return purch_appar_cloth;
}

    public void setPurch_appar_cloth (String purch_appar_cloth)
    {
        this.purch_appar_cloth = purch_appar_cloth;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [purch_houhold_items_per = "+purch_houhold_items_per+", purch_houhold_items = "+purch_houhold_items+", purch_appar_cloth_per = "+purch_appar_cloth_per+", purch_appar_cloth = "+purch_appar_cloth+"]";
    }
}