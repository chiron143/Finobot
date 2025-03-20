package com.purplepath.purplepath.taxanalysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxanalysis.adapter.TaxplanAdapter;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.TaxCashFlowModel;
import com.purplepath.purplepath.taxanalysis.modes.GetTaxPlanModels;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Murugesan on 1/5/18.
 */

public class TaxPlanDetailViewPager extends BaseFragment implements View.OnClickListener {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;
    private TextView errorTextview;
    OnActivityBackPressedListener mListener;
    private Context mContext;

    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
    private TaxplanAdapter adapter;

    View view;
    private TaxCashFlowModel taxAnalysistModel;
    private GetTaxPlanModels entitlementInfoModel;
    private Bundle args;

    public static TaxPlanDetailViewPager newInstance(TaxCashFlowModel taxAnalysisCashFlowChartModel, GetTaxPlanModels taxAnalysisChartModel) {
        TaxPlanDetailViewPager fragment = new TaxPlanDetailViewPager();
        Bundle args = new Bundle();
        if (taxAnalysisCashFlowChartModel != null) {
            args.putSerializable("TaxPlanDetails", taxAnalysisCashFlowChartModel);
            args.putSerializable("entitlementInfo", taxAnalysisChartModel);
            fragment.setArguments(args);
        }
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        mListener=(OnActivityBackPressedListener)mContext;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mListener.setActionBarTitle("Tax Plan Details");
        if(view==null){
            view=inflater.inflate(R.layout.fragment_taxplan_viewpager, container, false);
        }
        viewPager= view.findViewById(R.id.viewPager_tax);

        adapter = TaxplanAdapter.newInstance(getChildFragmentManager());
        tabLayout= view.findViewById(R.id.tab_layout_taxplan);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        errorTextview = view.findViewById(R.id.errorTextview);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        errorTextview = view.findViewById(R.id.errorTextview);

        callTaxProductService();



        args = getArguments();
        if (args != null) {
            if (args.containsKey("entitlementInfo")) {
                entitlementInfoModel = (GetTaxPlanModels) args.getSerializable("entitlementInfo");
                try {
                    if (entitlementInfoModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                    } else {
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

        }else {
            callTaxEntitlementService();
        }




        return view;
    }


    private void callTaxProductService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<TaxCashFlowModel> call = webServiceObj.callinsurance_tax_Cash_Flow_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<TaxCashFlowModel>() {
            @Override
            public void onResponse(Call<TaxCashFlowModel> call, Response<TaxCashFlowModel> response) {
                UtileKit.dismisssSpinnerDialog();
                taxAnalysistModel= response.body();
                try {
                    if (taxAnalysistModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                        adapter.addFragment(TaxPlanDetail.newInstance(taxAnalysistModel, entitlementInfoModel), getString(R.string.taxplansavings));
                        adapter.addFragment(TaxProductDetail.newInstance(taxAnalysistModel), getString(R.string.taxplanproduct));

                        viewPager.setAdapter(adapter);
                        tabLayout.setupWithViewPager(viewPager);
                        viewPager.setOffscreenPageLimit(fragmentsList.size());
                    } else {
                        errorTextview.setVisibility(View.VISIBLE);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<TaxCashFlowModel> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    private void callTaxEntitlementService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GetTaxPlanModels> call = webServiceObj.callinsurance_tax_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GetTaxPlanModels>() {
            @Override
            public void onResponse(Call<GetTaxPlanModels> call, Response<GetTaxPlanModels> response) {
                try {
                    UtileKit.dismisssSpinnerDialog();
                    entitlementInfoModel = response.body();
                    if (entitlementInfoModel.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<GetTaxPlanModels> call, Throwable t) {
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_chart,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_chart);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.menu_chart:
                try{

                   // descriptiondatapass(taxAnalysistModel);

                    TaxAnalysisViewPager taxAnalysisViewPager=TaxAnalysisViewPager.newInstance(taxAnalysistModel);
                    showFragment(taxAnalysisViewPager);

                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:
                try{
                    addFragmenttoStack(new TaxPlanSummary());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
    private void showFragment(Fragment fragment) {
        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment).addToBackStack(null);
        transaction.commitAllowingStateLoss();

    }
    private void descriptiondatapass(TaxCashFlowModel user_tax) {
        try {

            Fragment fragment = new TaxAnalysis();
            Bundle bundle = new Bundle();

            if (user_tax != null) {
                bundle.putSerializable("user_tax_position", user_tax);
            }
            fragment.setArguments(bundle);
            addFragmenttoStack(fragment);
        }catch (Exception e){
            e.printStackTrace();
        }
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
