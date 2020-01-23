package com.purplepath.purplepath.desiproAllModules.depositcomparison.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 28/06/17.
 */

public class DepositData implements Serializable{
    private String message;

    private Dep1 dep1;

    private Dep2 dep2;

    private Dep3 dep3;

//    private ArrayList<String> best_ln_array;
//
//
//    public String getMessage ()
//    {
//        return message;
//    }
//
//    public ArrayList<String> getBest_ln_array() {
//        return best_ln_array;
//    }
//
//    public void setBest_ln_array(ArrayList<String> best_ln_array) {
//        this.best_ln_array = best_ln_array;
//    }


    private ArrayList<String> best_dep_array;

    public ArrayList<String> getBest_dep_array() {
        return best_dep_array;
    }

    public void setBest_dep_array(ArrayList<String> best_dep_array) {
        this.best_dep_array = best_dep_array;
    }

    public void setMessage (String message)

    {
        this.message = message;
    }

    public Dep1 getDep1 ()
    {
        return dep1;
    }

    public void setDep1 (Dep1 dep1)
    {
        this.dep1 = dep1;
    }

    public Dep2 getDep2 ()
    {
        return dep2;
    }

    public void setDep2 (Dep2 dep2)
    {
        this.dep2 = dep2;
    }

    public Dep3 getDep3 ()
    {
        return dep3;
    }

    public void setDep3 (Dep3 dep3)
    {
        this.dep3 = dep3;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", dep1 = "+dep1+", dep2 = "+dep2+", dep3 = "+dep3+"]";
    }
}
