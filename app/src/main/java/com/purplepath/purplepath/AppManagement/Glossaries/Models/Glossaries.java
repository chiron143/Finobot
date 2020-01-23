package com.purplepath.purplepath.AppManagement.Glossaries.Models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 19-05-2017.
 */

public class Glossaries implements Serializable {

    private String gloss_description;

    private String created_datetime;

    private String gloss_word;

    private String modified_datetime;

    private String gloss_id;

    private String gloss_alphabet;

    public String getGloss_description ()
    {
        return gloss_description;
    }

    public void setGloss_description (String gloss_description)
    {
        this.gloss_description = gloss_description;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getGloss_word ()
    {
        return gloss_word;
    }

    public void setGloss_word (String gloss_word)
    {
        this.gloss_word = gloss_word;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getGloss_id ()
    {
        return gloss_id;
    }

    public void setGloss_id (String gloss_id)
    {
        this.gloss_id = gloss_id;
    }

    public String getGloss_alphabet ()
    {
        return gloss_alphabet;
    }

    public void setGloss_alphabet (String gloss_alphabet)
    {
        this.gloss_alphabet = gloss_alphabet;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [gloss_description = "+gloss_description+", created_datetime = "+created_datetime+", gloss_word = "+gloss_word+", modified_datetime = "+modified_datetime+", gloss_id = "+gloss_id+", gloss_alphabet = "+gloss_alphabet+"]";
    }
}
