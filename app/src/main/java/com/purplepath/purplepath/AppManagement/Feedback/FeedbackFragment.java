package com.purplepath.purplepath.AppManagement.Feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Feedback.model.Feedbackmodel;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.purplepath.purplepath.apputiles.UtileKit.SUCCESSCODE;

public class FeedbackFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private EditText med_name,med_email,med_comments;
    private RelativeLayout mlayout_ed_prf_donebutton;
    private Context mContext;
    private Button mtxt_done;

    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtileKit.cretePrefAtHome(getContext());
        MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_feedback, container, false);
        backPressedListener.setActionBarTitle("Feedback");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        med_name= view.findViewById(R.id.ed_name);
        med_email= view.findViewById(R.id.ed_email);
        med_comments= view.findViewById(R.id.ed_comments);

        med_name.setSelection(med_name.getText().length());
        med_email.setSelection(med_email.getText().length());

        UtileKit.setSvgEdittextDrawableLeft(med_name,mContext,R.drawable.ic_name_icon);
        UtileKit.setSvgEdittextDrawableLeft(med_email,mContext,R.drawable.ic_email_svg);
       // UtileKit.setSvgEdittextDrawableLeft(med_comments,mContext,R.drawable.ic_edit_feedback);

       // mlayout_ed_prf_donebutton=(RelativeLayout)view.findViewById(R.id.layout_ed_prf_donebutton);
        mtxt_done= view.findViewById(R.id.txt_done);


        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mtxt_done.setOnClickListener(this);


        String getprefName = UtileKit.getPersistedPurplePathPref("name_services",null);
        if(getprefName!=null)
        {
            med_name.setText(getprefName);
            med_name.setSelection(getprefName.length());
        }
        String sugestionEmail=UtileKit.getPersistedPurplePathPref("email_service",null);
        if(sugestionEmail!=null)
        {
            med_email.setText(sugestionEmail);
            med_email.setSelection(sugestionEmail.length());
        }


        return view;
    }
    private void callFeedbackaddService(String ed_name,String ed_email,String ed_comment) {

        Log.d("hi","servicename"+ed_name);
        Log.d("hi","serviceemail"+ed_email);
        Log.d("hi","servicecomment"+ed_comment);
        try {
            WebServiceCalls webServiceObj;
            webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
            Call<Feedbackmodel> call = webServiceObj.FeedbackaddService(UtileKit.getPersistedPurplePathPref("user_id"),ed_name,ed_email,ed_comment);
            call.enqueue(new Callback<Feedbackmodel>() {
                @Override
                public void onResponse(Call<Feedbackmodel> call, Response<Feedbackmodel> response) {
                    Log.i("CallBack", " response is " + call.toString());
                    try {
                        Feedbackmodel feedbackmodel = response.body();
                        Log.d("hi","iiiiii"+ response.body());
                        if (feedbackmodel.getStatus_code().equalsIgnoreCase(SUCCESSCODE)) {
                            intitializeAlertDialog(getString(R.string.successfully),mContext);
                        }
                        else {
                            UtileKit.intitializeAlertDialog(feedbackmodel.getData().getMessage(),mContext);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Call<Feedbackmodel> call, Throwable t) {
//                    //Log.e("CallBack", " failure is " + t);
                    UtileKit.alertRetrofitExceptionDialog( mContext, t);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void intitializeAlertDialog(String string, Context mContext) {
        LayoutInflater inflater;
        View dialogView;
        final AlertDialog alertDialogs;
        try {
            inflater = LayoutInflater.from(mContext);
            dialogView = inflater.inflate(R.layout.alert_message_layout, null);
            alertDialogs = new AlertDialog.Builder(mContext).create();
            alertDialogs.setView(dialogView);
            TextView erroreMessage = dialogView.findViewById(R.id.textViewDilog);
            Log.i("FeedbackFragment", "intitializeAlertDialog " + string);
            erroreMessage.setText(string);
            dialogView.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialogs.dismiss();
                    backPressedListener.onActivityBackPressed();
                }
            });
//            dialogView.findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    alertDialog.dismiss();
//                }
//            });
            alertDialogs.show();
        }catch(Exception e){
            e.printStackTrace();
        }

    }
    public void validateUserCredentials(String ed_name,String ed_email, String ed_comment) {

        if(UtileKit.validateObjectValues(ed_name)){
            if (UtileKit.validateObjectValues(ed_email)) {
                if (UtileKit.validateEmail(ed_email)) {
                    if (UtileKit.validateObjectValues(ed_comment)) {
                        callFeedbackaddService(ed_name, ed_email, ed_comment);
                    } else {
                        UtileKit.intitializeAlertDialog(getString(R.string.entercomments), mContext);
                    }
                } else {
                    UtileKit.intitializeAlertDialog(getString(R.string.http_login_invalid_email), mContext);
                }
            }else {
                UtileKit.intitializeAlertDialog(getString(R.string.enteremail),mContext);
            }
        } else{
            UtileKit.intitializeAlertDialog(getString(R.string.signup_full_name_val_msg),mContext);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_done:
                String ed_name=med_name.getText().toString();
                String ed_email=med_email.getText().toString();
                String ed_comment=med_comments.getText().toString();
                validateUserCredentials(ed_name,ed_email,ed_comment);
                break;
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                break;

        }

    }
}
