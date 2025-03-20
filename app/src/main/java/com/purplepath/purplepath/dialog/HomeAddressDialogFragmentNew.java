package com.purplepath.purplepath.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.finobot.finobot.R;
import com.purplepath.purplepath.customview.CountryCodeSpinnerAdapter;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.finobot.finobot.R.id.product_spinner;

/**
 * Created by pravinr on 3/26/18.
 */

public class HomeAddressDialogFragmentNew extends DialogFragment implements AdapterView.OnItemSelectedListener {

    private Context mContext;
    private Activity activity;
    DisplayMetrics displaymetrics = new DisplayMetrics();
    private OnHomeAddressSetListener onHomeAddressSetListener;
    private EditText mhomeAddress_Edt,  mcity_Edt, mstate_Edt, mcountry_Edt, mzipcode_Edt;
    private String homeAddressValues, cityValue, stateValue, countryValue, zipcodeValue;
    private String maddress, mcity, mstate, mcountry, mzipcode;

    private EditText resNo_Edt,resName_Edt,street_Edt,area_Edt;
    private String mresNo, mresName, mstreet, marea;

    private Spinner state_spinner,country_spinner;

    private String str_state_spinner,str_country_spinner;

    private JSONArray m_jArry;
    private ArrayList stateArrayList;
    private ArrayList countryArrayList;

    public static HomeAddressDialogFragmentNew newInstance(OnHomeAddressSetListener onhomeaddressSetListener
            ,String address, String city, String state, String country, String zipcode,
                                                           String resno,String resname,String street,String area) {
        HomeAddressDialogFragmentNew homeAddressDialogFragmentNew = new HomeAddressDialogFragmentNew();
        homeAddressDialogFragmentNew.onHomeAddressSetListener = onhomeaddressSetListener;
        homeAddressDialogFragmentNew.maddress =address;
        homeAddressDialogFragmentNew.mcity =city;
        homeAddressDialogFragmentNew.mstate=state;
        homeAddressDialogFragmentNew.mcountry =country;
        homeAddressDialogFragmentNew.mzipcode=zipcode;


        homeAddressDialogFragmentNew.mresNo =resno;
        homeAddressDialogFragmentNew.mresName=resname;
        homeAddressDialogFragmentNew.mstreet =street;
        homeAddressDialogFragmentNew.marea=area;



        return homeAddressDialogFragmentNew;
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
        mContext = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog_homeaddresss, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        mcity_Edt = v.findViewById(R.id.city_Edt);
        mcity_Edt.setText(mcity);
        mcity_Edt.setSelection(mcity_Edt.getText().length());

        mzipcode_Edt = v.findViewById(R.id.zipcode_Edt);
        mzipcode_Edt.setText(mzipcode);
        mzipcode_Edt.setSelection(mzipcode_Edt.getText().length());


        state_spinner= v.findViewById(R.id.state_spinner);
        state_spinner.setOnItemSelectedListener(this);

        country_spinner= v.findViewById(R.id.country_spinner);
        country_spinner.setOnItemSelectedListener(this);


        resNo_Edt= v.findViewById(R.id.resNo_Edt);
        resNo_Edt.setText(mresNo);
        resNo_Edt.setSelection(resNo_Edt.getText().length());

        resName_Edt= v.findViewById(R.id.resName_Edt);
        resName_Edt.setText(mresName);
        resName_Edt.setSelection(resName_Edt.getText().length());

        street_Edt= v.findViewById(R.id.street_Edt);
        street_Edt.setText(mstreet);
        street_Edt.setSelection(street_Edt.getText().length());

        area_Edt= v.findViewById(R.id.area_Edt);
        area_Edt.setText(marea);
        area_Edt.setSelection(area_Edt.getText().length());



        setStateCode();
        setCountryCode();

        setSpinnerAdapter(state_spinner,stateArrayList,mContext);
        setSpinnerAdapter(country_spinner,countryArrayList,mContext);

        state_spinner.setSelection(getSpinnerAssetposition(mstate,stateArrayList));
        country_spinner.setSelection(getSpinnerAssetposition(mcountry,countryArrayList));




        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0) {
                    mstate = parent.getItemAtPosition(position).toString();



                }else {
                    mstate="";

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0) {
                    mcountry = parent.getItemAtPosition(position).toString();

                }else {
                    mcountry="";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



//        CountryCodeSpinnerAdapter adapter_state = new CountryCodeSpinnerAdapter(mContext, stateArrayList);
//        state_spinner.setAdapter(adapter_state);

//        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent,View view, int position, long id) {
//                // TODO Auto-generated method stub
//                String  mCodeValue =  state_spinner.getSelectedItem().toString();
//                str_state_spinner = getSelectedValue(mCodeValue);
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });

