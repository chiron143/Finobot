package com.purplepath.purplepath.AppManagement.Quiz.model;

import com.purplepath.purplepath.AppManagement.FAQ.model.FAQ;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 6/14/17.
 */

public class Quizdata implements Serializable {
    private String message;

    //private Quiz[] Quiz;

    public ArrayList<Quiz>Quiz;


    public ArrayList<com.purplepath.purplepath.AppManagement.Quiz.model.Quiz> getQuiz() {
        return Quiz;
    }

    public void setQuiz(ArrayList<com.purplepath.purplepath.AppManagement.Quiz.model.Quiz> quiz) {
        Quiz = quiz;
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
        return "ClassPojo [message = "+message+", Quiz = "+Quiz+"]";
    }
}

