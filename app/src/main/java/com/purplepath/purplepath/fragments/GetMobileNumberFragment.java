package com.purplepath.purplepath.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.AppManagement.TermsAndConditions.TermsAndConditionFragment;
import com.purplepath.purplepath.AppManagement.privacy.PrivacyPolicyFragment;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CountryCodeSpinnerAdapter;
import com.purplepath.purplepath.instuctionScreen.InstructionScreenOne;
import com.purplepath.purplepath.model.Restricted_menus;
import com.purplepath.purplepath.model.SignUpModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.persistingPurplePathPref;

/**
 * Created by Suresh on 24/07/17.
 */

public class GetMobileNumberFragment extends BaseFragment implements View.OnClickListener{

    private Context mContext;

    private Spinner mlistSpinnerPhoneCode;

    String mSpinnerPhoneCodeValue;

    private ArrayList countryArrayList;

    private JSONArray m_jArry;

    private ArrayList countryCodeArrayList;

    private Button bt_signup;

    private EditText meditTextMobileno,msignup_email_id;

    private LinearLayout memail_linear_layout;

    String name,mFlag,email,password,id;
    private  ArrayList<String> menuList;


    private TextView txt_terms;
    private CheckBox terms_checkbox;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        menuList=new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.menuitem)));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View signupView = inflater.inflate(R.layout.get_mobile_number_fragment,container, false);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Signup");
        mlistSpinnerPhoneCode = signupView.findViewById(R.id.mobileNo_code);

        meditTextMobileno = signupView.findViewById(R.id.signup_mobile_no);

        msignup_email_id= signupView.findViewById(R.id.signup_email_id);
        memail_linear_layout= signupView.findViewById(R.id.email_linear_layout);


        terms_checkbox= signupView.findViewById(R.id.terms_checkbox);
        terms_checkbox.setOnClickListener(this);
        txt_terms= signupView.findViewById(R.id.txt_terms);

        ClickableSpan termsOfServicesClick = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                addFragmenttoStack(TermsAndConditionFragment.newInstance(false));
            }
        };

        ClickableSpan privacyPolicyClick = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                addFragmenttoStack(new PrivacyPolicyFragment().newInstance(false));
            }
        };

        makeLinks(txt_terms, new String[] { "Terms and Conditions", "Privacy Policy" }, new ClickableSpan[] {
                termsOfServicesClick, privacyPolicyClick
        });


        if(email.isEmpty()){
            memail_linear_layout.setVisibility(View.VISIBLE);
        }else {
            memail_linear_layout.setVisibility(View.GONE);
        }

        meditTextMobileno.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    if (i == KeyEvent.KEYCODE_ENTER) {
                        String getMobileNumber = meditTextMobileno.getText().toString();

                        if(getMobileNumber.length()==10 && !getMobileNumber.isEmpty() && getMobileNumber!= null){

                            if(name!= null && email!= null && password!= null && id!= null ){
//                                //here term of condition
                                if(terms_checkbox.isChecked()==true){

                                callSignUpSocialMediaService(id, email, password, name, mFlag, getMobileNumber, mSpinnerPhoneCodeValue);
                                    //here term of condition
                                } else {
                                    UtileKit.intitializeAlertDialog(getString(R.string.terms_checkbox1),mContext);
                                }

                            }
                        }else{
                            UtileKit.intitializeAlertDialog(getString(R.string.get_mobile_number),
                                    mContext);
                        }

                        try {
                            persistingPurplePathPref("firsttimeLogin", "isSuccess");
                            String mLoginBtnChanged = UtileKit.getPersistedPurplePathPref("firsttimeLogin");
                            Log.i("GuideViewFragment","mLoginBtnChanged " + mLoginBtnChanged);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                return false;
            }
        });

        bt_signup = signupView.findViewById(R.id.bt_signup);

        setCountryCode();
        CountryCodeSpinnerAdapter adapter_state = new CountryCodeSpinnerAdapter(mContext, countryArrayList);
        mlistSpinnerPhoneCode.setAdapter(adapter_state);

        mlistSpinnerPhoneCode
                .setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        // TODO Auto-generated method stub
                        String  mCodeValue =  mlistSpinnerPhoneCode.getSelectedItem().toString();
                        mSpinnerPhoneCodeValue = getSelectedValue(mCodeValue);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });


        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getMobileNumber = meditTextMobileno.getText().toString();
                if(memail_linear_layout.getVisibility()==View.VISIBLE)
                 email=msignup_email_id.getText().toString();


                if(getMobileNumber.length()==10 && !getMobileNumber.isEmpty() && getMobileNumber!= null){

                    if(!email.isEmpty()) {
                        if (UtileKit.validateObjectValues(email)) {
                            if (UtileKit.validateEmail(email)) {

                                if (name != null && email != null && password != null && id != null) {

                                    //here term of condition
                                    if (terms_checkbox.isChecked() == true) {
                                        callSignUpSocialMediaService(id, email, password, name, mFlag, getMobileNumber, mSpinnerPhoneCodeValue);
                                        //here term of condition
                                    } else {
                                        UtileKit.intitializeAlertDialog(getString(R.string.terms_checkbox1), mContext);
                                    }

                                }
                            }
                            else{
                                UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_email_val_msg),mContext);
                            }
                        } else{
                            UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_email_val_msg),mContext);
                        }
                    }
                    else{
                        UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_enter_email),mContext);
                    }
                }
                  else{
                    UtileKit.intitializeAlertDialog(getString(R.string.get_mobile_number),mContext);
                }

                try {
                    persistingPurplePathPref("firsttimeLogin", "isSuccess");
                    String mLoginBtnChanged = UtileKit.getPersistedPurplePathPref("firsttimeLogin");
                    Log.i("GuideViewFragment","mLoginBtnChanged " + mLoginBtnChanged);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        return signupView;
    }
    public void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        SpannableString spannableString = new SpannableString(textView.getText());
        for (int i = 0; i < links.length; i++) {
            ClickableSpan clickableSpan = clickableSpans[i];
            String link = links[i];

            int startIndexOfLink = textView.getText().toString().indexOf(link);
            spannableString.setSpan(clickableSpan, startIndexOfLink, startIndexOfLink + link.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
    }

    private String getSelectedValue(String countryname){
        String countryCode= null;
        for(int i=0; i<countryCodeArrayList.size(); i++) {
            if(countryname.equalsIgnoreCase(countryArrayList.get(i).toString())){
                countryCode =  countryCodeArrayList.get(i).toString();
                break;
            }
        }
        return countryCode;
    }

    private void setCountryCode(){
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

    public static GetMobileNumberFragment newInstance(String social_media_id, String mMailID, String mPassword, String name, String Flag) {
        GetMobileNumberFragment fragment=new GetMobileNumberFragment();
        fragment.name = name;
        fragment.mFlag = Flag;
        fragment.password = mPassword;
        fragment.id = social_media_id;
        fragment.email = mMailID;
        return fragment;
    }



    private void callSignUpSocialMediaService(String mMediaId, final String mMailID, String mPassword, final String mFullName, String mFlag, final String getMobileNumber, final String mSpinnerPhoneCodeValue) {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<SignUpModel> call = webServiceObj.callRegisterSocialMediaService(mMediaId, mFullName, mMailID, mPassword,
                mFlag,getMobileNumber,mSpinnerPhoneCodeValue,"1");
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                SignUpModel signupModel = response.body();
                if (signupModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                   setRestricted_menus(signupModel.getData().getRestricted_menus());
                    UtileKit.createSharedPreference(getActivity(), signupModel.getData().getUser_id());
//                   UtileKit.C signupModel.getData().getUser_id();
                    persistingPurplePathPref("name_services",mFullName);
                    persistingPurplePathPref("email_service",mMailID);

                    //taking paid type
                    UtileKit.persistingPurplePathPref("paid_type",signupModel.getData().getUser_details().getUser_paid_type());

                    UtileKit.persistingPurplePathPref("cust_id",signupModel.getData().getCust_id());


                    UtileKit.persistingPurplePathPrefFirsttime("mobilenumbergot",getMobileNumber);
                    UtileKit.persistingPurplePathPrefFirsttime("countrycode",mSpinnerPhoneCodeValue);
                    Bundle args = new Bundle();
                    args.putString("userid", signupModel.getData().getUser_id());
                    args.putBoolean("IsSignUp", true);
                    InstructionScreenOne fragment = new InstructionScreenOne();
                    fragment.setArguments(args);
                    addFragmenttoStack(fragment);
                }
                else {
                    UtileKit.intitializeAlertDialog(signupModel.getData().getMessage(),
                            mContext);
                }
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                persistingPurplePathPref("firsttimeLogin","isFailed");
            }
        });
    }
    /**
     * enable menu in list
     * @param restricted_menus
     */
    private void setRestricted_menus(ArrayList<Restricted_menus> restricted_menus) {
        try {
            for (String menu : menuList){
                persistingPurplePathPref(menu, true);
            }
            for (String menu : menuList) {
                for(Restricted_menus obj : restricted_menus){
                    if (obj.getMenu_name().equalsIgnoreCase(menu)) {
                        persistingPurplePathPref(menu, false);
                    }
                }


            }
        }catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        catch (Exception e1){
            e1.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {

    }
}
