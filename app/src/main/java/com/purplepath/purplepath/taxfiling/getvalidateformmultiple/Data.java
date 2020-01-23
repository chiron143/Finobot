package com.purplepath.purplepath.taxfiling.getvalidateformmultiple;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 7/5/18.
 */

public class Data implements Serializable {

    private String message;

    private Upload_array upload_array;

    private Response_array response_array;

    private ArrayList<Error_array> error_array;

    public ArrayList<Error_array> getError_array() {
        return error_array;
    }

    public void setError_array(ArrayList<Error_array> error_array) {
        this.error_array = error_array;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Upload_array getUpload_array ()
    {
        return upload_array;
    }

    public void setUpload_array (Upload_array upload_array)
    {
        this.upload_array = upload_array;
    }

    public Response_array getResponse_array ()
    {
        return response_array;
    }

    public void setResponse_array (Response_array response_array)
    {
        this.response_array = response_array;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", upload_array = "+upload_array+", response_array = "+response_array+", error_array = "+error_array+"]";
    }
}

