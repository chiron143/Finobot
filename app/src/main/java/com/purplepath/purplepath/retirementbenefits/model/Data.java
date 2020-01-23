package com.purplepath.purplepath.retirementbenefits.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 14/07/16.
 */
public class  Data implements Serializable
{
    private String message;

    private ArrayList<Ret_ben> ret_ben;

    private Input input;

    public ArrayList<Ret_ben> getRet_ben() {
        return ret_ben;
    }

    public void setRet_ben(ArrayList<Ret_ben> ret_ben) {
        this.ret_ben = ret_ben;
    }

    private String ref_ben_id;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public Input getInput ()
    {
        return input;
    }

    public void setInput (Input input)
    {
        this.input = input;
    }

    public String getRef_ben_id ()
    {
        return ref_ben_id;
    }

    public void setRef_ben_id (String ref_ben_id)
    {
        this.ref_ben_id = ref_ben_id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", input = "+input+", ref_ben_id = "+ref_ben_id+"]";
    }
}
