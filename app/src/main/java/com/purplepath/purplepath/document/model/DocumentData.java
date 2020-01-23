package com.purplepath.purplepath.document.model;

import com.purplepath.purplepath.AppManagement.FAQ.model.FAQ;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 6/23/17.
 */

public class DocumentData implements Serializable {

        private String message;

        //private UploadedData[] uploaded_data;

    private ArrayList<UploadedData>  uploaded_data;

    public ArrayList<UploadedData> getUploaded_data() {
        return uploaded_data;
    }

    public void setUploaded_data(ArrayList<UploadedData> uploaded_data) {
        this.uploaded_data = uploaded_data;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

//    public UploadedData[] getUploaded_data ()
//    {
//        return uploaded_data;
//    }
//
//    public void setUploaded_data (UploadedData[] uploaded_data)
//    {
//        this.uploaded_data = uploaded_data;
//    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", uploaded_data = "+uploaded_data+"]";
    }
}
