package com.purplepath.purplepath.AppManagement.Payment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.AppManagement.Payment.adapter.PaymentAdapter;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

/**
 * Created by pravinr on 12/5/17.
 */

public class PaymentViewPager extends BaseFragment implements View.OnClickListener {

    private ViewPager viewPager;

    private TabLayout tabLayout;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;

    OnActivityBackPressedListener mListener;

    private Context mContext;

    private PaymentAdapter adapter;

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        mListener=(OnActivityBackPressedListener)mContext;
        MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mListener.setActionBarTitle("Upgrade");
        if(view==null){
            view=inflater.inflate(R.layout.fragment_payment_viewpager, container, false);
        }

        viewPager= view.findViewById(R.id.viewPager_payment);
        tabLayout= view.findViewById(R.id.tab_layout_payment);
        setupViewPager(viewPager);

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);


        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
            adapter = new PaymentAdapter(getChildFragmentManager());
            //adapter.addFragment(new LiteFragment(), "Lite");
            adapter.addFragment(new ProFragment(), "Pro");
            adapter.addFragment(new PrimeFragment(), "Prime");
            viewPager.setAdapter(adapter);
            viewPager.setOffscreenPageLimit(2);
            tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
                mListener.onActivityBackPressed();
                break;

            case R.id.relative_center_home:
                Intent i= new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
        }
    }
}
