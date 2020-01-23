package com.purplepath.purplepath.alertprompt.personalprompt.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dinesh on 16/01/17.
 */
public class Data implements Serializable {
    private ArrayList<String> empty_fields;
    private int page_id;
    private String page_name;
    private String message;

    public ArrayList<String> getEmptyFields() {
        return this.empty_fields;
    }

    public void setEmptyFields(ArrayList<String> empty_fields) {
        this.empty_fields = empty_fields;
    }

    public int getPageId() {
        return this.page_id;
    }

    public void setPageId(int page_id) {
        this.page_id = page_id;
    }

    public String getPageName() {
        return this.page_name;
    }

    public void setPageName(String page_name) {
        this.page_name = page_name;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}