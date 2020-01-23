package com.purplepath.purplepath.taxfiling.addsessionmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 6/20/18.
 */

public class Input implements Serializable{
    private String field_name;

    private String user_id;

    private String corresponding_table;

    private String field_value;

    public String getField_name ()
    {
        return field_name;
    }

    public void setField_name (String field_name)
    {
        this.field_name = field_name;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getCorresponding_table ()
    {
        return corresponding_table;
    }

    public void setCorresponding_table (String corresponding_table)
    {
        this.corresponding_table = corresponding_table;
    }

    public String getField_value ()
    {
        return field_value;
    }

    public void setField_value (String field_value)
    {
        this.field_value = field_value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [field_name = "+field_name+", user_id = "+user_id+", corresponding_table = "+corresponding_table+", field_value = "+field_value+"]";
    }
}

