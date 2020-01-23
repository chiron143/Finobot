package com.purplepath.purplepath.expenses.expensesmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 28-Jun-16.
 */
public class ExpensesLevelTwoData implements Serializable
{
    private String tb_field_name;

    private String id;

    private String lev2_name;

    private String lev1_id;

    public String getTb_field_name () {
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

    public String getLev2_name ()
    {
        return lev2_name;
    }

    public void setLev2_name (String lev2_name)
    {
        this.lev2_name = lev2_name;
    }

    public String getLev1_id ()
    {
        return lev1_id;
    }

    public void setLev1_id (String lev1_id)
    {
        this.lev1_id = lev1_id;
    }

    public ArrayList<ExpensesLevelThreeData> getExpensesLevelThreeData() {
        return expensesLevelThreeData;
    }

    public void setExpensesLevelThreeData(ArrayList<ExpensesLevelThreeData> expensesLevelThreeData) {
        this.expensesLevelThreeData = expensesLevelThreeData;
    }

    ArrayList<ExpensesLevelThreeData> expensesLevelThreeData;


    @Override
    public String toString()
    {
        return "ClassPojo [tb_field_name = "+tb_field_name+", id = "+id+", lev2_name = "+lev2_name+", lev1_id = "+lev1_id+"]";
    }
}
