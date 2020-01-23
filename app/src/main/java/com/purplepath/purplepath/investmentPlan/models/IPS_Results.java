package com.purplepath.purplepath.investmentPlan.models;

import android.util.Log;

import java.io.Serializable;

public class IPS_Results implements Serializable {


    public Equity_available_products getEquity_available_products() {
        return equity_available_products;
    }

    public void setEquity_available_products(Equity_available_products equity_available_products) {
        this.equity_available_products = equity_available_products;
    }

    public Debt_available_products getDebt_available_products() {
        return debt_available_products;
    }

    public void setDebt_available_products(Debt_available_products debt_available_products) {
        this.debt_available_products = debt_available_products;
    }

    public Liquid_available_products getLiquid_available_products() {
        return liquid_available_products;
    }

    public void setLiquid_available_products(Liquid_available_products liquid_available_products) {
        this.liquid_available_products = liquid_available_products;
    }

    public Comm_available_products getComm_available_products() {
        return comm_available_products;
    }

    public void setComm_available_products(Comm_available_products comm_available_products) {
        this.comm_available_products = comm_available_products;
    }

    public Real_estate_available_products getReal_estate_available_products() {
        return real_estate_available_products;
    }

    public void setReal_estate_available_products(Real_estate_available_products real_estate_available_products) {
        this.real_estate_available_products = real_estate_available_products;
    }

    private Equity_available_products equity_available_products;

    private Debt_available_products debt_available_products;

    private Liquid_available_products liquid_available_products;

    private Comm_available_products comm_available_products;

    private Real_estate_available_products real_estate_available_products;
    private String comm_pmt_yearly;

    public String getAssets_class() {
        return assets_class;
    }

    public void setAssets_class(String assets_class) {
        this.assets_class = assets_class;
    }

    private String assets_class = "";
    private String notes;

    private String comm_annu_due_pv;

    private String goal_cat_lev1_id;

    private String goal_years;

    private String debt_annu_due_pv;

    private String created_datetime;

    private String lev1_exp_inc;

    private Debt_recommended_products debt_recommended_products;

    private String goal_name;

    private String comm_pmt_lumpsum;

    private Real_estate_recommended_products real_estate_recommended_products;

    private String lev3_exp_inc;

    private String id;

    private String liquid_per;

    public String getComm_current_value() {
        return comm_current_value;
    }

    public void setComm_current_value(String comm_current_value) {
        this.comm_current_value = comm_current_value;
    }

    private String comm_current_value;

    private String goal_priority;

    private String debt_fv;

    private Equity_recommended_products equity_recommended_products;

    private String real_estate_pmt_yearly;

    private String equity_pmt_yearly;

    private Comm_recommended_products comm_recommended_products;

    private String goal_cat_lev2_id;

    private String liquid_annu_due_pv;

    private String real_estate_annu_due_pv;

    private String debt_pmt_lumpsum;

    private String real_estate_pmt_monthly;

    private Liquid_recommended_products liquid_recommended_products;

    private String expected_increment;

    private String cost_of_goal;

    private String goal_imp;

    private String user_id;

    private String goal_flexibility;

    private String goal_interval;

    private String goal_recurrence;

    private String goal_start_datetime;

    public String getEquity_annual_contr() {
        return equity_annual_contr;
    }

    public void setEquity_annual_contr(String equity_annual_contr) {
        this.equity_annual_contr = equity_annual_contr;
    }

    private String equity_annual_contr;
    private String fixed_inc_annual_contr;
    private String liquid_annual_contr;

    public String getFixed_inc_annual_contr() {
        return fixed_inc_annual_contr;
    }

    public void setFixed_inc_annual_contr(String fixed_inc_annual_contr) {
        this.fixed_inc_annual_contr = fixed_inc_annual_contr;
    }

    public String getLiquid_annual_contr() {
        return liquid_annual_contr;
    }

    public void setLiquid_annual_contr(String liquid_annual_contr) {
        this.liquid_annual_contr = liquid_annual_contr;
    }

    public String getComm_annual_contr() {
        return comm_annual_contr;
    }

    public void setComm_annual_contr(String comm_annual_contr) {
        this.comm_annual_contr = comm_annual_contr;
    }

    public String getReal_estate_annual_contr() {
        return real_estate_annual_contr;
    }

