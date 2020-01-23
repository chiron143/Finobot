package com.purplepath.purplepath.settings.UpdateModels;

import com.purplepath.purplepath.famlydetail.model.Updated_details;

/**
 * Created by Pratheep.S on 12-01-2017.
 */

public class SelectionsUpdateData {

    private String message;

    private Updated_details updated_details;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Updated_details getUpdated_details ()
    {
        return updated_details;
    }

    public void setUpdated_details (Updated_details updated_details)
    {
        this.updated_details = updated_details;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", updated_details = "+updated_details+"]";
    }

}
