package com.purplepath.purplepath.document.getfilemodels;

import com.purplepath.purplepath.AppManagement.Links.model.Links;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Suresh on 26/06/17.
 */

public class GetDocumentData implements Serializable {

    private String message;

    private ArrayList<Document_details> document_details;

    private ArrayList<Document_count> document_count;

    private ArrayList<Sec_prod> sec_prod;

    public ArrayList<Sec_prod> getSec_prod() {
        return sec_prod;
    }

    public void setSec_prod(ArrayList<Sec_prod> sec_prod) {
        this.sec_prod = sec_prod;
    }

    public ArrayList<Document_count> getDocument_count() {
        return document_count;
    }

    public void setDocument_count(ArrayList<Document_count> document_count) {
        this.document_count = document_count;
    }

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
        return "ClassPojo [message = "+message+", document_details = "+document_details+", sec_prod = "+sec_prod+"]";
    }
}
