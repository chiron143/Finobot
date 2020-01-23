package com.purplepath.purplepath.expensesRedesign.expensesredesignmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 25-Aug-16.
 */
public class Expense_cat_lev1 implements Serializable
{
    private String tb_field_name;

    private String lev1_name;

    private String id;

    private String type;

    @SerializedName("lev0_id")
    private String lev0_id;

    public String getLev0_id() {
        return lev0_id;
    }

    private String value;

    public ArrayList<Expense_cat_lev2> getExpense_cat_lev2() {
        return expense_cat_lev2;
    }

    public void setExpense_cat_lev2(ArrayList<Expense_cat_lev2> expense_cat_lev2) {
        this.expense_cat_lev2 = expense_cat_lev2;
    }

    private ArrayList<Expense_cat_lev2> expense_cat_lev2;

    public String getInfo_value() {
        return info_value;
    }

    public void setInfo_value(String info_value) {
        this.info_value = info_value;
    }

    private String info_value;
    public String  getValue ()
{
    return value;
}

    public void setValue (String value)
    {
        this.value = value;
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

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [tb_field_name = "+tb_field_name+", lev1_name = "+lev1_name+", id = "+id+", type = "+type+"]";
    }
}