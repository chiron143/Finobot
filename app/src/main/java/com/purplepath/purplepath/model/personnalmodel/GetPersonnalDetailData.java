package com.purplepath.purplepath.model.personnalmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 26-Jul-16.
 */
public class GetPersonnalDetailData implements Serializable {

    private String message;
    @SerializedName("empty_flds")
    private ArrayList<String> empty_flds;


    private ArrayList<User_per_det> user_per_det;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<User_per_det> getUser_per_det ()
    {
        return user_per_det;
    }

    public ArrayList<String> getEmpty_flds() {
        return empty_flds;
    }



    public void setUser_per_det (ArrayList<User_per_det> user_per_det)
    {

        this.user_per_det = user_per_det;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_per_det = "+user_per_det+"]";
    }
}