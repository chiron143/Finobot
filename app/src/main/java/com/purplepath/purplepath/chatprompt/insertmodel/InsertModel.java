package com.purplepath.purplepath.chatprompt.insertmodel;

import java.io.Serializable;

/**
 * Created by pravinr on 12/14/17.
 */

public class InsertModel implements Serializable {
    private String status_code;

    private String status;

    private InsertData data;

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

    public InsertData getData ()
    {
        return data;
    }

    public void setData (InsertData data)
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

    @Override
    public String toString()
    {
        return "ClassPojo [status_code = "+status_code+", status = "+status+", data = "+data+", service_name = "+service_name+"]";
    }
}

