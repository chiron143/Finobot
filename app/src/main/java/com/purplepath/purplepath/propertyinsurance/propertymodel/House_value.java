package com.purplepath.purplepath.propertyinsurance.propertymodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 11/21/17.
 */

public class House_value implements Serializable {
    private String overall_value;


    private ArrayList<House_val_by_cat> house_val_by_cat;

    public ArrayList<House_val_by_cat> getHouse_val_by_cat() {
        return house_val_by_cat;
    }

    public void setHouse_val_by_cat(ArrayList<House_val_by_cat> house_val_by_cat) {
        this.house_val_by_cat = house_val_by_cat;
    }

    public String getOverall_value ()
    {
        return overall_value;
    }

    public void setOverall_value (String overall_value)
    {
        this.overall_value = overall_value;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [overall_value = "+overall_value+", house_val_by_cat = "+house_val_by_cat+"]";
    }
}

