package com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow;

import java.io.Serializable;

/**
 * Created by bertrandrussellsakthees on 07/01/17.
 */

public class Sections  implements Serializable {

        private String val;

        private String name;

    public String getVal ()
    {
        return val;
    }

    public void setVal (String val)
    {
        this.val = val;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [val = "+val+", name = "+name+"]";
    }
}
