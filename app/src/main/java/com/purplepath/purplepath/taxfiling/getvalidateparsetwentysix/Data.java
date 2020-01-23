package com.purplepath.purplepath.taxfiling.getvalidateparsetwentysix;

import java.io.Serializable;

/**
 * Created by pravinr on 6/12/18.
 */

public class Data implements Serializable {
    private String message;

    private String is_form_26as;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getIs_form_26as ()
    {
        return is_form_26as;
    }

    public void setIs_form_26as (String is_form_26as)
    {
        this.is_form_26as = is_form_26as;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", is_form_26as = "+is_form_26as+"]";
    }
}

