package com.purplepath.purplepath.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 15/03/16.
 */
public class LoginDataModel implements Serializable {
    private String message;
    private String access_token;

    private User_details user_details;

    private String is_version_update;

    private ArrayList<Restricted_menus> restricted_menus;

    public ArrayList<Restricted_menus> getRestricted_menus() {
        return restricted_menus;
    }

    public void setRestricted_menus(ArrayList<Restricted_menus> restricted_menus) {
        this.restricted_menus = restricted_menus;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getAccess_token ()
    {
        return access_token;
    }

    public void setAccess_token (String access_token)
    {
        this.access_token = access_token;
    }

    public User_details getUser_details ()
    {
        return user_details;
    }

    public void setUser_details (User_details user_details)
    {
        this.user_details = user_details;
    }

    public String getIs_version_update()
    {
        return is_version_update;
    }

    public void setIs_version_update(String is_version_update)
    {
        this.is_version_update = is_version_update;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = " + message + ",restricted_menus="+restricted_menus+", user_details = " + user_details + ", is_version_update = " +
                is_version_update + "]";
    }


}
