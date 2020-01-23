package com.purplepath.purplepath.taxfiling.getchecklist;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 3/28/18.
 */

public class Result implements Serializable{


    private ArrayList<String> products;

    private String section;

    private String uploaded;

    private String insuff_files;

    private String rejected_count;

    public String getInsuff_files() {
        return insuff_files;
    }

    public void setInsuff_files(String insuff_files) {
        this.insuff_files = insuff_files;
    }

    public String getRejected_count() {
        return rejected_count;
    }

    public void setRejected_count(String rejected_count) {
        this.rejected_count = rejected_count;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

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
        return "ClassPojo [rejected_count = "+rejected_count+", uploaded = "+uploaded+", products = "+products+", section = "+section+", insuff_files = "+insuff_files+"]";
    }
}

