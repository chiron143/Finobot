package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class Tax_det implements Serializable
{
    private String tax_prop_tax_per;

    private String tax_in_tax;

    private String tax_in_tax_per;

    private String tax_prop_tax;

    public String getTax_prop_tax_per ()
    {
        return tax_prop_tax_per;
    }

    public void setTax_prop_tax_per (String tax_prop_tax_per)
    {
        this.tax_prop_tax_per = tax_prop_tax_per;
    }

    public String getTax_in_tax ()
{
    return tax_in_tax;
}

    public void setTax_in_tax (String tax_in_tax)
    {
        this.tax_in_tax = tax_in_tax;
    }

    public String getTax_in_tax_per ()
    {
        return tax_in_tax_per;
    }

    public void setTax_in_tax_per (String tax_in_tax_per)
    {
        this.tax_in_tax_per = tax_in_tax_per;
    }

    public String getTax_prop_tax ()
{
    return tax_prop_tax;
}

    public void setTax_prop_tax (String tax_prop_tax)
    {
        this.tax_prop_tax = tax_prop_tax;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tax_prop_tax_per = "+tax_prop_tax_per+", tax_in_tax = "+tax_in_tax+", tax_in_tax_per = "+tax_in_tax_per+", tax_prop_tax = "+tax_prop_tax+"]";
    }
}