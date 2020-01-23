package com.purplepath.purplepath.recommendation.getgoalmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 9/9/17.
 */

public class Resources_used implements Serializable {
    private String liquid;

    private String equity;

    private String debt;

    public String getLiquid ()
    {
        return liquid;
    }

    public void setLiquid (String liquid)
    {
        this.liquid = liquid;
    }

    public String getEquity ()
    {
        return equity;
    }

    public void setEquity (String equity)
    {
        this.equity = equity;
    }

    public String getDebt ()
    {
        return debt;
    }

    public void setDebt (String debt)
    {
        this.debt = debt;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [liquid = "+liquid+", equity = "+equity+", debt = "+debt+"]";
    }
}

