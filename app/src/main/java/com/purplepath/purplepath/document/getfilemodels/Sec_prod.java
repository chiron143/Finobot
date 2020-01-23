package com.purplepath.purplepath.document.getfilemodels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 3/30/18.
 */

public class Sec_prod implements Serializable{


    private ArrayList<String> products;

    private String section;

    public ArrayList<String> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<String> products) {
        this.products = products;
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
        return "ClassPojo [products = "+products+", section = "+section+"]";
    }
}

