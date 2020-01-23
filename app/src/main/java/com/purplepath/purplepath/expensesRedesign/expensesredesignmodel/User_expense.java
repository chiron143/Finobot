package com.purplepath.purplepath.expensesRedesign.expensesredesignmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 29-Aug-16.
 */
public class User_expense implements Serializable
{
    private  String level_0_ids;

    private String level_1_ids;

    private ArrayList<Expense_cat_lev3> expense_cat_lev3;

    private String level_3_ids;

    private String level_2_ids;

    public String getLevel_0_ids() {
        return level_0_ids;
    }

    public void setLevel_0_ids(String level_0_ids) {
        this.level_0_ids = level_0_ids;
    }

    private  ArrayList<Expense_cat_lev0> expense_cat_lev0;

    private ArrayList<Expense_cat_lev1> expense_cat_lev1;

    private ArrayList<Expense_cat_lev2> expense_cat_lev2;

    public ArrayList<Expense_cat_lev0> getExpense_cat_lev0() {
        return expense_cat_lev0;
    }

    public void setExpense_cat_lev0(ArrayList<Expense_cat_lev0> expense_cat_lev0) {
        this.expense_cat_lev0 = expense_cat_lev0;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    private String user_id;

    private String family_id;

    public String getLevel_1_ids ()
    {
        return level_1_ids;
    }

    public void setLevel_1_ids (String level_1_ids)
    {
        this.level_1_ids = level_1_ids;
    }

    public ArrayList<Expense_cat_lev3> getExpense_cat_lev3 ()
    {
        return expense_cat_lev3;
    }

    public void setExpense_cat_lev3 (ArrayList<Expense_cat_lev3> expense_cat_lev3)
    {
        this.expense_cat_lev3 = expense_cat_lev3;
    }

    public String getLevel_3_ids ()
    {
        return level_3_ids;
    }

    public void setLevel_3_ids (String level_3_ids)
    {
        this.level_3_ids = level_3_ids;
    }

    public String getLevel_2_ids ()
    {
        return level_2_ids;
    }

    public void setLevel_2_ids (String level_2_ids)
    {
        this.level_2_ids = level_2_ids;
    }

    public ArrayList<Expense_cat_lev1> getExpense_cat_lev1 ()
    {
        return expense_cat_lev1;
    }

    public void setExpense_cat_lev1 (ArrayList<Expense_cat_lev1> expense_cat_lev1)
    {
        this.expense_cat_lev1 = expense_cat_lev1;
    }

    public ArrayList<Expense_cat_lev2> getExpense_cat_lev2 ()
    {
        return expense_cat_lev2;
    }

    public void setExpense_cat_lev2 (ArrayList<Expense_cat_lev2> expense_cat_lev2)
    {
        this.expense_cat_lev2 = expense_cat_lev2;
    }

    public String getFamily_id ()
    {
        return family_id;
    }

    public void setFamily_id (String family_id)
    {
        this.family_id = family_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [level_1_ids = "+level_1_ids+", expense_cat_lev3 = "+expense_cat_lev3+", level_3_ids = "+level_3_ids+", level_2_ids = "+level_2_ids+", expense_cat_lev1 = "+expense_cat_lev1+", expense_cat_lev2 = "+expense_cat_lev2+", family_id = "+family_id+"]";
    }
}