    public void setReal_estate_annual_contr(String real_estate_annual_contr) {
        this.real_estate_annual_contr = real_estate_annual_contr;
    }

    private String comm_annual_contr;
    private String real_estate_annual_contr;

    private String modified_datetime;

    private String liquid_pmt_yearly;

    private String real_estate_per;

    private String status;

    private String liquid_fv;

    private String other_category;

    private String belongs_to_id;

    private String comm_fv;

    private String goal_duration;

    private String recur_months;

    private String equity_pmt_monthly;

    private String goal_end_datetime;

    private String equity_pmt_lumpsum;

    private String real_estate_fv;

    private String goal_cat_lev3_id;

    private String lev2_exp_inc;

    private String goal_frequency;

    private String debt_pmt_monthly;

    private String liquid_pmt_lumpsum;

    private String real_estate_pmt_lumpsum;

    private String debt_per;

    private String goal_accomp_datetime;

    private String recur_years;

    private String equity_annu_due_pv;

    private String equity_per;

    private String liquid_pmt_monthly;

    private String debt_pmt_yearly;

    private String equity_fv;

    private String comm_pmt_monthly;

    private String goal_occurrence;

    private String comm_per;

    public String getComm_pmt_yearly() {
        return comm_pmt_yearly;
    }

    public void setComm_pmt_yearly(String comm_pmt_yearly) {
        this.comm_pmt_yearly = comm_pmt_yearly;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getComm_annu_due_pv() {
        return comm_annu_due_pv;
    }

    public void setComm_annu_due_pv(String comm_annu_due_pv) {
        this.comm_annu_due_pv = comm_annu_due_pv;
    }

    public String getGoal_cat_lev1_id() {
        return goal_cat_lev1_id;
    }

    public void setGoal_cat_lev1_id(String goal_cat_lev1_id) {
        this.goal_cat_lev1_id = goal_cat_lev1_id;
    }

    public String getGoal_years() {
        return goal_years;
    }

    public void setGoal_years(String goal_years) {
        this.goal_years = goal_years;
    }

    public String getDebt_annu_due_pv() {
        return debt_annu_due_pv;
    }

    public void setDebt_annu_due_pv(String debt_annu_due_pv) {
        this.debt_annu_due_pv = debt_annu_due_pv;
    }

    public String getCreated_datetime() {
        return created_datetime;
    }

    public void setCreated_datetime(String created_datetime) {
        this.created_datetime = created_datetime;
    }

    public String getLev1_exp_inc() {
        return lev1_exp_inc;
    }

    public void setLev1_exp_inc(String lev1_exp_inc) {
        this.lev1_exp_inc = lev1_exp_inc;
    }

    public Debt_recommended_products getDebt_recommended_products() {
        return debt_recommended_products;
    }

    public void setDebt_recommended_products(Debt_recommended_products debt_recommended_products) {
        this.debt_recommended_products = debt_recommended_products;
    }

    public String getGoal_name() {
        return goal_name;
    }

    public void setGoal_name(String goal_name) {
        this.goal_name = goal_name;
    }

    public String getComm_pmt_lumpsum() {
        return comm_pmt_lumpsum;
    }

    public void setComm_pmt_lumpsum(String comm_pmt_lumpsum) {
        this.comm_pmt_lumpsum = comm_pmt_lumpsum;
    }

    public Real_estate_recommended_products getReal_estate_recommended_products() {
        return real_estate_recommended_products;
    }

    public void setReal_estate_recommended_products(Real_estate_recommended_products real_estate_recommended_products) {
        this.real_estate_recommended_products = real_estate_recommended_products;
    }

    public String getLev3_exp_inc() {
        return lev3_exp_inc;
    }

    public void setLev3_exp_inc(String lev3_exp_inc) {
        this.lev3_exp_inc = lev3_exp_inc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiquid_per() {
        return liquid_per;
    }

    public void setLiquid_per(String liquid_per) {
        this.liquid_per = liquid_per;
    }

    public String getGoal_priority() {
        return goal_priority;
    }

    public void setGoal_priority(String goal_priority) {
        this.goal_priority = goal_priority;
    }

    public String getDebt_fv() {
        return debt_fv;
    }

    public void setDebt_fv(String debt_fv) {
        this.debt_fv = debt_fv;
    }


    public Equity_recommended_products getEquity_recommended_products() {
        return equity_recommended_products;
    }


    public void setEquity_recommended_products(Equity_recommended_products equity_recommended_products) {
        this.equity_recommended_products = equity_recommended_products;
    }

    public String getReal_estate_pmt_yearly() {
        return real_estate_pmt_yearly;
    }

    public void setReal_estate_pmt_yearly(String real_estate_pmt_yearly) {
        this.real_estate_pmt_yearly = real_estate_pmt_yearly;
    }

    public String getEquity_pmt_yearly() {
        return equity_pmt_yearly;
    }

    public void setEquity_pmt_yearly(String equity_pmt_yearly) {
        this.equity_pmt_yearly = equity_pmt_yearly;
    }

    public Comm_recommended_products getComm_recommended_products() {
        return comm_recommended_products;
    }

    public void setComm_recommended_products(Comm_recommended_products comm_recommended_products) {
        this.comm_recommended_products = comm_recommended_products;
    }

    public String getGoal_cat_lev2_id() {
        return goal_cat_lev2_id;
    }

    public void setGoal_cat_lev2_id(String goal_cat_lev2_id) {
        this.goal_cat_lev2_id = goal_cat_lev2_id;
    }

    public String getLiquid_annu_due_pv() {
        return liquid_annu_due_pv;
    }

    public void setLiquid_annu_due_pv(String liquid_annu_due_pv) {
        this.liquid_annu_due_pv = liquid_annu_due_pv;
    }

    public String getReal_estate_annu_due_pv() {
        return real_estate_annu_due_pv;
    }

    public void setReal_estate_annu_due_pv(String real_estate_annu_due_pv) {
        this.real_estate_annu_due_pv = real_estate_annu_due_pv;
    }

    public String getDebt_pmt_lumpsum() {
        return debt_pmt_lumpsum;
    }

    public void setDebt_pmt_lumpsum(String debt_pmt_lumpsum) {
        this.debt_pmt_lumpsum = debt_pmt_lumpsum;
    }

    public String getReal_estate_pmt_monthly() {
        return real_estate_pmt_monthly;
    }

    public void setReal_estate_pmt_monthly(String real_estate_pmt_monthly) {
        this.real_estate_pmt_monthly = real_estate_pmt_monthly;
    }

    public Liquid_recommended_products getLiquid_recommended_products() {
        return liquid_recommended_products;
    }

    public void setLiquid_recommended_products(Liquid_recommended_products liquid_recommended_products) {
        this.liquid_recommended_products = liquid_recommended_products;
    }

    public String getExpected_increment() {
        return expected_increment;
    }

    public void setExpected_increment(String expected_increment) {
        this.expected_increment = expected_increment;
    }

    public String getCost_of_goal() {
        return cost_of_goal;
    }

    public void setCost_of_goal(String cost_of_goal) {
        this.cost_of_goal = cost_of_goal;
    }

    public String getGoal_imp() {
        return goal_imp;
    }

    public void setGoal_imp(String goal_imp) {
        this.goal_imp = goal_imp;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGoal_flexibility() {
        return goal_flexibility;
    }

    public void setGoal_flexibility(String goal_flexibility) {
        this.goal_flexibility = goal_flexibility;
    }

    public String getGoal_interval() {
        return goal_interval;
    }

    public void setGoal_interval(String goal_interval) {
        this.goal_interval = goal_interval;
    }

    public String getGoal_recurrence() {
        return goal_recurrence;
    }

    public void setGoal_recurrence(String goal_recurrence) {
        this.goal_recurrence = goal_recurrence;
    }

    public String getGoal_start_datetime() {
        return goal_start_datetime;
    }

    public void setGoal_start_datetime(String goal_start_datetime) {
        this.goal_start_datetime = goal_start_datetime;
    }

    public String getModified_datetime() {
        return modified_datetime;
    }

    public void setModified_datetime(String modified_datetime) {
        this.modified_datetime = modified_datetime;
    }

    public String getLiquid_pmt_yearly() {
        return liquid_pmt_yearly;
    }

    public void setLiquid_pmt_yearly(String liquid_pmt_yearly) {
        this.liquid_pmt_yearly = liquid_pmt_yearly;
    }

    public String getReal_estate_per() {
        return real_estate_per;
    }

    public void setReal_estate_per(String real_estate_per) {
        this.real_estate_per = real_estate_per;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLiquid_fv() {
        return liquid_fv;
    }

    public void setLiquid_fv(String liquid_fv) {
        this.liquid_fv = liquid_fv;
    }

    public String getOther_category() {
        return other_category;
    }

    public void setOther_category(String other_category) {
        this.other_category = other_category;
    }

    public String getBelongs_to_id() {
        return belongs_to_id;
    }

    public void setBelongs_to_id(String belongs_to_id) {
        this.belongs_to_id = belongs_to_id;
    }

    public String getComm_fv() {
        return comm_fv;
    }

    public void setComm_fv(String comm_fv) {
        this.comm_fv = comm_fv;
    }

    public String getGoal_duration() {
        return goal_duration;
    }

    public void setGoal_duration(String goal_duration) {
        this.goal_duration = goal_duration;
    }

    public String getRecur_months() {
        return recur_months;
    }

    public void setRecur_months(String recur_months) {
        this.recur_months = recur_months;
    }

    public String getEquity_pmt_monthly() {
        return equity_pmt_monthly;
    }

    public void setEquity_pmt_monthly(String equity_pmt_monthly) {
        this.equity_pmt_monthly = equity_pmt_monthly;
    }

    public String getGoal_end_datetime() {
        return goal_end_datetime;
    }

    public void setGoal_end_datetime(String goal_end_datetime) {
        this.goal_end_datetime = goal_end_datetime;
    }

    public String getEquity_pmt_lumpsum() {
        return equity_pmt_lumpsum;
    }

    public void setEquity_pmt_lumpsum(String equity_pmt_lumpsum) {
        this.equity_pmt_lumpsum = equity_pmt_lumpsum;
    }

    public String getReal_estate_fv() {
        return real_estate_fv;
    }

    public void setReal_estate_fv(String real_estate_fv) {
        this.real_estate_fv = real_estate_fv;
    }

    public String getGoal_cat_lev3_id() {
        return goal_cat_lev3_id;
    }

    public void setGoal_cat_lev3_id(String goal_cat_lev3_id) {
        this.goal_cat_lev3_id = goal_cat_lev3_id;
    }

    public String getLev2_exp_inc() {
        return lev2_exp_inc;
    }

    public void setLev2_exp_inc(String lev2_exp_inc) {
        this.lev2_exp_inc = lev2_exp_inc;
    }

    public String getGoal_frequency() {
        return goal_frequency;
    }

    public void setGoal_frequency(String goal_frequency) {
        this.goal_frequency = goal_frequency;
    }

    public String getDebt_pmt_monthly() {
        return debt_pmt_monthly;
    }

    public void setDebt_pmt_monthly(String debt_pmt_monthly) {
        this.debt_pmt_monthly = debt_pmt_monthly;
    }

    public String getLiquid_pmt_lumpsum() {
        return liquid_pmt_lumpsum;
    }

    public void setLiquid_pmt_lumpsum(String liquid_pmt_lumpsum) {
        this.liquid_pmt_lumpsum = liquid_pmt_lumpsum;
    }

    public String getReal_estate_pmt_lumpsum() {
        return real_estate_pmt_lumpsum;
    }

    public void setReal_estate_pmt_lumpsum(String real_estate_pmt_lumpsum) {
        this.real_estate_pmt_lumpsum = real_estate_pmt_lumpsum;
    }

    public String getDebt_per() {
        return debt_per;
    }

    public void setDebt_per(String debt_per) {
        this.debt_per = debt_per;
    }

    public String getGoal_accomp_datetime() {
        return goal_accomp_datetime;
    }

    public void setGoal_accomp_datetime(String goal_accomp_datetime) {
        this.goal_accomp_datetime = goal_accomp_datetime;
    }

    public String getRecur_years() {
        return recur_years;
    }

    public void setRecur_years(String recur_years) {
        this.recur_years = recur_years;
    }

    public String getEquity_annu_due_pv() {
        return equity_annu_due_pv;
    }

    public void setEquity_annu_due_pv(String equity_annu_due_pv) {
        this.equity_annu_due_pv = equity_annu_due_pv;
    }

    public String getEquity_per() {
        return equity_per;
    }

    public void setEquity_per(String equity_per) {
        this.equity_per = equity_per;
    }

    public String getLiquid_pmt_monthly() {
        return liquid_pmt_monthly;
    }

    public void setLiquid_pmt_monthly(String liquid_pmt_monthly) {
        this.liquid_pmt_monthly = liquid_pmt_monthly;
    }

    public String getDebt_pmt_yearly() {
        return debt_pmt_yearly;
    }

    public void setDebt_pmt_yearly(String debt_pmt_yearly) {
        this.debt_pmt_yearly = debt_pmt_yearly;
    }

    public String getEquity_fv() {
        return equity_fv;
    }

    public void setEquity_fv(String equity_fv) {
        this.equity_fv = equity_fv;
    }

    public String getComm_pmt_monthly() {
        return comm_pmt_monthly;
    }

    public void setComm_pmt_monthly(String comm_pmt_monthly) {
        this.comm_pmt_monthly = comm_pmt_monthly;
    }

    public String getGoal_occurrence() {
        return goal_occurrence;
    }

    public void setGoal_occurrence(String goal_occurrence) {
        this.goal_occurrence = goal_occurrence;
    }

    public String getComm_per() {
        return comm_per;
    }

    public void setComm_per(String comm_per) {
        this.comm_per = comm_per;
    }

    @Override
    public String toString() {
       // Log.e("viswa_total_value", "ClassPojo [comm_pmt_yearly = " + comm_pmt_yearly + ", notes = " + notes + ", comm_annu_due_pv = " + comm_annu_due_pv + ", goal_cat_lev1_id = " + goal_cat_lev1_id + ", goal_years = " + goal_years + ", debt_annu_due_pv = " + debt_annu_due_pv + ", created_datetime = " + created_datetime + ", lev1_exp_inc = " + lev1_exp_inc + ", debt_recommended_products = " + debt_recommended_products + ", goal_name = " + goal_name + ", comm_pmt_lumpsum = " + comm_pmt_lumpsum + ", real_estate_recommended_products = " + real_estate_recommended_products + ", lev3_exp_inc = " + lev3_exp_inc + ", id = " + id + ", liquid_per = " + liquid_per + ", goal_priority = " + goal_priority + ", debt_fv = " + debt_fv + ", equity_recommended_products = " + equity_recommended_products + ", real_estate_pmt_yearly = " + real_estate_pmt_yearly + ", equity_pmt_yearly = " + equity_pmt_yearly + ", comm_recommended_products = " + comm_recommended_products + ", goal_cat_lev2_id = " + goal_cat_lev2_id + ", liquid_annu_due_pv = " + liquid_annu_due_pv + ", real_estate_annu_due_pv = " + real_estate_annu_due_pv + ", debt_pmt_lumpsum = " + debt_pmt_lumpsum + ", real_estate_pmt_monthly = " + real_estate_pmt_monthly + ", liquid_recommended_products = " + liquid_recommended_products + ", expected_increment = " + expected_increment + ", cost_of_goal = " + cost_of_goal + ", goal_imp = " + goal_imp + ", user_id = " + user_id + ", goal_flexibility = " + goal_flexibility + ", goal_interval = " + goal_interval + ", goal_recurrence = " + goal_recurrence + ", goal_start_datetime = " + goal_start_datetime + ", modified_datetime = " + modified_datetime + ", liquid_pmt_yearly = " + liquid_pmt_yearly + ", real_estate_per = " + real_estate_per + ", status = " + status + ", liquid_fv = " + liquid_fv + ", other_category = " + other_category + ", belongs_to_id = " + belongs_to_id + ", comm_fv = " + comm_fv + ", goal_duration = " + goal_duration + ", recur_months = " + recur_months + ", equity_pmt_monthly = " + equity_pmt_monthly + ", goal_end_datetime = " + goal_end_datetime + ", equity_pmt_lumpsum = " + equity_pmt_lumpsum + ", real_estate_fv = " + real_estate_fv + ", goal_cat_lev3_id = " + goal_cat_lev3_id + ", lev2_exp_inc = " + lev2_exp_inc + ", goal_frequency = " + goal_frequency + ", debt_pmt_monthly = " + debt_pmt_monthly + ", liquid_pmt_lumpsum = " + liquid_pmt_lumpsum + ", real_estate_pmt_lumpsum = " + real_estate_pmt_lumpsum + ", debt_per = " + debt_per + ", goal_accomp_datetime = " + goal_accomp_datetime + ", recur_years = " + recur_years + ", equity_annu_due_pv = " + equity_annu_due_pv + ", equity_per = " + equity_per + ", liquid_pmt_monthly = " + liquid_pmt_monthly + ", debt_pmt_yearly = " + debt_pmt_yearly + ", equity_fv = " + equity_fv + ", comm_pmt_monthly = " + comm_pmt_monthly + ", goal_occurrence = " + goal_occurrence + ", comm_per = " + comm_per + "]");
        return "ClassPojo [comm_pmt_yearly = " + comm_pmt_yearly + ", notes = " + notes + ", comm_annu_due_pv = " + comm_annu_due_pv + ", goal_cat_lev1_id = " + goal_cat_lev1_id + ", goal_years = " + goal_years + ", debt_annu_due_pv = " + debt_annu_due_pv + ", created_datetime = " + created_datetime + ", lev1_exp_inc = " + lev1_exp_inc + ", debt_recommended_products = " + debt_recommended_products + ", goal_name = " + goal_name + ", comm_pmt_lumpsum = " + comm_pmt_lumpsum + ", real_estate_recommended_products = " + real_estate_recommended_products + ", lev3_exp_inc = " + lev3_exp_inc + ", id = " + id + ", liquid_per = " + liquid_per + ", goal_priority = " + goal_priority + ", debt_fv = " + debt_fv + ", equity_recommended_products = " + equity_recommended_products + ", real_estate_pmt_yearly = " + real_estate_pmt_yearly + ", equity_pmt_yearly = " + equity_pmt_yearly + ", comm_recommended_products = " + comm_recommended_products + ", goal_cat_lev2_id = " + goal_cat_lev2_id + ", liquid_annu_due_pv = " + liquid_annu_due_pv + ", real_estate_annu_due_pv = " + real_estate_annu_due_pv + ", debt_pmt_lumpsum = " + debt_pmt_lumpsum + ", real_estate_pmt_monthly = " + real_estate_pmt_monthly + ", liquid_recommended_products = " + liquid_recommended_products + ", expected_increment = " + expected_increment + ", cost_of_goal = " + cost_of_goal + ", goal_imp = " + goal_imp + ", user_id = " + user_id + ", goal_flexibility = " + goal_flexibility + ", goal_interval = " + goal_interval + ", goal_recurrence = " + goal_recurrence + ", goal_start_datetime = " + goal_start_datetime + ", modified_datetime = " + modified_datetime + ", liquid_pmt_yearly = " + liquid_pmt_yearly + ", real_estate_per = " + real_estate_per + ", status = " + status + ", liquid_fv = " + liquid_fv + ", other_category = " + other_category + ", belongs_to_id = " + belongs_to_id + ", comm_fv = " + comm_fv + ", goal_duration = " + goal_duration + ", recur_months = " + recur_months + ", equity_pmt_monthly = " + equity_pmt_monthly + ", goal_end_datetime = " + goal_end_datetime + ", equity_pmt_lumpsum = " + equity_pmt_lumpsum + ", real_estate_fv = " + real_estate_fv + ", goal_cat_lev3_id = " + goal_cat_lev3_id + ", lev2_exp_inc = " + lev2_exp_inc + ", goal_frequency = " + goal_frequency + ", debt_pmt_monthly = " + debt_pmt_monthly + ", liquid_pmt_lumpsum = " + liquid_pmt_lumpsum + ", real_estate_pmt_lumpsum = " + real_estate_pmt_lumpsum + ", debt_per = " + debt_per + ", goal_accomp_datetime = " + goal_accomp_datetime + ", recur_years = " + recur_years + ", equity_annu_due_pv = " + equity_annu_due_pv + ", equity_per = " + equity_per + ", liquid_pmt_monthly = " + liquid_pmt_monthly + ", debt_pmt_yearly = " + debt_pmt_yearly + ", equity_fv = " + equity_fv + ", comm_pmt_monthly = " + comm_pmt_monthly + ", goal_occurrence = " + goal_occurrence + ", comm_per = " + comm_per + "]";
     }
}

