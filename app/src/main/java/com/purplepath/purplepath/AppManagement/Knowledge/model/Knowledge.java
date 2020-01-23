package com.purplepath.purplepath.AppManagement.Knowledge.model;

import java.io.Serializable;

/**
 * Created by pravinr on 8/5/17.
 */

public class Knowledge implements Serializable {
    private String topic;

    private String web_url;

    public String getTopic ()
    {
        return topic;
    }

    public void setTopic (String topic)
    {
        this.topic = topic;
    }

    public String getWeb_url ()
    {
        return web_url;
    }

    public void setWeb_url (String web_url)
    {
        this.web_url = web_url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [topic = "+topic+", web_url = "+web_url+"]";
    }
}

