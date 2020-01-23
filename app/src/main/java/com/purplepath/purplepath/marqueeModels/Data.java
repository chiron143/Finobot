package com.purplepath.purplepath.marqueeModels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by bertrandrussellsakthees on 02/01/17.
 */

public class Data   implements Serializable {

    private String message;

//    private Mk_data[] mk_data;


    private ArrayList<Mk_data> mk_data;

    public ArrayList<Mk_data> getMk_data() {
        return mk_data;
    }

    public void setMk_data(ArrayList<Mk_data> mk_data) {
        this.mk_data = mk_data;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

//    public Mk_data[] getMk_data ()
//    {
//        return mk_data;
//    }
//
//    public void setMk_data (Mk_data[] mk_data)
//    {
//        this.mk_data = mk_data;
//    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", mk_data = "+mk_data+"]";
    }

}
