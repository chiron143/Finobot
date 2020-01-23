package com.purplepath.purplepath.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GoalArray {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("goal_name")
    @Expose
    private String goalName;
    @SerializedName("goal_years")
    @Expose
    private String goalYears;
    @SerializedName("goal_duration")
    @Expose
    private String goalDuration;
    @SerializedName("expected_increment")
    @Expose
    private String expectedIncrement;
    @SerializedName("cost_of_goal")
    @Expose
    private String costOfGoal;
    @SerializedName("goal_imp")
    @Expose
    private String goalImp;
    @SerializedName("goal_priority")
    @Expose
    private String goalPriority;
    @SerializedName("goal_flexibility")
    @Expose
    private String goalFlexibility;
    @SerializedName("goal_future_value")
    @Expose
    private Double goalFutureValue;
    @SerializedName("invest_array")
    @Expose
    private List<InvestArray> investArray = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalYears() {
        return goalYears;
    }

    public void setGoalYears(String goalYears) {
        this.goalYears = goalYears;
    }

    public String getGoalDuration() {
        return goalDuration;
    }

    public void setGoalDuration(String goalDuration) {
        this.goalDuration = goalDuration;
    }

    public String getExpectedIncrement() {
        return expectedIncrement;
    }

    public void setExpectedIncrement(String expectedIncrement) {
        this.expectedIncrement = expectedIncrement;
    }

    public String getCostOfGoal() {
        return costOfGoal;
    }

    public void setCostOfGoal(String costOfGoal) {
        this.costOfGoal = costOfGoal;
    }

    public String getGoalImp() {
        return goalImp;
    }

    public void setGoalImp(String goalImp) {
        this.goalImp = goalImp;
    }

    public String getGoalPriority() {
        return goalPriority;
    }

    public void setGoalPriority(String goalPriority) {
        this.goalPriority = goalPriority;
    }

    public String getGoalFlexibility() {
        return goalFlexibility;
    }

    public void setGoalFlexibility(String goalFlexibility) {
        this.goalFlexibility = goalFlexibility;
    }

    public Double getGoalFutureValue() {
        return goalFutureValue;
    }

    public void setGoalFutureValue(Double goalFutureValue) {
        this.goalFutureValue = goalFutureValue;
    }

    public List<InvestArray> getInvestArray() {
        return investArray;
    }

    public void setInvestArray(List<InvestArray> investArray) {
        this.investArray = investArray;
    }


}
