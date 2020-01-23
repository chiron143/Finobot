package com.purplepath.purplepath.fragments;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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
import com.purplepath.purplepath.model.ForgotPasswordModel;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * X-valuetech Pvt Ltd, created by praveen 05/11/2014
 */
public class ForgotPwdFragment extends BaseFragment implements View.OnClickListener {

    private EditText forgotPwdEmailView = null;
    private Button forgotPwdSendBtnView = null;
    private TextView forgotPwdtitleView = null; //Added by Murali
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

        //MURALi

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View forgotPwdFragmentView = inflater.inflate(
                R.layout.fragment_forget_password, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        TextView mTitle = toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText("Forgot password");
        forgotPwdtitleView = forgotPwdFragmentView
                .findViewById(R.id.forgotPwdTitleId);
        forgotPwdtitleView.setTypeface(null, Typeface.BOLD);
        forgotPwdEmailView = forgotPwdFragmentView
                .findViewById(R.id.forgotPwdEmailId);
        forgotPwdSendBtnView = forgotPwdFragmentView
                .findViewById(R.id.forgotPwdSendBtnId);
        forgotPwdSendBtnView.setOnClickListener(this);
        return forgotPwdFragmentView;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v == forgotPwdSendBtnView) {
            if (!UtileKit.isNetworkAvailable(MyApplication.getInstance())) {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            } else {
                String _emailId = UtileKit.getTextFromObjects(forgotPwdEmailView);
                if (UtileKit.validateObjectValues(_emailId)) {
                    if (UtileKit.validateEmail(_emailId)) {
//				 persistingSeekaPref(USER_EMAIL, _emailId);
//				 callSeekahooForgotPwd();
                        callVerificationCode(_emailId);
                    } else {
//                        UtileKit.alertDialog(getString(R.string.forget_pwd_email_val_msg), mContext);
                        UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_email_val_msg), mContext);
                    }
                } else {
//                    UtileKit.alertDialog(getString(R.string.forget_pwd_enter_email), mContext);
                    UtileKit.intitializeAlertDialog(getString(R.string.forget_pwd_enter_email), mContext);
                }


            }
        }
    }

    private void callVerificationCode(final String mMailID) {


        UtileKit.showSpinnerDialog(mContext, false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator
                .createService(WebServiceCalls.class);
        Call<ForgotPasswordModel> call = webServiceObj.ForgotPasswordService(mMailID);
        call.enqueue(new Callback<ForgotPasswordModel>() {
            @Override
            public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                //Log.e("CallBack", " response is " + call.toString());
                UtileKit.dismisssSpinnerDialog();
                ForgotPasswordModel forgotPasswordModel = response.body();
                if (forgotPasswordModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                   ForgotPwdAuthFragment frag = new ForgotPwdAuthFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("VerificationCode", forgotPasswordModel.getData().getAuth_code());
                    bundle.putString("Email", mMailID);
                    frag.setArguments(bundle);
                    addFragmenttoStack(frag);

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
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }
}
