package com.purplepath.purplepath.document.getfilemodels;

import java.io.Serializable;

/**
 * Created by Suresh on 26/06/17.
 */

public class Document_details implements Serializable {

    private String product;

    private String digital_version;

    private String id;

    private String doc_url;

    private String doc_name;

    private String sub_digital_version;

    private String created_datetime;

    private String doc_tag;

    private String modified_datetime;

    public String getProduct ()
    {
        return product;
    }

    public void setProduct (String product)
    {
        this.product = product;
    }

    public String getDigital_version ()
    {
        return digital_version;
    }

    public void setDigital_version (String digital_version)
    {
        this.digital_version = digital_version;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getDoc_url ()
    {
        return doc_url;
    }

    public void setDoc_url (String doc_url)
    {
        this.doc_url = doc_url;
    }

    public String getDoc_name ()
    {
        return doc_name;
    }

    public void setDoc_name (String doc_name)
    {
        this.doc_name = doc_name;
    }

    public String getSub_digital_version ()
    {
        return sub_digital_version;
    }

    public void setSub_digital_version (String sub_digital_version)
    {
        this.sub_digital_version = sub_digital_version;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    public String getDoc_tag ()
    {
        return doc_tag;
    }

    public void setDoc_tag (String doc_tag)
    {
        this.doc_tag = doc_tag;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [product = "+product+", digital_version = "+digital_version+", id = "+id+", doc_url = "+doc_url+", doc_name = "+doc_name+", sub_digital_version = "+sub_digital_version+", created_datetime = "+created_datetime+", doc_tag = "+doc_tag+", modified_datetime = "+modified_datetime+"]";
    }
}

