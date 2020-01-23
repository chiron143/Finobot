package com.purplepath.purplepath.propertyinsurance.healthinsurancemodel;

import java.io.Serializable;

/**
 * Created by pravinr on 11/16/17.
 */

public class Suggested implements Serializable {

    private String cover;

    private String family_relation;

    private String age;

    private String family_id;

    private String family_name;

    private String user_id;

    public String getCover ()
    {
        return cover;
    }

    public void setCover (String cover)
    {
        this.cover = cover;
    }

    public String getFamily_relation ()
    {
        return family_relation;
    }

    public void setFamily_relation (String family_relation)
    {
        this.family_relation = family_relation;
    }

    public String getAge ()
    {
        return age;
    }

    public void setAge (String age)
    {
        this.age = age;
    }

    public String getFamily_id ()
    {
        return family_id;
    }

    public void setFamily_id (String family_id)
    {
        this.family_id = family_id;
    }

    public String getFamily_name ()
    {
        return family_name;
    }

    public void setFamily_name (String family_name)
    {
        this.family_name = family_name;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cover = "+cover+", family_relation = "+family_relation+", age = "+age+", family_id = "+family_id+", family_name = "+family_name+", user_id = "+user_id+"]";
    }
}

