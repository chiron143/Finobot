package com.purplepath.purplepath.investmentPlan.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Pratheep.S on 20-07-2017.
 */

public class Data implements Serializable {

    private String message;

    private ArrayList<Emp_ben> emp_ben;

    private ArrayList<Liq> liq;

    private ArrayList<Fix_inc> fix_inc;

    private ArrayList<Real_prop> real_prop;

    private ArrayList<Equ> equ;

    private ArrayList<Comm_gold> comm_gold;

    private ArrayList<Hou_asst> hou_asst;

    private ArrayList<Oth_asst> oth_asst;

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public ArrayList<Emp_ben> getEmp_ben ()
    {
        return emp_ben;
    }

    public void setEmp_ben (ArrayList<Emp_ben> emp_ben)
    {
        this.emp_ben = emp_ben;
    }

    public ArrayList<Liq> getLiq ()
    {
        return liq;
    }

    public void setLiq (ArrayList<Liq> liq)
    {
        this.liq = liq;
    }

    public ArrayList<Fix_inc> getFix_inc ()
    {
        return fix_inc;
    }

    public void setFix_inc (ArrayList<Fix_inc> fix_inc)
    {
        this.fix_inc = fix_inc;
    }

    public ArrayList<Real_prop> getReal_prop ()
    {
        return real_prop;
    }

    public void setReal_prop (ArrayList<Real_prop> real_prop)
    {
        this.real_prop = real_prop;
    }

    public ArrayList<Equ> getEqu ()
    {
        return equ;
    }

    public void setEqu (ArrayList<Equ> equ)
    {
        this.equ = equ;
    }

    public ArrayList<Comm_gold> getComm_gold ()
    {
        return comm_gold;
    }

    public void setComm_gold (ArrayList<Comm_gold> comm_gold)
    {
        this.comm_gold = comm_gold;
    }

    public ArrayList<Hou_asst> getHou_asst ()
    {
        return hou_asst;
    }

    public void setHou_asst (ArrayList<Hou_asst> hou_asst)
    {
        this.hou_asst = hou_asst;
    }

    public ArrayList<Oth_asst> getOth_asst ()
    {
        return oth_asst;
    }

    public void setOth_asst (ArrayList<Oth_asst> oth_asst)
    {
        this.oth_asst = oth_asst;
    }

    HashMap<Integer,ArrayList> classMap=new HashMap<Integer, ArrayList>();

    public void intializeHashMap(){
        classMap.put(0,comm_gold);
        classMap.put(1,emp_ben);
        classMap.put(2,equ);
        classMap.put(3,fix_inc);
        classMap.put(4,hou_asst);
        classMap.put(5,liq);
        classMap.put(6,real_prop);
        classMap.put(7,oth_asst);

    }

    public HashMap getHashmap(){
        return classMap;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [message = "+message+", emp_ben = "+emp_ben+", liq = "+liq+", fix_inc = "+fix_inc+", real_prop = "+real_prop+", equ = "+equ+", comm_gold = "+comm_gold+", hou_asst = "+hou_asst+", oth_asst = "+oth_asst+"]";
    }
}
