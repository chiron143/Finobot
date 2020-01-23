package com.purplepath.purplepath.assets.model;

import java.io.Serializable;

/**
 * Created by Bert on 07-Jul-16.
 */
public class AddAssetData implements Serializable
{
    private String message;

    private AddAssetInputData input;

    private String asst_id;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public AddAssetInputData getInput ()
    {
        return input;
    }

    public void setInput (AddAssetInputData input)
    {
        this.input = input;
    }

    public String getAsst_id ()
    {
        return asst_id;
    }

    public void setAsst_id (String asst_id)
    {
        this.asst_id = asst_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", input = "+input+", asst_id = "+asst_id+"]";
    }
}