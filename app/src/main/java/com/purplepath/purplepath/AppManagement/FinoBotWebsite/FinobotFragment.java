package com.purplepath.purplepath.AppManagement.FinoBotWebsite;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

public class FinobotFragment extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;
    private  TextView mappstore,mwebsite;

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

        final View view=inflater.inflate(R.layout.fragment_finobot, container, false);
        backPressedListener.setActionBarTitle("Finobot Website");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        mwebsite= view.findViewById(R.id.website);
        mwebsite.setText("www.finobot.com");
        mwebsite.setPaintFlags(mwebsite.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        mwebsite.setOnClickListener(this);


        mappstore= view.findViewById(R.id.appstore);
        mappstore.setVisibility(View.GONE);
        mappstore.setText("https://play.google.com/store/apps/details?id=com.purplepath.purplepath&hl=en");
        mappstore.setPaintFlags(mappstore.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        mappstore.setOnClickListener(this);

//        Button btn_task=(Button)view.findViewById(R.id.btn_task);
//        btn_task.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                UtileKit.showSnackBar(getActivity(),  v, "Add success!!!");
//
//            }
//        });

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.website:
                Intent is = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.finobot.com"));
                startActivity(is);

                break;
            case R.id.appstore:
                Intent ik = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.purplepath.purplepath&hl=en"));
                startActivity(ik);
                break;


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
}
