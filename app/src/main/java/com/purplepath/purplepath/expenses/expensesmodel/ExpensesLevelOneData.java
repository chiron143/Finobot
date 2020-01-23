package com.purplepath.purplepath.expenses.expensesmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 28-Jun-16.
 */
public class ExpensesLevelOneData implements Serializable
{
    private String lev1_name;

    private String id;

    private String tb_field_name;

    public String type;

    private String lev0_id;

    public String getLev0_id() {
        return lev0_id;
    }

    public void setLev0_id(String lev0_id) {
        this.lev0_id = lev0_id;
    }

    public String getTb_field_name ()
    {
        return tb_field_name;
    }

    public void setTb_field_name (String tb_field_name)
    {
        this.tb_field_name = tb_field_name;
    }

    public String getLev1_name ()
    {
        return lev1_name;
    }

    public void setLev1_name (String lev1_name)
    {
        this.lev1_name = lev1_name;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<ExpensesLevelTwoData> getExpensesLevelTwoData() {
        return expensesLevelTwoData;
    }

    public void setExpensesLevelTwoData(ArrayList<ExpensesLevelTwoData> expensesLevelTwoData) {
        this.expensesLevelTwoData = expensesLevelTwoData;
    }

    ArrayList<ExpensesLevelTwoData> expensesLevelTwoData;

    public ArrayList<ExpensesLevelOneData> getExpensesLevelOneData() {
        return expensesLevelOneData;
    }

    public void setExpensesLevelOneData(ArrayList<ExpensesLevelOneData> expensesLevelOneData) {
        this.expensesLevelOneData = expensesLevelOneData;
    }

    ArrayList<ExpensesLevelOneData> expensesLevelOneData;


    @Override
    public String toString()
    {
        return "ClassPojo [tb_field_name = "+tb_field_name+", lev1_name = "+lev1_name+", id = "+id+"]";
    }
}