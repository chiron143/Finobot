package com.purplepath.purplepath.AppManagement.Survey.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by marut on 18-08-2017.
 */

public class Surveytodata implements Serializable {

    private String message;


    public Surveyto input;


    public Surveyto getSurid() {
        return input;
    }

    public void setQuiz(Surveyto quiz) {
        input = quiz;
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
        return "ClassPojo [message = "+message+", Quiz = "+input+"]";
    }
}


