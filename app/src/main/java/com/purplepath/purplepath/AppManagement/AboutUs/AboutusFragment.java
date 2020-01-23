package com.purplepath.purplepath.AppManagement.AboutUs;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import static com.purplepath.purplepath.retrofitservice.ServiceGenerator.about_us;



public class AboutusFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;
    private WebView webview;

    private String VersionName;
    private String VersionCode;
    TextView txt_VersionName,txt_VersionCode;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_aboutus, container, false);
        MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        backPressedListener.setActionBarTitle("About Us");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        txt_VersionName= view.findViewById(R.id.txt_VersionName);
        //txt_VersionCode=(TextView)view.findViewById(R.id.txt_VersionCode);

        try
        {
            VersionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            //VersionCode = Integer.toString(mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        txt_VersionName.setText("Version - "+VersionName);


       webview = view.findViewById(R.id.WebView);
        try {
            //webview.loadUrl("http://52.66.64.150/pp_dev2/webview/about_us.html");
            webview.loadUrl(about_us);
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
                startSettingHomeActivity();
                break;

        }

    }
}
