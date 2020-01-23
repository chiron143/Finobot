package com.purplepath.purplepath.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.autoinsurance.AutoinsuranceSummary;
import com.purplepath.purplepath.emergencyfundAnalysis.summary.EmergencyFundSummaryFrag;
import com.purplepath.purplepath.healthinsurance.HealthInsuranceSummary;
import com.purplepath.purplepath.insuranceSummary.InsuranceSummaryview;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.propertyinsurance.ProperyInsuranceSummary;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContigencyFragment extends BaseFragment implements View.OnClickListener {



    @Bind(R.id.lifeInsurance)
    ImageView lifeInsurance;

     @Bind(R.id.iv_health_insurance)
    ImageView iv_health_insurance;

     @Bind(R.id.iv_property_insurance)
    ImageView iv_property_insurance;

     @Bind(R.id.iv_expenseFund)
    ImageView iv_expenseFund;

     @Bind(R.id.iv_automobile)
    ImageView iv_automobile;

    Fragment fragment;
    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Context mContext;

    public ContigencyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        backPressedListener = (OnActivityBackPressedListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contigency, container, false);
        ButterKnife.bind(this,view);

        backPressedListener.setActionBarTitle("Contingency Plan");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);


      //  UtileKit.setSvgImageviewDrawable(iv_automobile,mContext,R.drawable.autoinsurance);


        lifeInsurance.setOnClickListener(this);
        iv_health_insurance.setOnClickListener(this);
        iv_property_insurance.setOnClickListener(this);
        iv_expenseFund.setOnClickListener(this);
        iv_automobile.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;
            case R.id.lifeInsurance:
                InsuranceSummaryview insuranceAnalysis=new InsuranceSummaryview();
                addFragmentToActivity(insuranceAnalysis);
                break;
           case R.id.iv_health_insurance:
                HealthInsuranceSummary healthInsuranceSummary=new HealthInsuranceSummary();
                addFragmentToActivity(healthInsuranceSummary);
                break;
            case R.id.iv_property_insurance:
                ProperyInsuranceSummary properyInsuranceSummary=new ProperyInsuranceSummary();
                addFragmentToActivity(properyInsuranceSummary);
                break;
            case R.id.iv_expenseFund:
                EmergencyFundSummaryFrag emergencyFundSummaryFrag=new EmergencyFundSummaryFrag();
                addFragmentToActivity(emergencyFundSummaryFrag);
                break;
           case R.id.iv_automobile:
                AutoinsuranceSummary autoinsuranceSummary=new AutoinsuranceSummary();
                addFragmentToActivity(autoinsuranceSummary);
                break;

        }
    }

    private void addFragmentToActivity(Fragment fragment) {
       addFragmenttoStack(fragment);

    }
}
