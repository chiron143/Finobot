package com.purplepath.purplepath.goaltimeline.getGoalPlanModel;

import java.io.Serializable;

/**
 * Created by Suresh on 25/07/17.
 */

public class Goal_plan  implements Serializable {

    private String earnings;

    private String need;

    private String time;

    private String grant;

    private String end_val;

    private String deficit;

    private String beg_val;

    private String age;

    private String year;

    private String savings;

    private String shade;

    private String cost;

    public String getEarnings ()
    {
        return earnings;
    }

    public void setEarnings (String earnings)
    {
        this.earnings = earnings;
    }

    public String getNeed ()
    {
        return need;
    }

    public void setNeed (String need)
    {
        this.need = need;
    }

    public String getTime ()
    {
        return time;
    }

    public void setTime (String time)
    {
        this.time = time;
    }

    public String getGrant ()
    {
        return grant;
    }

    public void setGrant (String grant)
    {
        this.grant = grant;
    }

    public String getEnd_val ()
    {
        return end_val;
    }

    public void setEnd_val (String end_val)
    {
        this.end_val = end_val;
    }

    public String getDeficit ()
    {
        return deficit;
    }

    public void setDeficit (String deficit)
    {
        this.deficit = deficit;
    }

    public String getBeg_val ()
    {
        return beg_val;
    }

    public void setBeg_val (String beg_val)
    {
        this.beg_val = beg_val;
    }

    public String getAge ()
    {
        return age;
    }

    public void setAge (String age)
    {
        this.age = age;
    }

    public String getYear ()
    {
        return year;
    }

    public void setYear (String year)
    {
        this.year = year;
    }

    public String getSavings ()
    {
        return savings;
    }

    public void setSavings (String savings)
    {
        this.savings = savings;
    }

    public String getShade ()
    {
        return shade;
    }

    public void setShade (String shade)
    {
        this.shade = shade;
    }

    public String getCost ()
    {
        return cost;
    }

    public void setCost (String cost)
    {
        this.cost = cost;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [earnings = "+earnings+", need = "+need+", time = "+time+", grant = "+grant+", end_val = "+end_val+", deficit = "+deficit+", beg_val = "+beg_val+", age = "+age+", year = "+year+", savings = "+savings+", shade = "+shade+", cost = "+cost+"]";
    }
}
