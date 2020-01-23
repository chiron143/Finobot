package com.purplepath.purplepath.taxprompt.gettaxpromptmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 1/24/18.
 */

public class Tax_classification implements Serializable{

    private String benefit;

    private String income;

    private String avg_tax_rate;

    private String mar_tax_rate;

    private String label;

    private String total_tax_payable;

    public String getBenefit ()
    {
        return benefit;
    }

    public void setBenefit (String benefit)
    {
        this.benefit = benefit;
    }

    public String getIncome ()
    {
        return income;
    }

    public void setIncome (String income)
    {
        this.income = income;
    }

    public String getAvg_tax_rate ()
    {
        return avg_tax_rate;
    }

    public void setAvg_tax_rate (String avg_tax_rate)
    {
        this.avg_tax_rate = avg_tax_rate;
    }

    public String getMar_tax_rate ()
    {
        return mar_tax_rate;
    }

    public void setMar_tax_rate (String mar_tax_rate)
    {
        this.mar_tax_rate = mar_tax_rate;
    }

    public String getLabel ()
    {
        return label;
    }

    public void setLabel (String label)
    {
        this.label = label;
    }

    public String getTotal_tax_payable ()
    {
        return total_tax_payable;
    }

    public void setTotal_tax_payable (String total_tax_payable)
    {
        this.total_tax_payable = total_tax_payable;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [benefit = "+benefit+", income = "+income+", avg_tax_rate = "+avg_tax_rate+", mar_tax_rate = "+mar_tax_rate+", label = "+label+", total_tax_payable = "+total_tax_payable+"]";
    }
}

