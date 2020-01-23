package com.purplepath.purplepath.taxfiling.getdeclarationmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 3/22/18.
 */

public class Dec_details implements Serializable{

    private String pan_no;

    private String first_name;

    private String capacity;

    private String surname;

    public String getPan_no ()
    {
        return pan_no;
    }

    public void setPan_no (String pan_no)
    {
        this.pan_no = pan_no;
    }

    public String getFirst_name ()
    {
        return first_name;
    }

    public void setFirst_name (String first_name)
    {
        this.first_name = first_name;
    }

    public String getCapacity ()
    {
        return capacity;
    }

    public void setCapacity (String capacity)
    {
        this.capacity = capacity;
    }

    public String getSurname ()
    {
        return surname;
    }

    public void setSurname (String surname)
    {
        this.surname = surname;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pan_no = "+pan_no+", first_name = "+first_name+", capacity = "+capacity+", surname = "+surname+"]";
    }
}

