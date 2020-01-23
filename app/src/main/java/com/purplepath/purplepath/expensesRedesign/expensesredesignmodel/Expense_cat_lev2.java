package com.purplepath.purplepath.expensesRedesign.expensesredesignmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 25-Aug-16.
 */
public class Expense_cat_lev2 implements Serializable{
    private String tb_field_name;

    private String id;

    private String lev2_name;

    private String lev1_id;

    private String value;

    private ArrayList<Expense_cat_lev3> expense_cat_lev3;

    public String getInfo_value() {
        return info_value;
    }

    public ArrayList<Expense_cat_lev3> getExpense_cat_lev3() {
        return expense_cat_lev3;
    }

    public void setExpense_cat_lev3(ArrayList<Expense_cat_lev3> expense_cat_lev3) {
        this.expense_cat_lev3 = expense_cat_lev3;
    }

    public void setInfo_value(String info_value) {
        this.info_value = info_value;
    }

    private String info_value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTb_field_name() {
        return tb_field_name;
    }

    public void setTb_field_name(String tb_field_name) {
        this.tb_field_name = tb_field_name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLev2_name() {
        return lev2_name;
    }

    public void setLev2_name(String lev2_name) {
        this.lev2_name = lev2_name;
    }

    public String getLev1_id() {
        return lev1_id;
    }

    public void setLev1_id(String lev1_id) {
        this.lev1_id = lev1_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [tb_field_name = " + tb_field_name + ", id = " + id + ", lev2_name = " + lev2_name + ", lev1_id = " + lev1_id + "]";
    }
}
