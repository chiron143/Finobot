package com.purplepath.purplepath.document.deleteModels;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Suresh on 30/06/17.
 */

public class DeleteModels implements Serializable {

    private String status_code;

    private String status;

//    private Data data;

    private DeleteModelsData data;

    private String service_name;



    public DeleteModelsData getData() {
        return data;
    }

    public void setData(DeleteModelsData data) {
        this.data = data;
    }


    public String getStatus_code ()
    {
        return status_code;
    }

    public void setStatus_code (String status_code)
    {
        this.status_code = status_code;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

//    public Data getData ()
//    {
//        return data;
//    }
//
//    public void setData (Data data)
//    {
//        this.data = data;
//    }

    public String getService_name ()
    {
        return service_name;
    }

    public void setService_name (String service_name)
    {
        this.service_name = service_name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status_code = "+status_code+", status = "+status+", data = "+data+", service_name = "+service_name+"]";
    }
}
