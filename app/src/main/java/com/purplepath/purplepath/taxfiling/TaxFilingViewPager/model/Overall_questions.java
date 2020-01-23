package com.purplepath.purplepath.taxfiling.TaxFilingViewPager.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by pravinr on 8/10/18.
 */

public class Overall_questions implements Serializable{

    private ArrayList<PersonalInformation> PersonalInformation;

    private ArrayList<FillingStatus> FillingStatus;

    private ArrayList<Address> Address;

    private ArrayList<BankDetails> BankDetails;

    public ArrayList<PersonalInformation> getPersonalInformation() {
        return PersonalInformation;
    }
    public void setPersonalInformation(ArrayList<PersonalInformation> personalInformation) {
        PersonalInformation = personalInformation;
    }

    public ArrayList<FillingStatus> getFillingStatus() {
        return FillingStatus;
    }

    public void setFillingStatus(ArrayList<FillingStatus> fillingStatus) {
        FillingStatus = fillingStatus;
    }

    public ArrayList<Address> getAddress() {
        return Address;
    }

    public void setAddress(ArrayList<Address> address) {
        Address = address;
    }

    public ArrayList<BankDetails> getBankDetails() {
        return BankDetails;
    }

    public void setBankDetails(ArrayList<BankDetails> bankDetails) {
        BankDetails = bankDetails;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PersonalInformation = "+PersonalInformation+", FillingStatus = "+FillingStatus+", Address = "+Address+", BankDetails = "+BankDetails+"]";
    }
}

