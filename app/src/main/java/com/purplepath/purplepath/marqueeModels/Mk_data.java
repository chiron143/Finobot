package com.purplepath.purplepath.marqueeModels;

import java.io.Serializable;

/**
 * Created by bertrandrussellsakthees on 02/01/17.
 */

public class Mk_data  implements Serializable {

    private String name;

    private String curr_value;

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCurr_value ()
    {
        return curr_value;
    }

    public void setCurr_value (String curr_value)
    {
        this.curr_value = curr_value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [name = "+name+", curr_value = "+curr_value+"]";
    }
}
