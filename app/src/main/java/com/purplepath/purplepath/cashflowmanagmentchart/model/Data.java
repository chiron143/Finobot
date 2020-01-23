package com.purplepath.purplepath.cashflowmanagmentchart.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 01/12/16.
 */
public class Data implements Serializable {
    private String message;

    private ArrayList<Fin_cash_flow> fin_cash_flow;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Fin_cash_flow> getFin_cash_flow ()
    {
        return fin_cash_flow;
    }

    public void setFin_cash_flow (ArrayList<Fin_cash_flow> fin_cash_flow)
    {
        this.fin_cash_flow = fin_cash_flow;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", fin_cash_flow = "+fin_cash_flow+"]";
    }
}

