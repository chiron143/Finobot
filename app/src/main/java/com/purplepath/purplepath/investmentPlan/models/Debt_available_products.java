package com.purplepath.purplepath.investmentPlan.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Debt_available_products implements Serializable {
        private ArrayList<IPS_Bank> bank;

        private ArrayList<IPS_Postoffice> post_office;

        public ArrayList<IPS_Mutual_found> getMutual_funds() {
            return mutual_funds;
        }

        public void setMutual_funds(ArrayList<IPS_Mutual_found> mutual_funds) {
            this.mutual_funds = mutual_funds;
        }

        private ArrayList<IPS_Mutual_found> mutual_funds;

        public ArrayList<IPS_Bank> getBank() {
            return bank;
        }

        public void setBank(ArrayList<IPS_Bank> bank) {
            this.bank = bank;
        }

        public ArrayList<IPS_Postoffice> getPost_office() {
            return post_office;
        }

        public void setPost_office(ArrayList<IPS_Postoffice> post_office) {
            this.post_office = post_office;
        }

        @Override
        public String toString() {
            return "ClassPojo [bank = " + bank + ", post_office = " + post_office + ", mutual_funds = " + mutual_funds + "]";
        }
}
