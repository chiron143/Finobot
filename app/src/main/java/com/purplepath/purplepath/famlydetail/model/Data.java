package com.purplepath.purplepath.famlydetail.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 14/06/16.
 */
public class Data implements Serializable{
    private String message;

    @SerializedName("family_details")
    private ArrayList<Family_details> family_details;


    @SerializedName("updated_details")
    private Updated_details updated_details;

    public Updated_details getUpdated_details() {
        return updated_details;
    }

    public ArrayList<Family_details> getFamily_details ()
    {
        return family_details;
    }


    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", family_details = "+family_details+"]";
    }
}