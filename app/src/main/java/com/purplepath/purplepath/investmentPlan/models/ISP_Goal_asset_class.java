package com.purplepath.purplepath.investmentPlan.models;

import java.io.Serializable;
import java.util.ArrayList;

public class ISP_Goal_asset_class implements Serializable {

    private ArrayList<IPS_Results> equity_goals;
    private ArrayList<IPS_Results> debt_goals;
    private ArrayList<IPS_Results> liquid_goals;
    private ArrayList<IPS_Results> comm_goals;
    private ArrayList<IPS_Results> real_estate_goals;

    public ArrayList<IPS_Results> getEquity_goals() {
        return equity_goals;
    }

    public void setEquity_goals(ArrayList<IPS_Results> equity_goals) {
        this.equity_goals = equity_goals;
    }

    public ArrayList<IPS_Results> getDebt_goals() {
        return debt_goals;
    }

    public void setDebt_goals(ArrayList<IPS_Results> debt_goals) {
        this.debt_goals = debt_goals;
    }

    public ArrayList<IPS_Results> getLiquid_goals() {
        return liquid_goals;
    }

    public void setLiquid_goals(ArrayList<IPS_Results> liquid_goals) {
        this.liquid_goals = liquid_goals;
    }

    public ArrayList<IPS_Results> getComm_goals() {
        return comm_goals;
    }

    public void setComm_goals(ArrayList<IPS_Results> comm_goals) {
        this.comm_goals = comm_goals;
    }

    public ArrayList<IPS_Results> getReal_estate_goals() {
        return real_estate_goals;
    }

    public void setReal_estate_goals(ArrayList<IPS_Results> real_estate_goals) {
        this.real_estate_goals = real_estate_goals;
    }

}
