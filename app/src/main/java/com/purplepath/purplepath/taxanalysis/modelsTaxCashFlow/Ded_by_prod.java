package com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow;

import java.io.Serializable;

/**
 * Created by pravinr on 1/4/18.
 */

public class Ded_by_prod implements Serializable{
    private String entitled;

    private String contr_val;

    private String allowed_value;

    private String tax_section;

    private String prod_name;

    public String getEntitled ()
    {
        return entitled;
    }

    public void setEntitled (String entitled)
    {
        this.entitled = entitled;
    }

    public String getContr_val ()
    {
        return contr_val;
    }

    public void setContr_val (String contr_val)
    {
        this.contr_val = contr_val;
    }

    public String getAllowed_value ()
    {
        return allowed_value;
    }

    public void setAllowed_value (String allowed_value)
    {
        this.allowed_value = allowed_value;
    }

    public String getTax_section ()
    {
        return tax_section;
    }

    public void setTax_section (String tax_section)
    {
        this.tax_section = tax_section;
    }

    public String getProd_name ()
    {
        return prod_name;
    }

    public void setProd_name (String prod_name)
    {
        this.prod_name = prod_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [entitled = "+entitled+", contr_val = "+contr_val+", allowed_value = "+allowed_value+", tax_section = "+tax_section+", prod_name = "+prod_name+"]";
    }
}

