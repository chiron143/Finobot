package com.purplepath.purplepath.Notification.Models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 21-01-2017.
 */

public class PromptsData implements Serializable {
    private String message;

    private Personal_fields personal_fields;

    private Family_fields family_fields;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Personal_fields getPersonal_fields ()
    {
        return personal_fields;
    }

    public void setPersonal_fields (Personal_fields personal_fields)
    {
        this.personal_fields = personal_fields;
    }
    public Family_fields getFamily_fields() {
        return family_fields;
    }

    public void setFamily_fields(Family_fields family_fields) {
        this.family_fields = family_fields;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", personal_fields = "+personal_fields+"]";
    }
}
