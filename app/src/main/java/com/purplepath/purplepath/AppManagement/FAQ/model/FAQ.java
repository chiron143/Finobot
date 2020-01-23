package com.purplepath.purplepath.AppManagement.FAQ.model;

import java.io.Serializable;

/**
 * Created by pravinr on 5/17/17.
 */

public class FAQ implements Serializable {
    private String category;

    private String faq_id;

    private String created_datetime;

    private String faq_ans;

    private String faq_que;

    private String modified_datetime;

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getFaq_id ()
    {
        return faq_id;
    }

    public void setFaq_id (String faq_id)
    {
        this.faq_id = faq_id;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getFaq_ans ()
    {
        return faq_ans;
    }

    public void setFaq_ans (String faq_ans)
    {
        this.faq_ans = faq_ans;
    }

    public String getFaq_que ()
    {
        return faq_que;
    }

    public void setFaq_que (String faq_que)
    {
        this.faq_que = faq_que;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [category = "+category+", faq_id = "+faq_id+", created_datetime = "+created_datetime+", faq_ans = "+faq_ans+", faq_que = "+faq_que+", modified_datetime = "+modified_datetime+"]";
    }
}
