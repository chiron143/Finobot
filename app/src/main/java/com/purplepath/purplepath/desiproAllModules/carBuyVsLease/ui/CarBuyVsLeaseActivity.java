package com.purplepath.purplepath.desiproAllModules.carBuyVsLease.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import butterknife.Bind;

/**
 * Created by dinesh on 13/09/17.
 */

public class CarBuyVsLeaseActivity extends BaseFragment implements View.OnClickListener,OnActivityBackPressedListener {
     Toolbar mToolbar;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    @Bind(R.id.title_viewId)
    AppCompatTextView mTitleTxtView;
    private Context mContext;
    private OnActivityBackPressedListener mCallBackListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setHasOptionsMenu(true);
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (Exception e) {
        }
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_bus_vs_lease_view);
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        getSupportActionBar().setTitle(null);
//        mTitleTxtView= (AppCompatTextView)mToolbar.findViewById(R.id.title_viewId);
//        mTitleTxtView.setText("DeciPro - Car - Buy vs Lease");
//        if(savedInstanceState==null)
//        {
//            showCarBuyVsLeaseFragment();
//        }
//        mleftRelativeLayout = (RelativeLayout) findViewById(R.id.relative_left_arrow);
//        mcenterRelativeLayout = (RelativeLayout) findViewById(R.id.relative_center_home);
//        mRightRelativeLayout = (RelativeLayout) findViewById(R.id.relative_right_arrow);
//
//        mRightRelativeLayout.setVisibility(View.GONE);
//        mleftRelativeLayout.setOnClickListener(this);
//        mcenterRelativeLayout.setOnClickListener(this);
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_bus_vs_lease_view,container,false);
        mCallBackListener.setActionBarTitle("DeciPro - Car - Buy vs Lease");
        if(savedInstanceState==null)
        {
            showCarBuyVsLeaseFragment();
        }
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);

        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        return  view;
    }


    private void showCarBuyVsLeaseFragment() {
        try {
            Fragment fragment = new CarBuyVsLeaseFragment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commitAllowingStateLoss();
        }catch (Exception e){e.printStackTrace();}
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
                Log.i("spcheck", " relative_left_arrow is clicked"  );
                mCallBackListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                Log.i("spcheck", " relative_center_home is clicked"  );
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                startActivity(i);
                break;

        }
    }

    @Override
    public void onActivityBackPressed() {
        try {
//            onBackPressed();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setActionBarTitle(String mTitle) {
        if (mToolbar != null) {
            mToolbar.setTitle("");
            // mToolbar.getMenu().clear();
        }
        if (mTitle != null)
            mTitleTxtView.setText(mTitle);

    }

    @Override
    public void setActionBarExpTitle(String mTitle) {
        setActionBarTitle(mTitle);
    }

    @Override
    public void closeDropDownTab() {

    }
}
