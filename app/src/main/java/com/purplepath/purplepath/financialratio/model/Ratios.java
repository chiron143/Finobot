package com.purplepath.purplepath.financialratio.model;

import java.io.Serializable;

/**
 * Created by dinesh on 28/04/17.
 */

public class Ratios implements Serializable {
    private String liquidity_ratio;

    private String debt_equit_ratio;

    private String savings_ratio;

    private String debt_income_ratio;

    private String expense_ratio;

    private String solvency_ratio;

    private String leverage_ratio;

    private String savings_income_ratio;

    public String getLiquidity_ratio ()
    {
        return liquidity_ratio;
    }

    public void setLiquidity_ratio (String liquidity_ratio)
    {
        this.liquidity_ratio = liquidity_ratio;
    }

    public String getDebt_equit_ratio ()
    {
        return debt_equit_ratio;
    }

    public void setDebt_equit_ratio (String debt_equit_ratio)
    {
        this.debt_equit_ratio = debt_equit_ratio;
    }

    public String getSavings_ratio ()
    {
        return savings_ratio;
    }

    public void setSavings_ratio (String savings_ratio)
    {
        this.savings_ratio = savings_ratio;
    }

    public String getDebt_income_ratio ()
    {
        return debt_income_ratio;
    }

    public void setDebt_income_ratio (String debt_income_ratio)
    {
        this.debt_income_ratio = debt_income_ratio;
    }

    public String getExpense_ratio ()
    {
        return expense_ratio;
    }

    public void setExpense_ratio (String expense_ratio)
    {
        this.expense_ratio = expense_ratio;
    }

    public String getSolvency_ratio ()
    {
        return solvency_ratio;
    }

    public void setSolvency_ratio (String solvency_ratio)
    {
        this.solvency_ratio = solvency_ratio;
    }

    public String getLeverage_ratio ()
    {
        return leverage_ratio;
    }

    public void setLeverage_ratio (String leverage_ratio)
    {
        this.leverage_ratio = leverage_ratio;
    }

    public String getSavings_income_ratio ()
    {
        return savings_income_ratio;
    }

    public void setSavings_income_ratio (String savings_income_ratio)
    {
        this.savings_income_ratio = savings_income_ratio;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [liquidity_ratio = "+liquidity_ratio+", debt_equit_ratio = "+debt_equit_ratio+", savings_ratio = "+savings_ratio+", debt_income_ratio = "+debt_income_ratio+", expense_ratio = "+expense_ratio+", solvency_ratio = "+solvency_ratio+", leverage_ratio = "+leverage_ratio+", savings_income_ratio = "+savings_income_ratio+"]";
    }
}
