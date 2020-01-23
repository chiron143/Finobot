package com.purplepath.purplepath.AppManagement.Quiz.topicModels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Suresh on 05/09/17.
 */

public class Data  implements Serializable {

    private String message;

//    private Quiz_topics[] quiz_topics;


    private ArrayList<Quiz_topics> quiz_topics;


    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Quiz_topics> getQuiz_topics() {
        return quiz_topics;
    }

    public void setQuiz_topics(ArrayList<Quiz_topics> quiz_topics) {
        this.quiz_topics = quiz_topics;
    }
//    public Quiz_topics[] getQuiz_topics ()
//    {
//        return quiz_topics;
//    }
//
//    public void setQuiz_topics (Quiz_topics[] quiz_topics)
//    {
//        this.quiz_topics = quiz_topics;
//    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", quiz_topics = "+quiz_topics+"]";
    }
}
