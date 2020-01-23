package com.purplepath.purplepath.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.model.ForgotPasswordModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * X-valuetech Pvt Ltd, created by praveen 05/11/2014
 */
public class ForgotPwdAuthFragment extends BaseFragment implements View.OnClickListener {

    private EditText forgotAuthCodeView = null;
    private EditText forgotNewPwdView = null, forgotReTypePwdView = null;
    private Button forgotAuthContinueBtnView = null;
    private TextView forgotAuthTitleView = null;
    private Context mContext;
    private String mAuthenticationCode, mEmail;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        //MURALi
        mAuthenticationCode = getArguments().getString("VerificationCode");
        mEmail = getArguments().getString("Email");
    }

    // persistingSeekaPref(USER_AUTH_CODE,
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View forgotAuthFragmentView = inflater.inflate(
                R.layout.fragment_forgot_pwd_auth_code, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Forgot Password");
        //Added by Murali
        forgotAuthTitleView = forgotAuthFragmentView
                .findViewById(R.id.forgotAuthTitleId);
        forgotAuthTitleView.setTypeface(null, Typeface.NORMAL);

        forgotAuthCodeView = forgotAuthFragmentView
                .findViewById(R.id.forgotAuthCodeId);
        forgotNewPwdView = forgotAuthFragmentView
                .findViewById(R.id.forgotNewPwdID);
        forgotReTypePwdView = forgotAuthFragmentView
                .findViewById(R.id.forgotReTypePwdID);
        forgotAuthContinueBtnView = forgotAuthFragmentView
                .findViewById(R.id.forgotAuthContinueBtnId);
        forgotAuthContinueBtnView.setOnClickListener(this);

        forgotNewPwdView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        forgotReTypePwdView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        return forgotAuthFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v == forgotAuthContinueBtnView) {
            if (!UtileKit.isNetworkAvailable(MyApplication.getInstance())) {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                String _authCode = UtileKit.getTextFromObjects(forgotAuthCodeView);
                String _newPwd = UtileKit.getTextFromObjects(forgotNewPwdView);
                String _reTypePwd = UtileKit.getTextFromObjects(forgotReTypePwdView);

                if (UtileKit.validateObjectValues(_authCode)) {
                    if (_authCode.equalsIgnoreCase(mAuthenticationCode))
                        if (UtileKit.validateObjectValues(_newPwd)
                                && UtileKit.validateObjectValues(_reTypePwd)
                                && _newPwd.equalsIgnoreCase(_reTypePwd)) {
                            String _encryptPwd = UtileKit.encryptPwd(_newPwd);
                            callChangePassword(mEmail, _encryptPwd);
                        } else {
                            UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_not_match), mContext);
                            //generalPropListener.showShortToast("Password mismatch!");
                        }
                    else
                        UtileKit.intitializeAlertDialog("Please enter a valid authorization code", mContext);
                } else {
                    UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_auth_code), mContext);

                    //generalPropListener.showShortToast("Invalid authrization code!");
                }
            }
        }
    }


        public  void passalertButtonDialog(Context mContext, String message ) {
            LayoutInflater inflater;
            View dialogView;
            final AlertDialog alertDialogs;
            try {
                inflater = LayoutInflater.from(mContext);
                dialogView = inflater.inflate(R.layout.alert_message_layout, null);
                alertDialogs = new AlertDialog.Builder(mContext).create();
                alertDialogs.setView(dialogView);
                TextView erroreMessage = dialogView.findViewById(R.id.textViewDilog);
                TextView title = dialogView.findViewById(R.id.textViewAlert);
//                Log.i(TAG, "intitializeAlertDialog " + string);
                erroreMessage.setText(message);
                dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.popBackStack();
                        fragmentManager.popBackStack();
                        alertDialogs.dismiss();

//                onBackPressed();
//                finish();
                    }
                });
//            dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//                }
//            });
                alertDialogs.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    private void callChangePassword(final String mMailID, final String mPassword) {


        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<ForgotPasswordModel> call = webServiceObj.changeForgotPasswordService(mMailID, mPassword);
        call.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                ForgotPasswordModel forgotPasswordModel = response.body();
                if (forgotPasswordModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    passalertButtonDialog(mContext,forgotPasswordModel.getData().getMessage());
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    PersonalDetailsFragment frag = new PersonalDetailsFragment();
////                    Bundle bundle = new Bundle();
//                    bundle.putString("VerificationCode", forgotPasswordModel.getData().getAuth_code());
//                    bundle.putString("Email", mMailID);
//                    frag.setArguments(bundle);
//                    fragmentTransaction.replace(R.id.fragment_container, frag);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commitAllowingStateLoss();

                } else {
                    UtileKit.intitializeAlertDialog(
                            forgotPasswordModel.getData().getMessage(),
                            mContext);

//                    UtileKit.alertDialog(
//                            forgotPasswordModel.getData().getMessage(),
//                            mContext);
                }

            }

            @Override
            public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {

                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }

}
