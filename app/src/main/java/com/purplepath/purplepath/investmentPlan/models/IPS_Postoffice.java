package com.purplepath.purplepath.investmentPlan.models;

import java.io.Serializable;

public class IPS_Postoffice implements Serializable {

        private String id;
        private String asset_class;
        private String asset_sub_class;
        private String product_name;
        private String investment_period;
        private String stated_interest;
        private String effective_date = null;
        private String compounding_period;
        private String effective_interest;
        private String tax_benefit = null;
        private String age = null;
        private String created_datetime;
        private String modified_datetime;


        // Getter Methods

        public String getId() {
            return id;
        }

        public String getAsset_class() {
            return asset_class;
        }

        public String getAsset_sub_class() {
            return asset_sub_class;
        }

        public String getProduct_name() {
            return product_name;
        }

        public String getInvestment_period() {
            return investment_period;
        }

        public String getStated_interest() {
            return stated_interest;
        }

        public String getEffective_date() {
            return effective_date;
        }

        public String getCompounding_period() {
            return compounding_period;
        }

        public String getEffective_interest() {
            return effective_interest;
        }

        public String getTax_benefit() {
            return tax_benefit;
        }

        public String getAge() {
            return age;
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

        public void setAsset_class(String asset_class) {
            this.asset_class = asset_class;
        }

        public void setAsset_sub_class(String asset_sub_class) {
            this.asset_sub_class = asset_sub_class;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public void setInvestment_period(String investment_period) {
            this.investment_period = investment_period;
        }

        public void setStated_interest(String stated_interest) {
            this.stated_interest = stated_interest;
        }

        public void setEffective_date(String effective_date) {
            this.effective_date = effective_date;
        }

        public void setCompounding_period(String compounding_period) {
            this.compounding_period = compounding_period;
        }

        public void setEffective_interest(String effective_interest) {
            this.effective_interest = effective_interest;
        }

        public void setTax_benefit(String tax_benefit) {
            this.tax_benefit = tax_benefit;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public void setCreated_datetime(String created_datetime) {
            this.created_datetime = created_datetime;
        }

        public void setModified_datetime(String modified_datetime) {
            this.modified_datetime = modified_datetime;
        }
    }