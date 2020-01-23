package com.purplepath.purplepath.expenses.expensesmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 01/06/17.
 */

public class ExpensesLevelZeroData implements Serializable {

    private String tb_field_name;

    private String id;

    private String lev0_name;

    private ArrayList<ExpensesLevelOneData> expense_cat_lev1;

    public ArrayList<ExpensesLevelOneData> getExpense_cat_lev1() {
        return expense_cat_lev1;
    }

    public void setExpense_cat_lev1(ArrayList<ExpensesLevelOneData> expense_cat_lev1) {
        this.expense_cat_lev1 = expense_cat_lev1;
    }

    public String getTb_field_name ()
    {
        return tb_field_name;
    }

    public void setTb_field_name (String tb_field_name)
    {
        this.tb_field_name = tb_field_name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getLev0_name ()
    {
        return lev0_name;
    }

    public void setLev0_name (String lev0_name)
    {
        this.lev0_name = lev0_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tb_field_name = "+tb_field_name+", id = "+id+", lev0_name = "+lev0_name+"]";
    }
}
