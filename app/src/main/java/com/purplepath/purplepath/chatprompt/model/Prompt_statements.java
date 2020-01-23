package com.purplepath.purplepath.chatprompt.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 12/13/17.
 */

public class Prompt_statements implements Serializable {
    private String statement;

    private String id;

    private String created_datetime;

    private ArrayList<String> form_array;

    private String boolean_flag;

    private String modified_datetime;

    private String page_name;

    private String type;

    private String corresponding_table;

    private String updated_flag;

    private String user_visited_flag;

    public String getUser_visited_flag() {
        return user_visited_flag;
    }

    public void setUser_visited_flag(String user_visited_flag) {
        this.user_visited_flag = user_visited_flag;
    }

    public String getBoolean_flag() {
        return boolean_flag;
    }

    public void setBoolean_flag(String boolean_flag) {
        this.boolean_flag = boolean_flag;
    }

    public String getStatement ()
    {
        return statement;
    }

    public void setStatement (String statement)
    {
        this.statement = statement;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public ArrayList<String> getForm_array() {
        return form_array;
    }

    public void setForm_array(ArrayList<String> form_array) {
        this.form_array = form_array;
    }


    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getPage_name ()
    {
        return page_name;
    }

    public void setPage_name (String page_name)
    {
        this.page_name = page_name;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getCorresponding_table ()
    {
        return corresponding_table;
    }

    public void setCorresponding_table (String corresponding_table)
    {
        this.corresponding_table = corresponding_table;
    }

    public String getUpdated_flag ()
    {
        return updated_flag;
    }

    public void setUpdated_flag (String updated_flag)
    {
        this.updated_flag = updated_flag;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [statement = "+statement+", id = "+id+", created_datetime = "+created_datetime+"," +
                " form_array = "+form_array+", boolean = "+boolean_flag+", modified_datetime = "+modified_datetime+"," +
                " page_name = "+page_name+", type = "+type+", corresponding_table = "+corresponding_table+", " +
                "updated_flag = "+updated_flag+"user_visited_flag="+user_visited_flag+"]";
    }
}

