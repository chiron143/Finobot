package com.purplepath.purplepath.assetsanalysis.model;

/**
 * Created by vishnu on 28-09-2016.
 */
public class Data
{
    private String message;

    private Rec_alloc rec_alloc;

    private Cur_alloc cur_alloc;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Rec_alloc getRec_alloc ()
    {
        return rec_alloc;
    }

    public void setRec_alloc (Rec_alloc rec_alloc)
    {
        this.rec_alloc = rec_alloc;
    }

    public Cur_alloc getCur_alloc ()
    {
        return cur_alloc;
    }

    public void setCur_alloc (Cur_alloc cur_alloc)
    {
        this.cur_alloc = cur_alloc;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", rec_alloc = "+rec_alloc+", cur_alloc = "+cur_alloc+"]";
    }
}