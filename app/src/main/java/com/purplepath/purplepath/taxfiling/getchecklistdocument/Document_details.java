package com.purplepath.purplepath.taxfiling.getchecklistdocument;

import java.io.Serializable;

/**
 * Created by pravinr on 3/29/18.
 */

public class Document_details implements Serializable{

    private String digital_version;

    private String id;

    private String rejected_status;

    private String doc_url;

    private String doc_name;

    private String sub_digital_version;

    private String created_datetime;

    private String auditor_verified_status;

    private String verified_status;

    private String doc_tag;

    private String modified_datetime;

    private String auditor_rejected_status;

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

    public String getRejected_status ()
    {
        return rejected_status;
    }

    public void setRejected_status (String rejected_status)
    {
        this.rejected_status = rejected_status;
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

    public String getAuditor_verified_status ()
    {
        return auditor_verified_status;
    }

    public void setAuditor_verified_status (String auditor_verified_status)
    {
        this.auditor_verified_status = auditor_verified_status;
    }

    public String getVerified_status ()
    {
        return verified_status;
    }

    public void setVerified_status (String verified_status)
    {
        this.verified_status = verified_status;
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

    public String getAuditor_rejected_status ()
    {
        return auditor_rejected_status;
    }

    public void setAuditor_rejected_status (String auditor_rejected_status)
    {
        this.auditor_rejected_status = auditor_rejected_status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [digital_version = "+digital_version+", id = "+id+", rejected_status = "+rejected_status+", doc_url = "+doc_url+", doc_name = "+doc_name+", sub_digital_version = "+sub_digital_version+", created_datetime = "+created_datetime+", auditor_verified_status = "+auditor_verified_status+", verified_status = "+verified_status+", doc_tag = "+doc_tag+", modified_datetime = "+modified_datetime+", auditor_rejected_status = "+auditor_rejected_status+"]";
    }
}

