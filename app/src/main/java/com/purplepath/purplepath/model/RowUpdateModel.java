package com.purplepath.purplepath.model;

import java.io.Serializable;

/**
 * Created by Bert on 27-Jul-16.
 */
public class RowUpdateModel implements Serializable {

    String rowString;
    String expectedValueString;

    public String getRowString() {
        return rowString;
    }

    public void setRowString(String rowString) {
        this.rowString = rowString;
    }

}
