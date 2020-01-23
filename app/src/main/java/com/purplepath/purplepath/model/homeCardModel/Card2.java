package com.purplepath.purplepath.model.homeCardModel;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 19-09-2017.
 */

public class Card2 implements Serializable {

    private String text;

    private String title;

    public String getText ()
    {
        return text;
    }

    public void setText (String text)
    {
        this.text = text;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }
}
