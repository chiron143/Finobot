package com.purplepath.purplepath.taxfiling.getdialogforminstruction;

import java.io.Serializable;

/**
 * Created by pravinr on 7/6/18.
 */

public class Samples implements Serializable {

    private String id;

    private String created_datetime;

    private String name;

    private String modified_datetime;

    private String type;

    private String url;

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

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", created_datetime = "+created_datetime+", name = "+name+", modified_datetime = "+modified_datetime+", type = "+type+", url = "+url+"]";
    }
}

