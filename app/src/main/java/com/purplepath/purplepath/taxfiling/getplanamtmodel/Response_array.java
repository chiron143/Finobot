package com.purplepath.purplepath.taxfiling.getplanamtmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 6/22/18.
 */

public class Response_array implements Serializable {

    private String form16_issued;

    private String house_property_multi_houses;

    private String agri_income_less_5k;

    private String non_form16;

    private String agri_income_gt_5k;

    private String id;

    private String first_time_filing;

    private String prev_year_filing;

    private String gross_tot_inc;

    private String clubbing_income;

    private String income_gt_50l;

    private String loss_house_property;

    private String house_property_single_house;

    private String created_datetime;

    private String capital_gains;

    private String income_less_50l;

    private String foreign_assets;

    private String plan_amount;

    private String no_of_employers;

    private String user_id;

    private String self_employed;

    private String modified_datetime;

    private String int_divd;

    private String loss_capital_gains;

    public String getForm16_issued ()
    {
        return form16_issued;
    }

    public void setForm16_issued (String form16_issued)
    {
        this.form16_issued = form16_issued;
    }

    public String getHouse_property_multi_houses ()
    {
        return house_property_multi_houses;
    }

    public void setHouse_property_multi_houses (String house_property_multi_houses)
    {
        this.house_property_multi_houses = house_property_multi_houses;
    }

    public String getAgri_income_less_5k ()
    {
        return agri_income_less_5k;
    }

    public void setAgri_income_less_5k (String agri_income_less_5k)
    {
        this.agri_income_less_5k = agri_income_less_5k;
    }

    public String getNon_form16 ()
    {
        return non_form16;
    }

    public void setNon_form16 (String non_form16)
    {
        this.non_form16 = non_form16;
    }

    public String getAgri_income_gt_5k ()
    {
        return agri_income_gt_5k;
    }

    public void setAgri_income_gt_5k (String agri_income_gt_5k)
    {
        this.agri_income_gt_5k = agri_income_gt_5k;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getFirst_time_filing ()
    {
        return first_time_filing;
    }

    public void setFirst_time_filing (String first_time_filing)
    {
        this.first_time_filing = first_time_filing;
    }

    public String getPrev_year_filing ()
    {
        return prev_year_filing;
    }

    public void setPrev_year_filing (String prev_year_filing)
    {
        this.prev_year_filing = prev_year_filing;
    }

    public String getGross_tot_inc ()
    {
        return gross_tot_inc;
    }

    public void setGross_tot_inc (String gross_tot_inc)
    {
        this.gross_tot_inc = gross_tot_inc;
    }

    public String getClubbing_income ()
    {
        return clubbing_income;
    }

    public void setClubbing_income (String clubbing_income)
    {
        this.clubbing_income = clubbing_income;
    }

    public String getIncome_gt_50l ()
    {
        return income_gt_50l;
    }

    public void setIncome_gt_50l (String income_gt_50l)
    {
        this.income_gt_50l = income_gt_50l;
    }

    public String getLoss_house_property ()
    {
        return loss_house_property;
    }

    public void setLoss_house_property (String loss_house_property)
    {
        this.loss_house_property = loss_house_property;
    }

    public String getHouse_property_single_house ()
    {
        return house_property_single_house;
    }

    public void setHouse_property_single_house (String house_property_single_house)
    {
        this.house_property_single_house = house_property_single_house;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getCapital_gains ()
    {
        return capital_gains;
    }

    public void setCapital_gains (String capital_gains)
    {
        this.capital_gains = capital_gains;
    }

    public String getIncome_less_50l ()
    {
        return income_less_50l;
    }

    public void setIncome_less_50l (String income_less_50l)
    {
        this.income_less_50l = income_less_50l;
    }

    public String getForeign_assets ()
    {
        return foreign_assets;
    }

    public void setForeign_assets (String foreign_assets)
    {
        this.foreign_assets = foreign_assets;
    }

    public String getPlan_amount ()
    {
        return plan_amount;
    }

    public void setPlan_amount (String plan_amount)
    {
        this.plan_amount = plan_amount;
    }

    public String getNo_of_employers ()
    {
        return no_of_employers;
    }

    public void setNo_of_employers (String no_of_employers)
    {
        this.no_of_employers = no_of_employers;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getSelf_employed ()
    {
        return self_employed;
    }

    public void setSelf_employed (String self_employed)
    {
        this.self_employed = self_employed;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getInt_divd ()
    {
        return int_divd;
    }

    public void setInt_divd (String int_divd)
    {
        this.int_divd = int_divd;
    }

    public String getLoss_capital_gains ()
    {
        return loss_capital_gains;
    }

    public void setLoss_capital_gains (String loss_capital_gains)
    {
        this.loss_capital_gains = loss_capital_gains;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [form16_issued = "+form16_issued+", house_property_multi_houses = "+house_property_multi_houses+", agri_income_less_5k = "+agri_income_less_5k+", non_form16 = "+non_form16+", agri_income_gt_5k = "+agri_income_gt_5k+", id = "+id+", first_time_filing = "+first_time_filing+", prev_year_filing = "+prev_year_filing+", gross_tot_inc = "+gross_tot_inc+", clubbing_income = "+clubbing_income+", income_gt_50l = "+income_gt_50l+", loss_house_property = "+loss_house_property+", house_property_single_house = "+house_property_single_house+", created_datetime = "+created_datetime+", capital_gains = "+capital_gains+", income_less_50l = "+income_less_50l+", foreign_assets = "+foreign_assets+", plan_amount = "+plan_amount+", no_of_employers = "+no_of_employers+", user_id = "+user_id+", self_employed = "+self_employed+", modified_datetime = "+modified_datetime+", int_divd = "+int_divd+", loss_capital_gains = "+loss_capital_gains+"]";
    }
}

