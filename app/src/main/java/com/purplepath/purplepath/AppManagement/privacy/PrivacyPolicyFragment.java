package com.purplepath.purplepath.AppManagement.privacy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.privacy_policy;

/**
 * Created by pravinr on 9/27/17.
 */

public class PrivacyPolicyFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;
    private  WebView webview;
    Boolean isSignUp=false;
    LinearLayout bottom_bar_layout;
    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_privacy_policy, container, false);
        backPressedListener.setActionBarTitle("Privacy Policy");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        bottom_bar_layout= view.findViewById(R.id.bottom_bar_layout);

        try{
            Bundle args=getArguments();

            if(args!=null){
                if(args.containsKey("Flag")) {
                    isSignUp = getArguments().getBoolean("Flag");
                }
            }
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){
            e.printStackTrace();
        }
        if(!isSignUp){
            bottom_bar_layout.setVisibility(View.GONE);
        }
        else {
            bottom_bar_layout.setVisibility(View.VISIBLE);
        }


        webview = view.findViewById(R.id.WebView);
        try {
            webview.loadUrl(privacy_policy);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }

    }
    public static Fragment newInstance(boolean flag) {
        PrivacyPolicyFragment fragment = new PrivacyPolicyFragment();
        Bundle args = new Bundle();
        args.putBoolean("Flag", flag);
        fragment.setArguments(args);
        return fragment;
    }
}
