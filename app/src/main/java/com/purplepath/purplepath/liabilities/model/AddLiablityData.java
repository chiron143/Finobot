package com.purplepath.purplepath.liabilities.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 11/07/16.
 */
public class AddLiablityData implements Serializable {

    private String message;

    private Input input;



    private ArrayList<UserLiabilityList> user_liabs;

    public ArrayList<UserLiabilityList> getUser_liabs() {
        return user_liabs;
    }

    public void setUser_liabs(ArrayList<UserLiabilityList> user_liabs) {
        this.user_liabs = user_liabs;
    }

    private String liab_id;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Input getInput ()
    {
        return input;
    }

    public void setInput (Input input)
    {
        this.input = input;
    }

    public String getLiab_id ()
    {
        return liab_id;
    }

    public void setLiab_id (String liab_id)
    {
        this.liab_id = liab_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", input = "+input+", liab_id = "+liab_id+"]";
    }
}
