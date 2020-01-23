package com.purplepath.purplepath.investmentPlan.models;

import java.util.ArrayList;
import java.util.Date;

public class IPS_Sum {

    public ArrayList<Integer> getStart_date_s() {
        return start_date_s;
    }

    public void setStart_date_s(ArrayList<Integer> start_date_s) {
        this.start_date_s = start_date_s;
    }

    public ArrayList<Integer> getEnd_date_s() {
        return end_date_s;
    }

    public void setEnd_date_s(ArrayList<Integer> end_date_s) {
        this.end_date_s = end_date_s;
    }

    private ArrayList<Integer> start_date_s = new ArrayList<>();

    public ArrayList<String> getDate_start() {
        return date_start;
    }

    public void setDate_start(ArrayList<String> date_start) {
        this.date_start = date_start;
    }

    public ArrayList<String> getDate_end() {
        return date_end;
    }

    public void setDate_end(ArrayList<String> date_end) {
        this.date_end = date_end;
    }

    private ArrayList<String> date_start = new ArrayList<>();
    private ArrayList<String> date_end = new ArrayList<>();


    private ArrayList<Integer> end_date_s = new ArrayList<>();
    private double current_value = 0;
    private double target_value = 0;

    public double getAnnual_con() {
        return annual_con;
    }

    public void setAnnual_con(double annual_con) {
        this.annual_con = annual_con;
    }

    private double annual_con = 0;
    private String key = "";
    private String title = "";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type = "";

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code = "";


    private double si_total = 0;
    private double ip_total = 0;
    private double ib_total = 0;
    private double cg_total = 0;
    private double ifs_total = 0;


    public double getSi_total() {
        return si_total;
    }

    public void setSi_total(double si_total) {
        this.si_total = si_total;
    }

    public double getIp_total() {
        return ip_total;
    }

    public void setIp_total(double ip_total) {
        this.ip_total = ip_total;
    }

    public double getIb_total() {
        return ib_total;
    }

    public void setIb_total(double ib_total) {
        this.ib_total = ib_total;
    }

    public double getCg_total() {
        return cg_total;
    }

    public void setCg_total(double cg_total) {
        this.cg_total = cg_total;
    }

    public double getIfs_total() {
        return ifs_total;
    }

    public void setIfs_total(double ifs_total) {
        this.ifs_total = ifs_total;
    }


    public int getDiv() {
        return div;
    }

    public void setDiv(int div) {
        this.div = div;
    }

    private int div = 1;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double getCurrent_value() {
        return current_value;
    }

    public void setCurrent_value(double current_value) {
        this.current_value = current_value;
    }

    public double getTarget_value() {
        return target_value;
    }

    public void setTarget_value(double target_value) {
        this.target_value = target_value;
    }


}
