package com.purplepath.purplepath.incomechartdetail.model;

import java.io.Serializable;

/**
 * Created by dinesh on 18/07/16.
 */
public class Cg_det implements Serializable {
    private String cg_short_term_per;

    private String cg_long_term;

    private String cg_short_term;

    private String cg_long_term_per;

    public String getCg_short_term_per ()
    {
        return cg_short_term_per;
    }

    public void setCg_short_term_per (String cg_short_term_per)
    {
        this.cg_short_term_per = cg_short_term_per;
    }

    public String getCg_long_term ()
    {
        return cg_long_term;
    }

    public void setCg_long_term (String cg_long_term)
    {
        this.cg_long_term = cg_long_term;
    }

    public String getCg_short_term ()
    {
        return cg_short_term;
    }

    public void setCg_short_term (String cg_short_term)
    {
        this.cg_short_term = cg_short_term;
    }

    public String getCg_long_term_per ()
    {
        return cg_long_term_per;
    }

    public void setCg_long_term_per (String cg_long_term_per)
    {
        this.cg_long_term_per = cg_long_term_per;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [cg_short_term_per = "+cg_short_term_per+", cg_long_term = "+cg_long_term+", cg_short_term = "+cg_short_term+", cg_long_term_per = "+cg_long_term_per+"]";
    }
}
