package com.purplepath.purplepath.Notification.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Pratheep.S on 21-01-2017.
 */

public class Personal_fields  implements Serializable{
    private ArrayList<String> empty_fields;

    private String page_id;

    private String page_name;

    public ArrayList<String> getEmpty_fields ()
    {
        return empty_fields;
    }

    public void setEmpty_fields (ArrayList<String> empty_fields)
    {
        this.empty_fields = empty_fields;
    }

    public String getPage_id ()
    {
        return page_id;
    }

    public void setPage_id (String page_id)
    {
        this.page_id = page_id;
    }

    public String getPage_name ()
    {
        return page_name;
    }

    public void setPage_name (String page_name)
    {
        this.page_name = page_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [empty_fields = "+empty_fields+", page_id = "+page_id+", page_name = "+page_name+"]";
    }

}
