package com.purplepath.purplepath.propertyinsurance.propertymodel;

import java.io.Serializable;

/**
 * Created by pravinr on 10/27/17.
 */

public class Property_ins_plan implements Serializable {
    private Insurance insurance;

    private House_value house_value;

    private House_hold_content house_hold_content;

    private String is_own_house;

    public Insurance getInsurance ()
    {
        return insurance;
    }

    public void setInsurance (Insurance insurance)
    {
        this.insurance = insurance;
    }

    public House_value getHouse_value ()
    {
        return house_value;
    }

    public void setHouse_value (House_value house_value)
    {
        this.house_value = house_value;
    }

    public House_hold_content getHouse_hold_content ()
    {
        return house_hold_content;
    }

    public void setHouse_hold_content (House_hold_content house_hold_content)
    {
        this.house_hold_content = house_hold_content;
    }

    public String getIs_own_house ()
    {
        return is_own_house;
    }

    public void setIs_own_house (String is_own_house)
    {
        this.is_own_house = is_own_house;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [insurance = "+insurance+", house_value = "+house_value+", house_hold_content = "+house_hold_content+", is_own_house = "+is_own_house+"]";
    }
}

