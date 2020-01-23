package com.purplepath.purplepath.expensesRedesign.expensesredesignmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Data implements Serializable
{

    @SerializedName("message")
    private String message;

    @SerializedName("user_expense")
    private ArrayList<User_expense> user_expense;

    public ArrayList<User_expense> getUser_expense ()
    {
        return user_expense;
    }

    public String getMessage() {
        return message;
    }


}