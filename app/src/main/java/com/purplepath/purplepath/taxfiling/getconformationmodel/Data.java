package com.purplepath.purplepath.taxfiling.getconformationmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 4/25/18.
 */

public class Data implements Serializable {

    private String message;

    private String itr1_url;

    private String approve_flag;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getItr1_url ()
    {
        return itr1_url;
    }

    public void setItr1_url (String itr1_url)
    {
        this.itr1_url = itr1_url;
    }

    public String getApprove_flag ()
    {
        return approve_flag;
    }

    public void setApprove_flag (String approve_flag)
    {
        this.approve_flag = approve_flag;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", itr1_url = "+itr1_url+", approve_flag = "+approve_flag+"]";
    }
}

