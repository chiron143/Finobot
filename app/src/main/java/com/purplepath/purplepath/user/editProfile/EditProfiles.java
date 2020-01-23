package com.purplepath.purplepath.user.editProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.finobot.finobot.MyApplication;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.user.editProfile.model.ChangepwdModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.SUCCESSCODE;


public class EditProfiles extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private EditText med_prf_oldpassword, med_prf_newpassword, med_prf_confirmpassword;
    private RelativeLayout mlayout_ed_prf_donebutton;
    private Boolean isClicked = false;
    private Button mtxt_done;
    private Context mContext;

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        backPressedListener = (OnActivityBackPressedListener) mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        backPressedListener.setActionBarTitle("Change Password");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);


        med_prf_oldpassword = view.findViewById(R.id.ed_prf_oldpassword);
        med_prf_newpassword = view.findViewById(R.id.ed_prf_newpassword);
        med_prf_confirmpassword = view.findViewById(R.id.ed_prf_confirmpassword);

        UtileKit.setSvgEdittextDrawableRight(med_prf_oldpassword,mContext,R.drawable.ic_passwor_icon);
        UtileKit.setSvgEdittextDrawableRight(med_prf_newpassword,mContext,R.drawable.ic_passwor_icon);
        UtileKit.setSvgEdittextDrawableRight(med_prf_confirmpassword,mContext,R.drawable.ic_passwor_icon);


        // mlayout_ed_prf_donebutton = (RelativeLayout) view.findViewById(R.id.layout_ed_prf_donebutton);

        mtxt_done= view.findViewById(R.id.txt_done);
        mtxt_done.setOnClickListener(this);
        mtxt_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ed_prf_oldPwd=med_prf_oldpassword.getText().toString();
                String ed_prf_newpwd=med_prf_newpassword.getText().toString();
                String ed_prf_confirmpwd=med_prf_confirmpassword.getText().toString();
                Log.d("hi", "oldpasswrd" + ed_prf_oldPwd);
                Log.d("hi", "newpasswrd" + ed_prf_newpwd);
                Log.d("hi", "confornpasswrd" + ed_prf_confirmpwd);
                validateUserCredentials(ed_prf_oldPwd,ed_prf_newpwd,ed_prf_confirmpwd);
            }
        });
        return view;
    }


   public void validateUserCredentials(String ed_prf_oldPwd,String ed_prf_newpwd, String ed_prf_confirmpwd) {

        if(UtileKit.validateObjectValues(ed_prf_oldPwd)){
            ed_prf_oldPwd = UtileKit.encryptPwd(ed_prf_oldPwd);

          if (UtileKit.validateObjectValues(ed_prf_newpwd)) {
              ed_prf_newpwd = UtileKit.encryptPwd(ed_prf_newpwd);

            if (ed_prf_newpwd.trim().length() > 2) {

               if (UtileKit.validateObjectValues(ed_prf_confirmpwd)) {
                    ed_prf_confirmpwd = UtileKit.encryptPwd(ed_prf_confirmpwd);

                if (ed_prf_confirmpwd.equals(ed_prf_newpwd)) {
                   // if (!isClicked) {
                    callChangePasswordService(ed_prf_oldPwd, ed_prf_newpwd);
                     //   isClicked = true;
                    //}
                    } else {
                    UtileKit.intitializeAlertDialog(getString(R.string.newdoes),mContext);
                    }
                } else {
                   UtileKit.intitializeAlertDialog(getString(R.string.enterconfornpass),mContext);
                }
            }
            else {
               // med_prf_newpassword.requestFocus();
                UtileKit.intitializeAlertDialog(getString(R.string.enterthree),mContext);
          }
        } else {
              UtileKit.intitializeAlertDialog(getString(R.string.enternewpass),mContext);
        }
    } else
    {
        UtileKit.intitializeAlertDialog(getString(R.string.enteroldpass),mContext);
    }
}


   private void callChangePasswordService(String ed_prf_oldPwd,String ed_prf_newpwd) {

       Log.d("hi","serviceoldpass"+ed_prf_oldPwd);
       Log.d("hi","servicenewpass"+ed_prf_newpwd);
        try {
            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
            Call<ChangepwdModel> call = webServiceObj.ChangePassword(UtileKit.getPersistedPurplePathPref("user_id"),ed_prf_oldPwd, ed_prf_newpwd);
            call.enqueue(new Callback<ChangepwdModel>() {
                @Override
                public void onResponse(Call<ChangepwdModel> call, Response<ChangepwdModel> response) {
                    Log.i("CallBack", " response is " + call.toString());
                     isClicked=false;
                    try {
                        ChangepwdModel changepwdModel = response.body();
                        Log.d("hi","iiiiii"+ response.body());
                        Log.d("hi","iiiiii"+ changepwdModel.getData().getMessage());
                            if (changepwdModel.getStatus_code().equalsIgnoreCase(SUCCESSCODE)) {
                                UtileKit.intitializeAlertDialog(getString(R.string.successful),mContext);
                            }
                        else {
                                UtileKit.intitializeAlertDialog(getString(R.string.pleaseentervaliedold),mContext);

                              //  UtileKit.intitializeAlertDialog(changepwdModel.getData().getMessage(),mContext);
                            }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<ChangepwdModel> call, Throwable t) {
                 isClicked=false;
                    //Log.e("CallBack", " failure is " + t);
                    UtileKit.alertRetrofitExceptionDialog( mContext, t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mtxt_done) {
            if(!UtileKit.isNetworkAvailable(MyApplication.getInstance())){
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            }else {

            }
        }
        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
                break;
        }
    }
}
