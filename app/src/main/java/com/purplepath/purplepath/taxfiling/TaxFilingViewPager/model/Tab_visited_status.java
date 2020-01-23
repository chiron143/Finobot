package com.purplepath.purplepath.taxfiling.TaxFilingViewPager.model;

import java.io.Serializable;

/**
 * Created by pravinr on 8/10/18.
 */

public class Tab_visited_status implements Serializable {

    private String id;

    private String created_datetime;

    private String user_id;

    private String tab_name;

    private String modified_datetime;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getTab_name ()
    {
        return tab_name;
    }

    public void setTab_name (String tab_name)
    {
        this.tab_name = tab_name;
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
        return "ClassPojo [id = "+id+", created_datetime = "+created_datetime+", user_id = "+user_id+", tab_name = "+tab_name+", modified_datetime = "+modified_datetime+"]";
    }
}

