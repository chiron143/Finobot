package com.purplepath.purplepath.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.purplepath.purplepath.AppManagement.TermsAndConditions.TermsAndConditionFragment;
import com.purplepath.purplepath.AppManagement.privacy.PrivacyPolicyFragment;
import com.purplepath.purplepath.apputiles.AppConstants;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.CountryCodeSpinnerAdapter;
import com.purplepath.purplepath.customview.CustomSpinerAdapter;
import com.purplepath.purplepath.model.VerificationModel;
import com.purplepath.purplepath.model.companyCategoryModel.CompanyCategorieModel;
import com.purplepath.purplepath.model.companyCategoryModel.Company_categories;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.getTextFromObjects;


public class SignUpFragment extends BaseFragment implements View.OnClickListener, AppConstants, AdapterView.OnItemSelectedListener {
    private Context mContext;
    private EditText mEmailIdEdtTxt, mPasswordEdtTxt, mConfirmPasswordEdtTxt, mFullNameEdtTxt, mMobileEdtTxt;
    private Button mSignUpBtn;
    private JSONArray m_jArry;
    private ArrayList countryArrayList;
    private ArrayList countryCodeArrayList;
    private Spinner mlistSpinnerPhoneCode;
    private Boolean isClicked = false;
    String mSpinnerPhoneCodeValue;
    public static LayoutInflater inflater;
    public static View dialogView;
    public static AlertDialog alertDialog;
    private CheckBox terms_checkbox;
    private TextView txt_terms;
    CompanyCategorieModel companyCategorieModel;
    private Spinner company_categories_spinner;

    private ArrayList<String> companyCategoriesArray = new ArrayList<>();
    private Activity activit;
    private String company_categories;
    ArrayList<Company_categories> mcompany_catogories;

    public SignUpFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View signupView = inflater.inflate(R.layout.fragment_sign_up, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.signup_txt);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mEmailIdEdtTxt = signupView.findViewById(R.id.signup_email_id);
        mPasswordEdtTxt = signupView.findViewById(R.id.signup_password);
        mlistSpinnerPhoneCode = signupView.findViewById(R.id.mobileNo_code);
        mSignUpBtn = signupView.findViewById(R.id.bt_signup);


        company_categories_spinner = signupView.findViewById(R.id.company_categories_spinner);
        company_categories_spinner.setOnItemSelectedListener(this);
        terms_checkbox = signupView.findViewById(R.id.terms_checkbox);
        terms_checkbox.setOnClickListener(this);

        txt_terms = signupView.findViewById(R.id.txt_terms);


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

        makeLinks(txt_terms, new String[]{"Terms and Conditions", "Privacy Policy"}, new ClickableSpan[]{
                termsOfServicesClick, privacyPolicyClick
        });


