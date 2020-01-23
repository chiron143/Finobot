package com.purplepath.purplepath.model.homeCardModel;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 19-09-2017.
 */

public class Card1 implements Serializable {
    private String text;

    private String date;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [text = "+text+", date = "+date+"]";
    }
}

