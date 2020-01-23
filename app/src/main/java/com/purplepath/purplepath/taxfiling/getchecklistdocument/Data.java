package com.purplepath.purplepath.taxfiling.getchecklistdocument;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 3/29/18.
 */

public class Data implements Serializable{
    private String message;

    private ArrayList<Document_details> document_details;

    public ArrayList<Document_details> getDocument_details() {
        return document_details;
    }

    public void setDocument_details(ArrayList<Document_details> document_details) {
        this.document_details = document_details;
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
        return "ClassPojo [message = "+message+", document_details = "+document_details+"]";
    }
}