//        CountryCodeSpinnerAdapter adapter_country = new CountryCodeSpinnerAdapter(mContext, countryArrayList);
//        country_spinner.setAdapter(adapter_country);
//
//        country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent,View view, int position, long id) {
//                // TODO Auto-generated method stub
//                String  mCodeValue =  country_spinner.getSelectedItem().toString();
//                str_country_spinner = getSelectedValues(mCodeValue);
//
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });



        FloatingActionButton fab = v.findViewById(R.id.personal_address_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputValues();

                onHomeAddressSetListener.onHomeAddressSet(homeAddressValues, cityValue, mstate, mcountry,
                        zipcodeValue,mresNo,mresName,mstreet,marea);
                dismiss();
            }
        });



        return v;
    }
    private int getSpinnerAssetposition(String value, ArrayList<String> spinerlist) {
        int pos = 0;
        for (int j = 0; j < spinerlist.size(); j++) {
            if (value.equalsIgnoreCase(spinerlist.get(j))) {
                return j + 1;
            }
        }
        return pos;
    }
    private void getInputValues(){

        cityValue = mcity_Edt.getText().toString().trim();
        zipcodeValue =  mzipcode_Edt.getText().toString().trim();

        mresNo = resNo_Edt.getText().toString().trim();
        mresName = resName_Edt.getText().toString().trim();
        mstreet =  street_Edt.getText().toString().trim();
        marea =  area_Edt.getText().toString().trim();


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public interface OnHomeAddressSetListener {
        void onHomeAddressSet(String address, String city, String state, String country, String zipcode,
                              String resno,String resname,String street,String area);
    }



    private String getSelectedValue(String countryname){
        String countryCode= null;
        for(int i=0; i<stateArrayList.size(); i++) {
            if(countryname.equalsIgnoreCase(stateArrayList.get(i).toString())){
                countryCode =  stateArrayList.get(i).toString();
                break;
            }
        }
        return countryCode;
    }

    private String getSelectedValues(String countryname){
        String countryCode= null;
        for(int i=0; i<countryArrayList.size(); i++) {
            if(countryname.equalsIgnoreCase(countryArrayList.get(i).toString())){
                countryCode =  countryArrayList.get(i).toString();
                break;
            }
        }
        return countryCode;
    }



    public void setSpinnerAdapter(Spinner mMyMartialSpinner, ArrayList<String> mystringList, Context mycontext) {
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("");
        for (String s : mystringList) {
            stringList.add(s);
        }
        CustomSpinerAdapter adapter_state = new CustomSpinerAdapter(mycontext, stringList);
        mMyMartialSpinner.setAdapter(adapter_state);

    }


    private void setStateCode(){
        try {
            m_jArry = new JSONArray(loadJSONFromAssetStates());
            stateArrayList = new ArrayList<String>();

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject obj = m_jArry.getJSONObject(i);

                stateArrayList.add(obj.getString("state_name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String loadJSONFromAssetStates() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("statenamelist.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }




    private void setCountryCode(){
        try {
            m_jArry = new JSONArray(loadJSONFromAssetCountry());
            countryArrayList = new ArrayList<String>();

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject obj = m_jArry.getJSONObject(i);

                countryArrayList.add(obj.getString("country_name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAssetCountry() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("countrynamelist.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }



}