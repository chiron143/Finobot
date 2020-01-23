package com.purplepath.purplepath.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.finobot.finobot.R;

public class HomeAddressDialogFragment extends DialogFragment {

    DisplayMetrics displaymetrics = new DisplayMetrics();
    private OnHomeAddressSetListener onHomeAddressSetListener;
    private EditText mhomeAddress_Edt,  mcity_Edt, mstate_Edt, mcountry_Edt, mzipcode_Edt;
    private String homeAddressValues, cityValue, stateValue, countryValue, zipcodeValue;
    private String maddress, mcity, mstate, mcountry, mzipcode;


    public static HomeAddressDialogFragment newInstance(OnHomeAddressSetListener onhomeaddressSetListener
    ,String address, String city, String state, String country, String zipcode) {
        HomeAddressDialogFragment homeAddressDialogFragment = new HomeAddressDialogFragment();
        homeAddressDialogFragment.onHomeAddressSetListener = onhomeaddressSetListener;
        homeAddressDialogFragment.maddress =address;
                homeAddressDialogFragment.mcity =city;
                        homeAddressDialogFragment.mstate=state;
                homeAddressDialogFragment.mcountry =country;
                homeAddressDialogFragment.mzipcode=zipcode;
        return homeAddressDialogFragment;
    }


    @Override
    public void onStart()
    {
        super.onStart();
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        double w = width*0.85;
        double h = height*0.85;
        int dialogwidth = (int)(w);
        int dialogheight = (int)(h);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_homeaddress, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mhomeAddress_Edt = v.findViewById(R.id.homeAddress_Edt);
        mhomeAddress_Edt.setText(maddress);
        mhomeAddress_Edt.setSelection(mhomeAddress_Edt.getText().length());
        mcity_Edt = v.findViewById(R.id.city_Edt);
        mcity_Edt.setText(mcity);
        mcity_Edt.setSelection(mcity_Edt.getText().length());
        mstate_Edt = v.findViewById(R.id.state_Edt);
        mstate_Edt.setText(mstate);
        mstate_Edt.setSelection(mstate_Edt.getText().length());
        mcountry_Edt = v.findViewById(R.id.country_Edt);
        mcountry_Edt.setText(mcountry);
        mcountry_Edt.setSelection(mcountry_Edt.getText().length());
        mzipcode_Edt = v.findViewById(R.id.zipcode_Edt);
        mzipcode_Edt.setText(mzipcode);
        mzipcode_Edt.setSelection(mzipcode_Edt.getText().length());
        FloatingActionButton fab = v.findViewById(R.id.personal_address_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputValues();
                onHomeAddressSetListener.onHomeAddressSet(homeAddressValues, cityValue, stateValue, countryValue, zipcodeValue);
                dismiss();
            }
        });
        return v;
    }

    private void getInputValues(){

                homeAddressValues = mhomeAddress_Edt.getText().toString().trim() ;
                cityValue = mcity_Edt.getText().toString().trim();
                stateValue = mstate_Edt.getText().toString().trim();
                countryValue =  mcountry_Edt.getText().toString().trim();
                zipcodeValue =  mzipcode_Edt.getText().toString().trim();
    }



    public interface OnHomeAddressSetListener {
        void onHomeAddressSet(String address, String city, String state, String country, String zipcode);
    }


}