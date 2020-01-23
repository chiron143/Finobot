package com.purplepath.purplepath.taxfiling.TaxFilingViewPager.model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by pravinr on 8/10/18.
 */

public class Data implements Serializable{

    private String message;
    public HashMap<String, ArrayList<QuestionData>> overall_questions;

    private ArrayList<Tab_visited_status> tab_visited_status;

    public ArrayList<String> getAva_tab() {
        return ava_tab;
    }

    public void setAva_tab(ArrayList<String> ava_tab) {
        this.ava_tab = ava_tab;
    }

    private ArrayList<String> ava_tab;

    public ArrayList<Tab_visited_status> getTab_visited_status() {
        return tab_visited_status;
    }

    public void setTab_visited_status(ArrayList<Tab_visited_status> tab_visited_status) {
        this.tab_visited_status = tab_visited_status;
    }

    private Overall_questions overall_questionss;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }



    public Overall_questions getOverall_questions ()
    {
        return overall_questionss;
    }

    public void setOverall_questions (Overall_questions overall_questions)
    {
        this.overall_questionss = overall_questions;
    }

    public HashMap<String, ArrayList<QuestionData>> getKeyQuestion() {
        return overall_questions;
    }

    public void setKeyQuestion(HashMap<String, ArrayList<QuestionData>> keyQuestion) {
        this.overall_questions = keyQuestion;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", tab_visited_status = "+tab_visited_status+", overall_questions = "+overall_questions+"]";
    }
}

