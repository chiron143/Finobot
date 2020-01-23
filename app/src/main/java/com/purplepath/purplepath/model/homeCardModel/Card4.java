package com.purplepath.purplepath.model.homeCardModel;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 19-09-2017.
 */

public class Card4 implements Serializable {
    private String quote_person;

    private String quote_topic;

    private String quote;

    public String getQuote_person ()
    {
        return quote_person;
    }

    public void setQuote_person (String quote_person)
    {
        this.quote_person = quote_person;
    }

    public String getQuote_topic ()
    {
        return quote_topic;
    }

    public void setQuote_topic (String quote_topic)
    {
        this.quote_topic = quote_topic;
    }

    public String getQuote ()
    {
        return quote;
    }

    public void setQuote (String quote)
    {
        this.quote = quote;
    }
}
