package com.purplepath.purplepath.document.deleteModels;

import java.io.Serializable;

/**
 * Created by Suresh on 30/06/17.
 */

public class Input implements Serializable {


    private String doc_id;

    private String user_id;

    public String getDoc_id ()
    {
        return doc_id;
    }

    public void setDoc_id (String doc_id)
    {
        this.doc_id = doc_id;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [doc_id = "+doc_id+", user_id = "+user_id+"]";
    }
}
