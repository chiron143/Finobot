package com.purplepath.purplepath.expensesRedesign.expensesredesignmodel;

import java.io.Serializable;

/**
 * Created by Bert on 25-Aug-16.
 */
public class Expense_cat_lev3 implements Serializable {
    private String tb_field_name;

    private String id;

    private String lev2_id;

    private String lev3_name;

    private String value;

    public String getValue() {
        return value;
    }

    public String getInfo_value() {
        return info_value;
    }

    public void setInfo_value(String info_value) {
        this.info_value = info_value;
    }

    private String info_value;

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

    public String getLev2_id() {
        return lev2_id;
    }

    public void setLev2_id(String lev2_id) {
        this.lev2_id = lev2_id;
    }

    public String getLev3_name() {
        return lev3_name;
    }

    public void setLev3_name(String lev3_name) {
        this.lev3_name = lev3_name;
    }

    @Override
    public String toString() {
        return "ClassPojo [tb_field_name = " + tb_field_name + ", id = " + id + ", lev2_id = " + lev2_id + ", lev3_name = " + lev3_name + "]";
    }
}

