package com.purplepath.purplepath.user.MyAccount;


import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CountryCodeSpinnerAdapter;
import com.purplepath.purplepath.dialog.HomeAddressDialogFragment;
import com.purplepath.purplepath.dialog.HomeAddressDialogFragmentNew;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.user.MyAccount.getModels.GetProfiledata;
import com.purplepath.purplepath.user.MyAccount.getModels.User_profile_det;
import com.purplepath.purplepath.user.MyAccount.models.ProfileModels;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.BLANK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccount extends BaseFragment implements View.OnClickListener, HomeAddressDialogFragmentNew.OnHomeAddressSetListener {

    OnActivityBackPressedListener backPressedListener;
    private EditText firstname, last_name, ed_email, mobile_number, ed_homeaddress, aliasname;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;

    String mfirstname, mLastname, mAliasname, mEmailId, mHomeaddress, mMobileNo, mcountry_code,
            ah_city = "", ah_state = "", ah_country = "", ah_zipcode = "", mAddressHome = "";

    GetProfiledata mGetProfiledata;

    ArrayList<User_profile_det> user_profile_det;

    private FloatingActionButton madd_floating_button_Id;

    private DialogFragment newFragment;

    private EditText mCalanderEdt;

    private Spinner mlistSpinnerPhoneCode;

    private ArrayList countryArrayList, countryCodeArrayList;

//    String mSpinnerPhoneCodeValue;

    private JSONArray m_jArry;

    public String ah_resno = "", ah_resname = "", ah_street = "", ah_area = "";

    Boolean isSignUp = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        backPressedListener = (OnActivityBackPressedListener) mContext;
        MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        backPressedListener.setActionBarTitle("Profile");
        firstname = view.findViewById(R.id.firstname);
        last_name = view.findViewById(R.id.last_name);
        ed_email = view.findViewById(R.id.ed_email);
        mobile_number = view.findViewById(R.id.mobile_number);
        ed_homeaddress = view.findViewById(R.id.ed_homeaddress);
        aliasname = view.findViewById(R.id.aliasView_name);
        madd_floating_button_Id = view.findViewById(R.id.add_floating_button_Id);
        mlistSpinnerPhoneCode = view.findViewById(R.id.mobileNo_code);
        UtileKit.setSvgEdittextDrawableLeft(firstname, mContext, R.drawable.ic_user_icon);
        UtileKit.setSvgEdittextDrawableLeft(last_name, mContext, R.drawable.ic_user_icon);
        UtileKit.setSvgEdittextDrawableLeft(aliasname, mContext, R.drawable.ic_user_icon);
        UtileKit.setSvgEdittextDrawableLeft(ed_email, mContext, R.drawable.ic_email_svg);
        UtileKit.setSvgEdittextDrawableLeft(ed_homeaddress, mContext, R.drawable.ic_address_icon);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        madd_floating_button_Id.setOnClickListener(this);
        ed_homeaddress.setOnClickListener(this);
        ed_homeaddress.setInputType(InputType.TYPE_NULL);

        if (mGetProfiledata != null) {
            getProfileData(user_profile_det);
        } else {
            callgetProfileService();
        }

        firstname.setSelection(firstname.getText().length());
        last_name.setSelection(last_name.getText().length());
        ed_email.setSelection(ed_email.getText().length());
        aliasname.setSelection(aliasname.getText().length());
        mobile_number.setSelection(mobile_number.getText().length());
        ed_homeaddress.setSelection(ed_homeaddress.getText().length());

        setCountryCode();
        CountryCodeSpinnerAdapter adapter_state = new CountryCodeSpinnerAdapter(mContext, countryArrayList);
        mlistSpinnerPhoneCode.setAdapter(adapter_state);


        mlistSpinnerPhoneCode
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // TODO Auto-generated method stub
//                        String  mCodeValue =  mlistSpinnerPhoneCode.getSelectedItem().toString();
                        mcountry_code = "" + countryCodeArrayList.get(position);
//                        mcountry_code = getSelectedValue(mCodeValue);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });

//        mfirstname = UtileKit.getPersistedPurplePathPref("mobilenumbergot");
//        mSpinnerPhoneCodeValue = UtileKit.getPersistedPurplePathPref("countrycode");
//        if(validateObjectValues(mfirstname)){
//            firstname.setText(mfirstname);
//        }
        return view;
    }

    private void setEdtText(EditText edttext) {
        this.mCalanderEdt = edttext;
    }

    private EditText getEdtText() {
        return mCalanderEdt;
    }

    private void callgetProfileService() {
        try {
            user_profile_det = new ArrayList<User_profile_det>();
            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
            Call<GetProfiledata> call = webServiceObj.callgetProfileService(UtileKit.getPersistedPurplePathPref("user_id"));
            call.enqueue(new Callback<GetProfiledata>() {
                @Override
                public void onResponse(Call<GetProfiledata> call, Response<GetProfiledata> response) {
                    Log.i("CallBack", " response is " + response.toString());
                    mGetProfiledata = response.body();
                    user_profile_det = mGetProfiledata.getData().getUser_profile_det();

                    getProfileData(user_profile_det);
                }

                @Override
                public void onFailure(Call<GetProfiledata> call, Throwable t) {
                    //Log.e("CallBack", " failure is " + t);
                    UtileKit.alertRetrofitExceptionDialog(mContext, t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getProfileData(ArrayList<User_profile_det> user_profile_det) {
        String mComplete_address = null;
        mfirstname = user_profile_det.get(0).getName();
        mLastname = user_profile_det.get(0).getLast_name();
        mEmailId = user_profile_det.get(0).getEmail();
        mAddressHome = user_profile_det.get(0).getAddress_home();
        mMobileNo = user_profile_det.get(0).getPhone();
        String getcountry_code = user_profile_det.get(0).getCountry_code();
        ah_city = user_profile_det.get(0).getAh_city();
        ah_state = user_profile_det.get(0).getAh_state();
        ah_country = user_profile_det.get(0).getAh_country();
        ah_zipcode = user_profile_det.get(0).getAh_zipcode();
        mAliasname = user_profile_det.get(0).getAlias_name();


        ah_resno = user_profile_det.get(0).getAh_res_no();
        ah_resname = user_profile_det.get(0).getAh_res_name();
        ah_street = user_profile_det.get(0).getAh_road_street();
        ah_area = user_profile_det.get(0).getAh_locality_area();

        //mComplete_address = mAddressHome+ ","+ ah_city +","+ ah_state +","+ah_country +","+ah_zipcode;

        mComplete_address = ah_resno + "," + ah_resname + "," + ah_street + "," + ah_area + "," + "," + ah_city + "," + ah_state + "," + ah_country + "," + ah_zipcode;

        if (UtileKit.validateObjectValues(mfirstname)) {
            setEditTextvalue(firstname, mfirstname);
        } else {
            mfirstname = UtileKit.getPersistedPurplePathPref("name_services");
            setEditTextvalue(firstname, mfirstname);
        }
        setEditTextvalue(last_name, mLastname);
        setEditTextvalue(aliasname, mAliasname);
        setEditTextvalue(ed_email, mEmailId);
        if (UtileKit.validateObjectValues(mComplete_address)) {
            setEditTextvalue(ed_homeaddress, mComplete_address.replace(",,,,", ""));
        } else {
            setEditTextvalue(ed_homeaddress, mComplete_address);
        }

        setEditTextvalue(mobile_number, mMobileNo);

        mcountry_code = getSelectedValue(getcountry_code);
        int status = getStringArraySpinnerposition(mcountry_code, countryCodeArrayList);
        mlistSpinnerPhoneCode.setSelection(status);
    }

    private String validatePhoneNumber(String number) {
        String getnumber;
        if (UtileKit.validateObjectValues(number)) {
            getnumber = number;
        } else {
            getnumber = "India".concat(" ").concat("+91").concat(" ").concat("IN");
        }
        return getnumber;
    }

    private void setEditTextvalue(EditText meditText, String prefValue) {

        if (UtileKit.validateObjectValues(prefValue)) {
            meditText.setText(prefValue);
            meditText.setSelection(meditText.getText().length());
        } else {
            meditText.setText(BLANK);
        }
    }

    private void callUpdateProfileService(String mfirstname, String mLastname, String mEmailId, String mcountry_code, String mMobileNo,
                                          String mHomeaddress, String ah_city, String ah_state, String ah_country,
                                          String ah_zipcode, String mAliasname,
                                          String resno, String resname, String street, String area) {

        try {
            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
            Call<ProfileModels> call = webServiceObj.callUpdateProfileService(UtileKit.getPersistedPurplePathPref("user_id"),
                    mfirstname, mLastname, mEmailId, mcountry_code, mMobileNo, mHomeaddress, ah_city, ah_state, ah_country,
                    ah_zipcode, mAliasname, resno, resname, street, area);
            call.enqueue(new Callback<ProfileModels>() {
                @Override
                public void onResponse(Call<ProfileModels> call, Response<ProfileModels> response) {
                    Log.i("MyAccount", " response is " + response.toString());
                    if (response.body().getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        backPressedListener.onActivityBackPressed();
                    } else {
                        UtileKit.intitializeAlertDialog(response.message(), mContext);
                    }
                }

                @Override
                public void onFailure(Call<ProfileModels> call, Throwable t) {
                    //Log.e("MyAccount", " failure is " + t);
                    UtileKit.alertRetrofitExceptionDialog(mContext, t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.add_floating_button_Id:
                try {
                    mfirstname = firstname.getText().toString();
                    mLastname = last_name.getText().toString();
                    mEmailId = ed_email.getText().toString();
                    mMobileNo = mobile_number.getText().toString();
                    mAliasname = aliasname.getText().toString();
                    mcountry_code = validatePhoneNumber(mcountry_code);
                    if (UtileKit.validateObjectValues(mfirstname) && UtileKit.validate_character(mfirstname)) {
                        if (UtileKit.validateObjectValues(mLastname) && UtileKit.validate_character(mLastname)) {
                            if (UtileKit.validateEmail(mEmailId)) {
                                if (UtileKit.validateObjectValues(mcountry_code)) {
                                    if (UtileKit.validateObjectValues(mMobileNo)) {
                                        if (UtileKit.validateObjectValues(mAliasname) && UtileKit.validate_character(mAliasname)) {
                                            callUpdateProfileService(mfirstname, mLastname, mEmailId, mcountry_code,
                                                    mMobileNo, mAddressHome, ah_city, ah_state, ah_country, ah_zipcode, mAliasname,
                                                    ah_resno, ah_resname, ah_street, ah_area);
                                        } else {
                                            UtileKit.intitializeAlertDialog("Alias Name is invalid", mContext);
                                        }
                                    } else {
                                        UtileKit.intitializeAlertDialog("Phone Number is Empty", mContext);
                                    }
                                } else {
                                    UtileKit.intitializeAlertDialog("Country code is Empty", mContext);
                                }
                            } else {
                                UtileKit.intitializeAlertDialog("E-mail id is not valid", mContext);
                            }

                        } else {
                            UtileKit.intitializeAlertDialog("Last name is invalid", mContext);
                        }

                    } else {
                        UtileKit.intitializeAlertDialog("First name is invalid", mContext);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case R.id.ed_homeaddress: {
                try {
                    showPopupDialog(mAddressHome, ah_city, ah_state, ah_country, ah_zipcode,
                            ah_resno, ah_resname, ah_street, ah_area);
                    setEdtText(ed_homeaddress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            break;
        }

    }


    private void showPopupDialog(String address, String city, String state, String country, String zipcode,
                                 String resno, String resname, String street, String area) {
        if (newFragment != null && newFragment.getDialog() != null) {
            if (newFragment.getDialog().isShowing()) {
            }
        }
        if (newFragment != null && newFragment.getDialog() != null && newFragment.getDialog().isShowing()) {
        } else {
            FragmentManager fm = ((FragmentActivity) mContext).getFragmentManager();
            newFragment = HomeAddressDialogFragmentNew.newInstance(this,
                    address, city, state, country, zipcode, resno, resname, street, area);
            newFragment.show(fm, "dialog");
        }
    }

    @Override
    public void onHomeAddressSet(String address, String city, String state, String country, String zipcode, String resno, String resname, String street, String area) {

        if (getEdtText() == ed_homeaddress) {
            mAddressHome = address;
            ah_city = city;
            ah_state = state;
            ah_country = country;
            ah_zipcode = zipcode;

            ah_resno = resno;
            ah_resname = resname;
            ah_street = street;
            ah_area = area;

            getEdtText().setText(ah_resno + "\n " + ah_resname + "\n" + ah_street + "\n" + ah_area + "\n " + ah_city + " \n" + ah_state
                    + " \n" + ah_country + "\n " + ah_zipcode);

        }
    }


    private void setCountryCode() {
        try {
            m_jArry = new JSONArray(loadJSONFromAsset());
            countryArrayList = new ArrayList<String>();
            countryCodeArrayList = new ArrayList<String>();

            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject obj = m_jArry.getJSONObject(i);

                countryArrayList.add(obj
                        .getString("name")
                        .concat(" ")
                        .concat(obj.getString("dial_code").concat(" ")
                                .concat(obj.getString("code"))));

                countryCodeArrayList.add(obj.getString("dial_code"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("countrycode.json");
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

    private String getSelectedValue(String countryname) {
        String countryCode = null;
        try {
            for (int i = 0; i < countryArrayList.size(); i++) {
                if (countryname.equalsIgnoreCase(countryCodeArrayList.get(i).toString())) {
                    countryCode = countryCodeArrayList.get(i).toString();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return countryCode;
        }
        return countryCode;
    }

    private int getStringArraySpinnerposition(String value, ArrayList spinerlist) {
        int pos = 0;
        try {
            if (value != null) {
                for (int i = 0; i < spinerlist.size(); i++) {
                    try {

                        if (value.equalsIgnoreCase(countryCodeArrayList.get(i).toString())) {
                            pos = i;
//                    mcountry_code =spinerlist.get(i).toString();
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pos;
    }


}
