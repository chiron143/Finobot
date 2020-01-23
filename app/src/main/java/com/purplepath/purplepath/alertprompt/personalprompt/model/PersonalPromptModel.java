package com.purplepath.purplepath.alertprompt.personalprompt.model;

import java.io.Serializable;

/**
 * Created by dinesh on 16/01/17.
 */
public class PersonalPromptModel implements Serializable {
    private String status_code;

    public String getStatusCode() { return this.status_code; }

    public void setStatusCode(String status_code) { this.status_code = status_code; }

    private String status;

    public String getStatus() { return this.status; }

    public void setStatus(String status) { this.status = status; }

    private String service_name;

    public String getServiceName() { return this.service_name; }

    public void setServiceName(String service_name) { this.service_name = service_name; }

    private Data data;

    public Data getData() { return this.data; }

    public void setData(Data data) { this.data = data; }
}
