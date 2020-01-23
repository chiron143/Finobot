package com.purplepath.purplepath.expensesanalysis.model;

import java.io.Serializable;

/**
 * Created by Bert on 17-Jul-16.
 */
public class Fd_det implements Serializable
{
    private String fd_groc_per;

    private String fd_groc;

    private String fd_eat_out;

    private String fd_eat_out_per;

    public String getFd_groc_per ()
    {
        return fd_groc_per;
    }

    public void setFd_groc_per (String fd_groc_per)
    {
        this.fd_groc_per = fd_groc_per;
    }

    public String getFd_groc ()
{
    return fd_groc;
}

    public void setFd_groc (String fd_groc)
    {
        this.fd_groc = fd_groc;
    }

    public String getFd_eat_out ()
{
    return fd_eat_out;
}

    public void setFd_eat_out (String fd_eat_out)
    {
        this.fd_eat_out = fd_eat_out;
    }

    public String getFd_eat_out_per ()
    {
        return fd_eat_out_per;
    }

    public void setFd_eat_out_per (String fd_eat_out_per)
    {
        this.fd_eat_out_per = fd_eat_out_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [fd_groc_per = "+fd_groc_per+", fd_groc = "+fd_groc+", fd_eat_out = "+fd_eat_out+", fd_eat_out_per = "+fd_eat_out_per+"]";
    }
}