        mConfirmPasswordEdtTxt = signupView.findViewById(R.id.signup_confirm_password);
        mConfirmPasswordEdtTxt.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    if (i == KeyEvent.KEYCODE_ENTER) {
                        //do what you want
                        String _signUpEmail = getTextFromObjects(mEmailIdEdtTxt);
                        if (terms_checkbox.isChecked() == true) {
                            GetInput();
                        } else {
                            UtileKit.intitializeAlertDialog(getString(R.string.terms_checkbox1), mContext);
                        }
                    }
                return false;
            }
        });
        mFullNameEdtTxt = signupView.findViewById(R.id.signup_fullname);
        mMobileEdtTxt = signupView.findViewById(R.id.signup_mobile_no);

        UtileKit.setSvgEdittextDrawableLeft(mEmailIdEdtTxt, mContext, R.drawable.ic_email_icon);
        UtileKit.setSvgEdittextDrawableLeft(mFullNameEdtTxt, mContext, R.drawable.ic_user_icon_gray);

        mSignUpBtn.setOnClickListener(this);
        setCountryCode();
        CountryCodeSpinnerAdapter adapter_state = new CountryCodeSpinnerAdapter(mContext, countryArrayList);
        mlistSpinnerPhoneCode.setAdapter(adapter_state);

        mlistSpinnerPhoneCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                String mCodeValue = mlistSpinnerPhoneCode.getSelectedItem().toString();
                mSpinnerPhoneCodeValue = getSelectedValue(mCodeValue);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        callGetCompanyCategotiesService();

        /*if (UtileKit.getPersistedPurplePathPref("is_avail").equalsIgnoreCase("Y")) {
            mMobileEdtTxt.setText(UtileKit.getPersistedPurplePathPref("MobileNo"));
            mEmailIdEdtTxt.setText(UtileKit.getPersistedPurplePathPref("EmailId"));
            mFullNameEdtTxt.setText(UtileKit.getPersistedPurplePathPref("Name"));

        }*/


        return signupView;
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    void GetInput() {
        String _signUpEmail = getTextFromObjects(mEmailIdEdtTxt);
        String _signupPwd = getTextFromObjects(mPasswordEdtTxt);
        String _signupconfirmPassword = getTextFromObjects(mConfirmPasswordEdtTxt);
        String _signupfullname = getTextFromObjects(mFullNameEdtTxt);
        String country_code = validatePhoneNumber(mSpinnerPhoneCodeValue);
        UtileKit.persistingPurplePathPref("is_avail", "N");
        String _signupmobieno = getTextFromObjects(mMobileEdtTxt);

        UtileKit.persistingPurplePathPref("EmailId", _signUpEmail);
        UtileKit.persistingPurplePathPref("country_code", country_code);
        if (mMobileEdtTxt.getText().toString().trim()
                .length() == 10) {
            UtileKit.persistingPurplePathPref("MobileNo", _signupmobieno);
            validateUserCredentials(_signUpEmail, _signupPwd, _signupconfirmPassword, _signupfullname, _signupmobieno, country_code);

        } else{
            mMobileEdtTxt.setError("Please Enter Valid Mobile No");
        }



    }

    @Override
    public void onClick(View v) {
        if (v == mSignUpBtn) {
            if (!UtileKit.isNetworkAvailable(MyApplication.getInstance())) {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                if (terms_checkbox.isChecked()) {
                    GetInput();
                } else {
                    UtileKit.intitializeAlertDialog(getString(R.string.terms_checkbox1), mContext);
                }

            }
        }
    }

    @Override
    public void onStop() {
        String _signUpEmail = getTextFromObjects(mEmailIdEdtTxt);
        String _signupfullname = getTextFromObjects(mFullNameEdtTxt);
        String country_code = validatePhoneNumber(mSpinnerPhoneCodeValue);
        String _signupmobieno = getTextFromObjects(mMobileEdtTxt);


        UtileKit.persistingPurplePathPref("MobileNo", _signupmobieno);
        UtileKit.persistingPurplePathPref("EmailId", _signUpEmail);
        UtileKit.persistingPurplePathPref("country_code", country_code);
        UtileKit.persistingPurplePathPref("Name", _signupfullname);
        super.onStop();
    }

    protected void validateUserCredentials(String _signUpEmail,
                                           String _signupPwd, String _signupconfirmPassword,
                                           String _signupfullname, String _signupmobieno, String country_code) {
        // TODO Auto-generated method stub
        if (UtileKit.validateObjectValues(_signupmobieno)) {
            if (UtileKit.validateObjectValues(_signUpEmail)) {
                if (UtileKit.validateEmail(_signUpEmail)) {
                    if (UtileKit.validateObjectValues(_signupfullname)) {
                        if (UtileKit.validateObjectValues(_signupPwd)) {
                            if (UtileKit.validateObjectValues(_signupconfirmPassword)) {
                                if (_signupconfirmPassword.equals(_signupPwd)) {

                                    if (_signupPwd.trim().length() > 2) {
                                        _signupPwd = UtileKit.encryptPwd(_signupPwd);
                                        // if(_termCheckbox.isEmpty()){
                                        if (!isClicked) {
                                            callVerificationCode(_signUpEmail, _signupPwd, _signupfullname, _signupmobieno, country_code);
                                            isClicked = true;
                                        }
                                    } else {
                                        mPasswordEdtTxt.requestFocus();
                                        UtileKit.intitializeAlertDialog(getString(R.string.signup_min_password_lenth_msg), mContext);
                                    }

                                } else {
                                    UtileKit.intitializeAlertDialog(getString(R.string.signup_confirm_password_val_msg), mContext);
                                }
                            } else {
                                UtileKit.intitializeAlertDialog(getString(R.string.signup_confirm_password_val_msg), mContext);
                            }
                        } else {
                            UtileKit.intitializeAlertDialog(getString(R.string.http_login_invalid_pwd), mContext);
                        }
                    } else {
                        UtileKit.intitializeAlertDialog(getString(R.string.signup_full_name_val_msg), mContext);
                    }
                } else {
                    UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_email_val_msg), mContext);
                }
            } else {
                UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_enter_email), mContext);
            }
        } else {
            UtileKit.intitializeAlertDialog(getString(R.string.http_login_invalid_phonenumber), mContext);
        }
    }


    private void callVerificationCode(final String mMailID,
                                      final String mPassword,
                                      final String mFullName,
                                      final String mPhoneNumber,
                                      final String countryCode) {

        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<VerificationModel> call = webServiceObj.GetVerificationCode(mMailID, company_categories, mPhoneNumber, countryCode);
        call.enqueue(new Callback<VerificationModel>() {
            @Override
            public void onResponse(Call<VerificationModel> call, Response<VerificationModel> response) {
                isClicked = false;
                UtileKit.dismisssSpinnerDialog();
                VerificationModel verificationModel = response.body();
                if (verificationModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    CodeVerifyFragment frag = new CodeVerifyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("VerificationCode", verificationModel.getData().getVerify_code());
                    bundle.putString("VerificationCode_mobile", verificationModel.getData().getVerify_code_mobile());
                    bundle.putString("verificationmessage", verificationModel.getData().getMessage());
                    bundle.putString("Email", mMailID);
                    bundle.putString("Password", mPassword);
                    bundle.putString("Fullname", mFullName);
                    bundle.putString("PhoneNumber", mPhoneNumber);
                    bundle.putString("countrycode", countryCode);
                    bundle.putString("company_categories", company_categories);
                    frag.setArguments(bundle);
                    addFragmenttoStack(frag);
                    UtileKit.persistingPurplePathPref("name_services", mFullName);
                    UtileKit.persistingPurplePathPref("email_service", mMailID);
                    UtileKit.persistingPurplePathPref("UserEmailPref", mMailID);

                } else {
                    UtileKit.intitializeAlertDialog(verificationModel.getData().getMessage(), mContext);
                }

            }

            @Override
            public void onFailure(Call<VerificationModel> call, Throwable t) {
                isClicked = false;
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }


    public void callGetCompanyCategotiesService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<CompanyCategorieModel> call = webServiceObj.callGetCompanyCategotiesService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<CompanyCategorieModel>() {
            @Override
            public void onResponse(Call<CompanyCategorieModel> call, Response<CompanyCategorieModel> response) {
                UtileKit.dismisssSpinnerDialog();
                companyCategorieModel = response.body();


                if (companyCategorieModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    mcompany_catogories = companyCategorieModel.getData().getCompany_categories();
                    companyCategoriesArray = new ArrayList<>();
                    for (Company_categories obj : mcompany_catogories) {
                        companyCategoriesArray.add(obj.getCat_name());
                    }

                    setSpinnerAdapter(company_categories_spinner, companyCategoriesArray, mContext);
                    company_categories_spinner.setSelection(1);
                } else {
                    UtileKit.intitializeAlertDialog(companyCategorieModel.getData().getMessage(), mContext);
                }
            }

            @Override
            public void onFailure(Call<CompanyCategorieModel> call, Throwable t) {

                UtileKit.alertRetrofitExceptionDialog(mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
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
        for (int i = 0; i < countryArrayList.size(); i++) {
            if (countryname.equalsIgnoreCase(countryArrayList.get(i).toString())) {
                countryCode = countryArrayList.get(i).toString();
                break;
            }
        }
        return countryCode;
    }


    private String getSelectedValues(String countryname) {
        String countryCode = null;
        for (int i = 0; i < mcompany_catogories.size(); i++) {
            if (countryname.equalsIgnoreCase(mcompany_catogories.get(i).toString())) {
                countryCode = mcompany_catogories.get(i).toString();
                break;
            }
        }
        return countryCode;
    }


    private String validatePhoneNumber(String number) {
        String getnumber;
        if (UtileKit.validateObjectValues(number)) {
            getnumber = number;
        } else {
            getnumber = "+91";
        }
        return getnumber;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            company_categories = mcompany_catogories.get(i - 1).getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }


}
