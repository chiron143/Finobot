package com.purplepath.purplepath.document.model;

import java.io.Serializable;

/**
 * Created by pravinr on 6/23/17.
 */

public class UploadedData implements Serializable {
    private String doc_id;

    public String getDoc_id ()
    {
        return doc_id;
    }

    public void setDoc_id (String doc_id)
    {
        this.doc_id = doc_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [doc_id = "+doc_id+"]";
    }
}

