package com.purplepath.purplepath.propertyinsurance.propertymodel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 11/21/17.
 */

public class Insurance implements Serializable {
    private String overall_value;

    private ArrayList<Ins_by_prod_type> ins_by_prod_type;

    public ArrayList<Ins_by_prod_type> getIns_by_prod_type() {
        return ins_by_prod_type;
    }

    public void setIns_by_prod_type(ArrayList<Ins_by_prod_type> ins_by_prod_type) {
        this.ins_by_prod_type = ins_by_prod_type;
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
        return "ClassPojo [overall_value = "+overall_value+", ins_by_prod_type = "+ins_by_prod_type+"]";
    }
}

