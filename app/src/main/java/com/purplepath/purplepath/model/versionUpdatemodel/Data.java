package com.purplepath.purplepath.model.versionUpdatemodel;

import java.io.Serializable;

/**
 * Created by pravinr on 11/29/17.
 */

public class Data implements Serializable {
    private String message;

    private String is_version_update;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getIs_version_update ()
    {
        return is_version_update;
    }

    public void setIs_version_update (String is_version_update)
    {
        this.is_version_update = is_version_update;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", is_version_update = "+is_version_update+"]";
    }
}

