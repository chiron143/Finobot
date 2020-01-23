package com.purplepath.purplepath.taxfiling.getvalidateform;

import java.io.Serializable;

/**
 * Created by pravinr on 7/13/18.
 */

public class Error_array implements Serializable{

    private String id;

    private String title;

    private String description;

    private String created_datetime;

    private String error_code;

    private String modified_datetime;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getError_code ()
    {
        return error_code;
    }

    public void setError_code (String error_code)
    {
        this.error_code = error_code;
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
        return "ClassPojo [id = "+id+", title = "+title+", description = "+description+", created_datetime = "+created_datetime+", error_code = "+error_code+", modified_datetime = "+modified_datetime+"]";
    }
}

