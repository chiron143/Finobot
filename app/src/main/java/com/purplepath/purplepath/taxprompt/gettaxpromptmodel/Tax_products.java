package com.purplepath.purplepath.taxprompt.gettaxpromptmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 1/22/18.
 */

public class Tax_products implements Serializable{

    private String field_name;

    private String ent_value;

    private String id;

    private String created_datetime;

    private String table_name;

    private String instrument;

    private String modified_datetime;

    private String section;

    private String disability_flag;

    public String getDisability_flag() {
        return disability_flag;
    }

    public void setDisability_flag(String disability_flag) {
        this.disability_flag = disability_flag;
    }

    public String getField_name ()
    {
        return field_name;
    }

    public void setField_name (String field_name)
    {
        this.field_name = field_name;
    }

    public String getEnt_value ()
    {
        return ent_value;
    }

    public void setEnt_value (String ent_value)
    {
        this.ent_value = ent_value;
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

    public String getTable_name ()
    {
        return table_name;
    }

    public void setTable_name (String table_name)
    {
        this.table_name = table_name;
    }

    public String getInstrument ()
    {
        return instrument;
    }

    public void setInstrument (String instrument)
    {
        this.instrument = instrument;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getSection ()
    {
        return section;
    }

    public void setSection (String section)
    {
        this.section = section;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [field_name = "+field_name+", ent_value = "+ent_value+", id = "+id+"," +
                " created_datetime = "+created_datetime+", table_name = "+table_name+"," +
                " instrument = "+instrument+", modified_datetime = "+modified_datetime+", section = "+section+", disability_flag = "+disability_flag+"]";
    }
}

