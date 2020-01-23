package com.purplepath.purplepath.AppManagement.Survey.model;

import com.purplepath.purplepath.AppManagement.FAQ.model.FAQ;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 6/14/17.
 */

public class Surveydata implements Serializable {
    private String message;

    //private Quiz[] Quiz;

    public ArrayList<Survey>surv_ques;


    public ArrayList<Survey> getQuiz() {
        return surv_ques;
    }

    public void setQuiz(ArrayList<Survey> quiz) {
        surv_ques = quiz;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

   /* public Quiz[] getQuiz ()
    {
        return Quiz;
    }

    public void setQuiz (Quiz[] Quiz)
    {
        this.Quiz = Quiz;
    }*/

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", Quiz = "+surv_ques+"]";
    }
}

