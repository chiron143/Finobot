package com.purplepath.purplepath.AppManagement.Quiz.topicModels;

import java.io.Serializable;

/**
 * Created by Suresh on 05/09/17.
 */

public class Quiz_topics  implements Serializable {

    private String quiz_topic;

    public String getQuiz_topic ()
    {
        return quiz_topic;
    }

    public void setQuiz_topic (String quiz_topic)
    {
        this.quiz_topic = quiz_topic;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [quiz_topic = "+quiz_topic+"]";
    }
}
