package com.purplepath.purplepath.model.homeCardModel;

import java.io.Serializable;

/**
 * Created by pravinr on 1/25/18.
 */

public class Card0 implements Serializable{
    private String text;

    private String title;

    private String enable_flag;

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

    public String getEnable_flag ()
    {
        return enable_flag;
    }

    public void setEnable_flag (String enable_flag)
    {
        this.enable_flag = enable_flag;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [text = "+text+", title = "+title+", enable_flag = "+enable_flag+"]";
    }
}

