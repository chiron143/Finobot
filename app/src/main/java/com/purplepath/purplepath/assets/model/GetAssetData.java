package com.purplepath.purplepath.assets.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 07-Jul-16.
 */
public class GetAssetData implements Serializable
{
    private String message;

    private ArrayList<GetAssetUserData> user_assets;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<GetAssetUserData> getUser_assets ()
    {
        return user_assets;
    }

    public void setUser_assets (ArrayList<GetAssetUserData> user_assets)
    {
        this.user_assets = user_assets;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", user_assets = "+user_assets+"]";
    }
}