package com.purplepath.purplepath.quickMenu.models;

import java.io.Serializable;

/**
 * Created by bertrandrussellsakthees on 20/03/17.
 */

public class GirdviewText implements Serializable {
    private String myDataText;

    private String myPlanText;

    private int myDataImage;

    private int myPlanImage;

    private String quickmenuText;

    private int quickmenuImage;

    public String getQuickmenuText() {
        return quickmenuText;
    }

    public void setQuickmenuText(String quickmenuText) {
        this.quickmenuText = quickmenuText;
    }

    public int getQuickmenuImage() {
        return quickmenuImage;
    }

    public void setQuickmenuImage(int quickmenuImage) {
        this.quickmenuImage = quickmenuImage;
    }

    public String getMyDataText() {
        return myDataText;
    }

    public void setMyDataText(String myDataText) {
        this.myDataText = myDataText;
    }

    public String getMyPlanText() {
        return myPlanText;
    }

    public void setMyPlanText(String myPlanText) {
        this.myPlanText = myPlanText;
    }

    public int getMyDataImage() {
        return myDataImage;
    }

    public void setMyDataImage(int myDataImage) {
        this.myDataImage = myDataImage;
    }

    public int getMyPlanImage() {
        return myPlanImage;
    }

    public void setMyPlanImage(int myPlanImage) {
        this.myPlanImage = myPlanImage;
    }



}
