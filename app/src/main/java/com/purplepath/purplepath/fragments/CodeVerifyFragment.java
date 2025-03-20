package com.purplepath.purplepath.fragments;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import android.text.style.ClickableSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.instuctionScreen.InstructionScreenOne;
import com.purplepath.purplepath.model.Restricted_menus;
import com.purplepath.purplepath.model.SignUpModel;
import com.purplepath.purplepath.model.VerificationModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.persistingPurplePathPref;

/**
 * Created by dinesh  xvalue on 10/29/2014.
 */
public class CodeVerifyFragment extends BaseFragment implements View.OnClickListener {

    //Added by Murali
    ClickableSpan clickableSpan = new ClickableSpan() {
        public void updateDrawState(android.text.TextPaint ds) {
            ds.setUnderlineText(true);
        }

        @Override
        public void onClick(View widget) {
            // TODO Auto-generated method stub
        }
    };
    private TextView verifyStepTxtView = null, verifycodedescId;
    private EditText verifyCodeEdView = null;
    private Button verifyNextBtnView = null;
    private TextView termsCondTxtTextView = null;
    private TextView text_resend = null;
    private Context mContext;
    private String VerificationCode_mobile, mVerificationCode, mVerificationMessage;
    private String mEmail, mPhoneNo, mPassword, mFullName, mcountryCode;
    private ArrayList<String> menuList;
    private String companyCategories;
    private int resend_count = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.menuitem)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View verifyView = inflater.inflate(R.layout.fragment_login_verify_code,
                container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Code Verification");
        verifycodedescId = verifyView.findViewById(R.id.verifycodedescId);
        verifycodedescId.setText("Please enter your 6 digit verification code");
        verifyStepTxtView = verifyView
                .findViewById(R.id.verifyStepId);
        verifyCodeEdView = verifyView
                .findViewById(R.id.verifycodeId);
        verifyNextBtnView = verifyView
                .findViewById(R.id.verifyCodeNextBtnId);
        text_resend = verifyView.findViewById(R.id.text_resend);
        verifyNextBtnView.setOnClickListener(this);
        text_resend.setOnClickListener(this);

        termsCondTxtTextView = verifyView.findViewById(R.id.loginverifytermsCondTxtId);
        mVerificationCode = getArguments().getString("VerificationCode");
        VerificationCode_mobile = getArguments().getString("VerificationCode_mobile");
        mVerificationMessage = getArguments().getString("verificationmessage");
        mEmail = getArguments().getString("Email");
        mPhoneNo = getArguments().getString("PhoneNumber");
        mPassword = getArguments().getString("Password");
        mFullName = getArguments().getString("Fullname");
        mcountryCode = getArguments().getString("countrycode");
        companyCategories = getArguments().getString("company_categories");
        UtileKit.intitializeAlertDialog(mVerificationMessage, mContext);

        verifyCodeEdView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN)
                    if (i == KeyEvent.KEYCODE_ENTER) {
                        String givenCode = verifyCodeEdView.getText().toString().trim();
                        if (UtileKit.validateObjectValues(givenCode)) {
                            if (givenCode.equalsIgnoreCase(mVerificationCode)) {
                                callSignUpService(mEmail, "Email");
                            } else if (givenCode.equalsIgnoreCase(VerificationCode_mobile)) {
                                callSignUpService(mEmail, "Mobile");
                            } else {
                                UtileKit.intitializeAlertDialog(getString(R.string.login_verification_auth_code_wrong), mContext);
                            }
                        } else {
                            UtileKit.intitializeAlertDialog(getString(R.string.login_verification_code), mContext);
                        }
                    }
                return false;
            }
        });
        return verifyView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v == verifyNextBtnView) {
            if (!UtileKit.isNetworkAvailable(MyApplication.getInstance())) {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                String givenCode = verifyCodeEdView.getText().toString().trim();
                if (UtileKit.validateObjectValues(givenCode)) {
                    if (givenCode.equalsIgnoreCase(mVerificationCode)) {

                        callSignUpService(mEmail, "Email");
                    } else if (givenCode.equalsIgnoreCase(VerificationCode_mobile)) {
                        callSignUpService(mEmail, "Mobile");

                    } else {

                        UtileKit.intitializeAlertDialog(getString(R.string.login_verification_auth_code_wrong), mContext);
                    }
                } else {
                    UtileKit.intitializeAlertDialog(getString(R.string.login_verification_code), mContext);
                }
            }

        } else if (v == text_resend) {
            if (resend_count < 5) {

                callVerificationCode(mEmail, mPhoneNo, mcountryCode);


            } else {
                text_resend.setText("can you try after some time...");
            }

        }
    }

    // otp type as email or mobile otp
    private void callSignUpService(String mMailID, String otp_type) {
        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);

        Call<SignUpModel> call = webServiceObj.callRegisterService(mFullName, mMailID, mPhoneNo, mPassword, mcountryCode, companyCategories, otp_type);
        call.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                UtileKit.dismisssSpinnerDialog();
                SignUpModel signupModel = response.body();
                if (signupModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    setRestricted_menus(signupModel.getData().getRestricted_menus());

                    //taking paid type
                    UtileKit.persistingPurplePathPref("paid_type", signupModel.getData().getUser_details().getUser_paid_type());

                    UtileKit.createSharedPreference(getActivity(), signupModel.getData().getUser_id());

                    UtileKit.persistingPurplePathPref("cust_id", signupModel.getData().getCust_id());

                    Bundle args = new Bundle();
                    args.putString("userid", signupModel.getData().getUser_id());
                    args.putBoolean("IsSignUp", true);

                    InstructionScreenOne fragment = new InstructionScreenOne();
                    fragment.setArguments(args);
                    addFragmenttoStack(fragment);
                }
            }

            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {

                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }


    /**
     * enable menu in list
     *
     * @param restricted_menus
     */
    private void setRestricted_menus(ArrayList<Restricted_menus> restricted_menus) {
        try {
            for (String menu : menuList) {
                persistingPurplePathPref(menu, true);
            }
            for (String menu : menuList) {
                for (Restricted_menus obj : restricted_menus) {
                    if (obj.getMenu_name().equalsIgnoreCase(menu)) {
                        persistingPurplePathPref(menu, false);
                    }
                }


            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    private void callVerificationCode(final String mMailID,
                                      final String mPhoneNumber,
                                      final String countryCode) {

        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<VerificationModel> call = webServiceObj.GetVerificationCode(mMailID, companyCategories, mPhoneNumber, countryCode);
        call.enqueue(new Callback<VerificationModel>() {
            @Override
            public void onResponse(Call<VerificationModel> call, Response<VerificationModel> response) {
                UtileKit.dismisssSpinnerDialog();
                VerificationModel verificationModel = response.body();
                if (verificationModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    mVerificationCode = verificationModel.getData().getVerify_code();
                    VerificationCode_mobile = verificationModel.getData().getVerify_code_mobile();
                    resend_count++;
                } else {
                    UtileKit.intitializeAlertDialog(verificationModel.getData().getMessage(), mContext);
                }

            }

            @Override
            public void onFailure(Call<VerificationModel> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog(mContext, t);
            }
        });
    }

}
