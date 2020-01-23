package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by Bert on 18-Jun-16.
 */
public class GoalDetailsModel implements Serializable {

        private String goal_cat_lev3_id;

        private String goal_cat_lev1_id;

        private String goal_recurrence;

        private String other_category;

        private String goal_priority;

        private String recur_months;

        private String goal_end_date;

        private String goal_years;

        private String cost_of_goal;

        private String goal_flexibility;

        private String goal_imp;

        private String goal_cat_lev2_id;

        private String belongs_to_id;

        private String expected_increment;

        private String goal_name;

        private String goal_frequency;

        private String user_id;

        private String notes;

        private String recur_years;

        private String goal_start_date;

        private String goal_interval;

    public String getGoal_interval() {
        return goal_interval;
    }

    public void setGoal_interval(String goal_interval) {
        this.goal_interval = goal_interval;
    }

    public String getGoal_cat_lev3_id ()
        {
            return goal_cat_lev3_id;
        }

        public void setGoal_cat_lev3_id (String goal_cat_lev3_id)
        {
            this.goal_cat_lev3_id = goal_cat_lev3_id;
        }

        public String getGoal_cat_lev1_id ()
        {
            return goal_cat_lev1_id;
        }

        public void setGoal_cat_lev1_id (String goal_cat_lev1_id)
        {
            this.goal_cat_lev1_id = goal_cat_lev1_id;
        }

        public String getGoal_recurrence ()
        {
            return goal_recurrence;
        }

        public void setGoal_recurrence (String goal_recurrence)
        {
            this.goal_recurrence = goal_recurrence;
        }

        public String getOther_category ()
        {
            return other_category;
        }

        public void setOther_category (String other_category)
        {
            this.other_category = other_category;
        }

        public String getGoal_priority ()
        {
            return goal_priority;
        }

        public void setGoal_priority (String goal_priority)
        {
            this.goal_priority = goal_priority;
        }

        public String getRecur_months ()
        {
            return recur_months;
        }

        public void setRecur_months (String recur_months)
        {
            this.recur_months = recur_months;
        }

        public String getGoal_end_date ()
        {
            return goal_end_date;
        }

        public void setGoal_end_date (String goal_end_date)
        {
            this.goal_end_date = goal_end_date;
        }

        public String getGoal_years ()
        {
            return goal_years;
        }

        public void setGoal_years (String goal_years)
        {
            this.goal_years = goal_years;
        }

        public String getCost_of_goal ()
        {
            return cost_of_goal;
        }

        public void setCost_of_goal (String cost_of_goal)
        {
            this.cost_of_goal = cost_of_goal;
        }

        public String getGoal_flexibility ()
        {
            return goal_flexibility;
        }

        public void setGoal_flexibility (String goal_flexibility)
        {
            this.goal_flexibility = goal_flexibility;
        }

        public String getGoal_imp ()
        {
            return goal_imp;
        }

        public void setGoal_imp (String goal_imp)
        {
            this.goal_imp = goal_imp;
        }

        public String getGoal_cat_lev2_id ()
        {
            return goal_cat_lev2_id;
        }

        public void setGoal_cat_lev2_id (String goal_cat_lev2_id)
        {
            this.goal_cat_lev2_id = goal_cat_lev2_id;
        }

        public String getBelongs_to_id ()
        {
            return belongs_to_id;
        }

        public void setBelongs_to_id (String belongs_to_id)
        {
            this.belongs_to_id = belongs_to_id;
        }

        public String getExpected_increment ()
        {
            return expected_increment;
        }

        public void setExpected_increment (String expected_increment)
        {
            this.expected_increment = expected_increment;
        }

        public String getGoal_name ()
        {
            return goal_name;
        }

        public void setGoal_name (String goal_name)
        {
            this.goal_name = goal_name;
        }

        public String getGoal_frequency ()
        {
            return goal_frequency;
        }

        public void setGoal_frequency (String goal_frequency)
        {
            this.goal_frequency = goal_frequency;
        }

        public String getUser_id ()
        {
            return user_id;
        }

        public void setUser_id (String user_id)
        {
            this.user_id = user_id;
        }

        public String getNotes ()
        {
            return notes;
        }

        public void setNotes (String notes)
        {
            this.notes = notes;
        }

        public String getRecur_years ()
        {
            return recur_years;
        }

        public void setRecur_years (String recur_years)
        {
            this.recur_years = recur_years;
        }

        public String getGoal_start_date ()
        {
            return goal_start_date;
        }

        public void setGoal_start_date (String goal_start_date)
        {
            this.goal_start_date = goal_start_date;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [goal_cat_lev3_id = "+goal_cat_lev3_id+"," +
                    " goal_cat_lev1_id = "+goal_cat_lev1_id+", " +
                    "goal_recurrence = "+goal_recurrence+"," +
                    " other_category = "+other_category+", goal_priority = "+goal_priority+"," +
                    " recur_months = "+recur_months+", goal_end_date = "+goal_end_date+", " +
                    "goal_years = "+goal_years+", cost_of_goal = "+cost_of_goal+", " +
                    "goal_flexibility = "+goal_flexibility+"," +
                    " goal_imp = "+goal_imp+", goal_cat_lev2_id = "+goal_cat_lev2_id+", " +
                    "belongs_to_id = "+belongs_to_id+", expected_increment = "+expected_increment+", " +
                    "goal_name = "+goal_name+", goal_frequency = "+goal_frequency+", user_id = "+user_id+", " +
                    "notes = "+notes+", recur_years =" +
                    " "+recur_years+", goal_start_date = "+goal_start_date+", goal_interval = "+goal_interval+"]";


        }
}


