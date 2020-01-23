package com.purplepath.purplepath.expensesRedesign.expensesredesignmodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 30/05/17.
 */

public class Expense_cat_lev0 implements Serializable {

    private String id;

    private String lev0_name;

    private String tb_field_name;

    private String value;

    private String info_value;

    public String getId() {
        return this.id;
    }

    private ArrayList<Expense_cat_lev1> expense_cat_lev1;

    public ArrayList<Expense_cat_lev1> getExpense_cat_lev1() {
        return expense_cat_lev1;
    }

    public void setExpense_cat_lev1(ArrayList<Expense_cat_lev1> expense_cat_lev1) {
        this.expense_cat_lev1 = expense_cat_lev1;
    }

    public void setId(String id) {
        this.id = id;

    }

    public String getLev0_name() {
        return this.lev0_name;
    }

    public void setLev0_name(String lev0_name) {
        this.lev0_name = lev0_name;
    }

    public String getTb_field_name() {
        return this.tb_field_name;
    }

    public void setTb_field_name(String tb_field_name) {
        this.tb_field_name = tb_field_name;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getInfo_value() {
        return this.info_value;
    }

    public void setInfo_value(String info_value) {
        this.info_value = info_value;
    }
}
