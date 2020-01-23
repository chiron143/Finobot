package com.purplepath.purplepath.investmentPlan.models;

 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ISP_Data implements Serializable {
    private String message;

    public ArrayList<String> getAsset_classes() {
        return asset_classes;
    }

    public void setAsset_classes(ArrayList<String> asset_classes) {
        this.asset_classes = asset_classes;
    }

    private ArrayList<String> asset_classes;

    public IPS_Income_details getIncome_details() {
        return income_details;
    }

    public void setIncome_details(IPS_Income_details income_details) {
        this.income_details = income_details;
    }

    private IPS_Income_details income_details;


    public HashMap<String, ArrayList<IPS_Results>> getGoals_with_asset_class() {
        return goals_with_asset_class;
    }

    public void setGoals_with_asset_class(HashMap<String, ArrayList<IPS_Results>> goals_with_asset_class) {
        this.goals_with_asset_class = goals_with_asset_class;
    }

    private HashMap<String, ArrayList<IPS_Results>> goals_with_asset_class;







    private ArrayList<IPS_Results> results;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<IPS_Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<IPS_Results> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ClassPojo [message = " + message + ", results = " + results + "]";
    }
}

