package com.purplepath.purplepath.investmentPlan.models;

import java.io.Serializable;

public class IPS_Mutual_found implements Serializable {

    private String id;
    private String fund_house;
    private String scheme_code;
    private String scheme_name;
    private String scheme_type;
    private String scheme_category;
    private String scheme_sub_category;
    private String fund_type;
    private String scheme_nav_name;
    private String plan_type;
    private String option;
    private String sub_option;
    private String state;
    private String pub_risk_meter = null;
    private String inhouse_risk_meter = null;
    private String inhouse_recomm;
    private String launch_date;
    private String closure_date;
    private String min_invst_amt;
    private String min_add_purch_amt = null;
    private String min_sip_inst_amt = null;
    private String issue_close_date = null;
    private String maturity_date = null;
    private String lock_in_period;
    private String bench_mark;
    private String fund_mgr;
    private String aum;
    private String rating = null;
    private String created_datetime;
    private String modified_datetime;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnnual_contr() {
        return annual_contr;
    }

    public void setAnnual_contr(String annual_contr) {
        this.annual_contr = annual_contr;
    }

    private String annual_contr;


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getFund_house() {
        return fund_house;
    }

    public String getScheme_code() {
        return scheme_code;
    }

    public String getScheme_name() {
        return scheme_name;
    }

    public String getScheme_type() {
        return scheme_type;
    }

    public String getScheme_category() {
        return scheme_category;
    }

    public String getScheme_sub_category() {
        return scheme_sub_category;
    }

    public String getFund_type() {
        return fund_type;
    }

    public String getScheme_nav_name() {
        return scheme_nav_name;
    }

    public String getPlan_type() {
        return plan_type;
    }

    public String getOption() {
        return option;
    }

    public String getSub_option() {
        return sub_option;
    }

    public String getState() {
        return state;
    }

    public String getPub_risk_meter() {
        return pub_risk_meter;
    }

    public String getInhouse_risk_meter() {
        return inhouse_risk_meter;
    }

    public String getInhouse_recomm() {
        return inhouse_recomm;
    }

    public String getLaunch_date() {
        return launch_date;
    }

    public String getClosure_date() {
        return closure_date;
    }

    public String getMin_invst_amt() {
        return min_invst_amt;
    }

    public String getMin_add_purch_amt() {
        return min_add_purch_amt;
    }

    public String getMin_sip_inst_amt() {
        return min_sip_inst_amt;
    }

    public String getIssue_close_date() {
        return issue_close_date;
    }

    public String getMaturity_date() {
        return maturity_date;
    }

    public String getLock_in_period() {
        return lock_in_period;
    }

    public String getBench_mark() {
        return bench_mark;
    }

    public String getFund_mgr() {
        return fund_mgr;
    }

    public String getAum() {
        return aum;
    }

    public String getRating() {
        return rating;
    }

    public String getCreated_datetime() {
        return created_datetime;
    }

    public String getModified_datetime() {
        return modified_datetime;
    }

    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setFund_house(String fund_house) {
        this.fund_house = fund_house;
    }

    public void setScheme_code(String scheme_code) {
        this.scheme_code = scheme_code;
    }

    public void setScheme_name(String scheme_name) {
        this.scheme_name = scheme_name;
    }

    public void setScheme_type(String scheme_type) {
        this.scheme_type = scheme_type;
    }

    public void setScheme_category(String scheme_category) {
        this.scheme_category = scheme_category;
    }

    public void setScheme_sub_category(String scheme_sub_category) {
        this.scheme_sub_category = scheme_sub_category;
    }

    public void setFund_type(String fund_type) {
        this.fund_type = fund_type;
    }

    public void setScheme_nav_name(String scheme_nav_name) {
        this.scheme_nav_name = scheme_nav_name;
    }

    public void setPlan_type(String plan_type) {
        this.plan_type = plan_type;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setSub_option(String sub_option) {
        this.sub_option = sub_option;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setPub_risk_meter(String pub_risk_meter) {
        this.pub_risk_meter = pub_risk_meter;
    }

    public void setInhouse_risk_meter(String inhouse_risk_meter) {
        this.inhouse_risk_meter = inhouse_risk_meter;
    }

    public void setInhouse_recomm(String inhouse_recomm) {
        this.inhouse_recomm = inhouse_recomm;
    }

    public void setLaunch_date(String launch_date) {
        this.launch_date = launch_date;
    }

    public void setClosure_date(String closure_date) {
        this.closure_date = closure_date;
    }

    public void setMin_invst_amt(String min_invst_amt) {
        this.min_invst_amt = min_invst_amt;
    }

    public void setMin_add_purch_amt(String min_add_purch_amt) {
        this.min_add_purch_amt = min_add_purch_amt;
    }

    public void setMin_sip_inst_amt(String min_sip_inst_amt) {
        this.min_sip_inst_amt = min_sip_inst_amt;
    }

    public void setIssue_close_date(String issue_close_date) {
        this.issue_close_date = issue_close_date;
    }

    public void setMaturity_date(String maturity_date) {
        this.maturity_date = maturity_date;
    }

    public void setLock_in_period(String lock_in_period) {
        this.lock_in_period = lock_in_period;
    }

    public void setBench_mark(String bench_mark) {
        this.bench_mark = bench_mark;
    }

    public void setFund_mgr(String fund_mgr) {
        this.fund_mgr = fund_mgr;
    }

    public void setAum(String aum) {
        this.aum = aum;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setCreated_datetime(String created_datetime) {
        this.created_datetime = created_datetime;
    }

    public void setModified_datetime(String modified_datetime) {
        this.modified_datetime = modified_datetime;
    }
}