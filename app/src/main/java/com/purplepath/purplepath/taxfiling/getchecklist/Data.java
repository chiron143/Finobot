package com.purplepath.purplepath.taxfiling.getchecklist;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 3/28/18.
 */

public class Data implements Serializable{

    private String message;

    private String file_uploaded;

    public String getFile_uploaded() {
        return file_uploaded;
    }

    public void setFile_uploaded(String file_uploaded) {
        this.file_uploaded = file_uploaded;
    }

    private ArrayList<Result> result;

    public ArrayList<Result> getResult() {
        return result;
    }

    public void setResult(ArrayList<Result> result) {
        this.result = result;
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
        return "ClassPojo [message = "+message+", result = "+result+"]";
    }
}

