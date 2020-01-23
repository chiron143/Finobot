package com.purplepath.purplepath.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Bert on 20-May-16.
 */
public class AddDeviceIdModel implements Serializable {
    private String status_code;

    private String status;

//    private AddDeviceIdData data;

    private ArrayList<AddDeviceIdData> data;

    private String service_name;

    public ArrayList<AddDeviceIdData> getData() {
        return data;
    }

    public void setData(ArrayList<AddDeviceIdData> data) {
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

//    public AddDeviceIdData getData ()
//    {
//        return data;
//    }
//
//    public void setData (AddDeviceIdData data)
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
}
