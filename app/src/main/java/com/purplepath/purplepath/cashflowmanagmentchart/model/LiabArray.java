package com.purplepath.purplepath.cashflowmanagmentchart.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LiabArray implements Serializable {


    private String credit_card;

    private String loan_offer;

    private String loan;

    private String ref_deposit;

    private String unpaid_bills;

    public String getCreditCard() {
        return credit_card;
    }

    public void setCreditCard(String credit_card) {
        this.credit_card = credit_card;
    }

    public String getLoanOffer() {
        return loan_offer;
    }

    public void setLoanOffer(String loan_offer) {
        this.loan_offer = loan_offer;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getRefDeposit() {
        return ref_deposit;
    }

    public void setRefDeposit(String ref_deposit) {
        this.ref_deposit = ref_deposit;
    }

    public String getUnpaidBills() {
        return unpaid_bills;
    }

    public void setUnpaidBills(String unpaid_bills) {
        this.unpaid_bills = unpaid_bills;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [credit_card = "+credit_card+", loan_offer = "+loan_offer+", loan = "+loan+", ref_deposit = "+ref_deposit+", unpaid_bills = "+unpaid_bills+"]";
    }

}
