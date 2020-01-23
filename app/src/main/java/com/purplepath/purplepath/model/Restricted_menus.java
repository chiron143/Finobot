package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by pravinr on 11/27/17.
 */

public class Restricted_menus implements Serializable {
    private String user_type;

    private String menu_name;

    public String getUser_type ()
    {
        return user_type;
    }

    public void setUser_type (String user_type)
    {
        this.user_type = user_type;
    }

    public String getMenu_name ()
    {
        return menu_name;
    }

    public void setMenu_name (String menu_name)
    {
        this.menu_name = menu_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [user_type = "+user_type+", menu_name = "+menu_name+"]";
    }
}

