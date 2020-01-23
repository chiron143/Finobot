package com.purplepath.purplepath.recommendation.getsaveinvestmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 9/12/17.
 */

public class Resources_used implements Serializable {

    private String property;

    private String salary;

    private String business;

    public String getProperty ()
    {
        return property;
    }

    public void setProperty (String property)
    {
        this.property = property;
    }

    public String getSalary ()
    {
        return salary;
    }

    public void setSalary (String salary)
    {
        this.salary = salary;
    }

    public String getBusiness ()
    {
        return business;
    }

    public void setBusiness (String business)
    {
        this.business = business;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [property = "+property+", salary = "+salary+", business = "+business+"]";
    }
}

