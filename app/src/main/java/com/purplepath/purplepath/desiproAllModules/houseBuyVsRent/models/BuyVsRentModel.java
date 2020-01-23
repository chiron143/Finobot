package com.purplepath.purplepath.desiproAllModules.houseBuyVsRent.models;

import java.io.Serializable;

/**
 * Created by Pratheep.S on 25-09-2017.
 */

public class BuyVsRentModel implements Serializable {
    private String status_code;

    private String status;

    private MainData data;

    private String service_name;

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

    public MainData getData ()
    {
        return data;
    }

    public void setData (MainData data)
    {
        this.data = data;
    }

    public String getService_name ()
    {
        return service_name;
    }

    public void setService_name (String service_name)
    {
        this.service_name = service_name;
    }
}
