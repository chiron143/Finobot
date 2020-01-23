package com.purplepath.purplepath.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InvestCashflow {

    @SerializedName("cash_age")
    @Expose
    private String cashAge;
    @SerializedName("years_pass")
    @Expose
    private Integer yearsPass;
    @SerializedName("years_remain")
    @Expose
    private Integer yearsRemain;
    @SerializedName("goal_array")
    @Expose
    private List<GoalArray> goalArray = null;
    @SerializedName("year_pass")
    @Expose
    private Integer yearPass;

    public String getCashAge() {
        return cashAge;
    }

    public void setCashAge(String cashAge) {
        this.cashAge = cashAge;
    }

    public Integer getYearsPass() {
        return yearsPass;
    }

    public void setYearsPass(Integer yearsPass) {
        this.yearsPass = yearsPass;
    }

    public Integer getYearsRemain() {
        return yearsRemain;
    }

    public void setYearsRemain(Integer yearsRemain) {
        this.yearsRemain = yearsRemain;
    }

    public List<GoalArray> getGoalArray() {
        return goalArray;
    }

    public void setGoalArray(List<GoalArray> goalArray) {
        this.goalArray = goalArray;
    }

    public Integer getYearPass() {
        return yearPass;
    }

    public void setYearPass(Integer yearPass) {
        this.yearPass = yearPass;
    }

}
