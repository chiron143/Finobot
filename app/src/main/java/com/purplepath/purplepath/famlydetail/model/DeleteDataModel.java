package com.purplepath.purplepath.famlydetail.model;

import java.io.Serializable;

/**
 * Created by dinesh on 11/02/17.
 */
public class DeleteDataModel  implements Serializable {
    private String message;

    private Details details;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Details getDetails ()
    {
        return details;
    }

    public void setDetails (Details details)
    {
        this.details = details;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", details = "+details+"]";
    }
}
