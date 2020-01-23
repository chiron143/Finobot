package com.purplepath.purplepath.model.companyCategoryModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 12/20/17.
 */

public class Data implements Serializable {
    private String message;

    private ArrayList<Company_categories> company_categories;

    public ArrayList<Company_categories> getCompany_categories() {
        return company_categories;
    }

    public void setCompany_categories(ArrayList<Company_categories> company_categories) {
        this.company_categories = company_categories;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }



    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", company_categories = "+company_categories+"]";
    }
}

