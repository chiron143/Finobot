package com.purplepath.purplepath.networthanalysis.model;

import java.io.Serializable;

/**
 * Created by dinesh on 29/09/16.
 */
public class Data implements Serializable {
    private String message;

    private Net_worth net_worth;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Net_worth getNet_worth ()
    {
        return net_worth;
    }

    public void setNet_worth (Net_worth net_worth)
    {
        this.net_worth = net_worth;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", net_worth = "+net_worth+"]";
    }
}
