package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by dinesh on 11/05/16.
 */
public class ForgotPasswordData implements Serializable {
    private String message;

    private String auth_code;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getAuth_code ()
    {
        return auth_code;
    }

    public void setAuth_code (String auth_code)
    {
        this.auth_code = auth_code;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", auth_code = "+auth_code+"]";
    }
}