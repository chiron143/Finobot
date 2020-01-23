package com.purplepath.purplepath.AppManagement.Quiz.model;

import java.io.Serializable;

/**
 * Created by pravinr on 6/14/17.
 */

public class Quiz implements Serializable {
    private String quiz_op2;

    private String quiz_topic;

    private String quiz_op1;

    private String quiz_ans;

    private String quiz_que;

    private String quiz_id;

    private String created_datetime;

    private String modified_datetime;

    private String quiz_op4;

    private String quiz_op3;

    public String getQuiz_op2 ()
    {
        return quiz_op2;
    }

    public void setQuiz_op2 (String quiz_op2)
    {
        this.quiz_op2 = quiz_op2;
    }

    public String getQuiz_topic ()
    {
        return quiz_topic;
    }

    public void setQuiz_topic (String quiz_topic)
    {
        this.quiz_topic = quiz_topic;
    }

    public String getQuiz_op1 ()
    {
        return quiz_op1;
    }

    public void setQuiz_op1 (String quiz_op1)
    {
        this.quiz_op1 = quiz_op1;
    }

    public String getQuiz_ans ()
    {
        return quiz_ans;
    }

    public void setQuiz_ans (String quiz_ans)
    {
        this.quiz_ans = quiz_ans;
    }

    public String getQuiz_que ()
    {
        return quiz_que;
    }

    public void setQuiz_que (String quiz_que)
    {
        this.quiz_que = quiz_que;
    }

    public String getQuiz_id ()
    {
        return quiz_id;
    }

    public void setQuiz_id (String quiz_id)
    {
        this.quiz_id = quiz_id;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getQuiz_op4 ()
    {
        return quiz_op4;
    }

    public void setQuiz_op4 (String quiz_op4)
    {
        this.quiz_op4 = quiz_op4;
    }

    public String getQuiz_op3 ()
    {
        return quiz_op3;
    }

    public void setQuiz_op3 (String quiz_op3)
    {
        this.quiz_op3 = quiz_op3;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [quiz_op2 = "+quiz_op2+", quiz_topic = "+quiz_topic+", quiz_op1 = "+quiz_op1+", quiz_ans = "+quiz_ans+", quiz_que = "+quiz_que+", quiz_id = "+quiz_id+", created_datetime = "+created_datetime+", modified_datetime = "+modified_datetime+", quiz_op4 = "+quiz_op4+", quiz_op3 = "+quiz_op3+"]";
    }
}

