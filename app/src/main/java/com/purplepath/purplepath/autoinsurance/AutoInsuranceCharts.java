/*
package com.purplepath.purplepath.autoinsurance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.autoinsurance.adapter.AutoInsuranceAdapter;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

*/
/**
 * Created by pravinr on 11/23/17.
 *//*


public class AutoInsuranceCharts extends BaseFragment implements View.OnClickListener {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;
    private TextView errorTextview;
    OnActivityBackPressedListener mListener;
    private Context mContext;
    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();
    private AutoInsuranceAdapter adapter;

    Motor_Proper_Health_Model motor_Proper_Health_Model;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        mListener=(OnActivityBackPressedListener)mContext;
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mListener.setActionBarTitle("Auto Insurance");
        if(view==null){
            view=inflater.inflate(R.layout.fragment_autoinsurance_charts, container, false);
        }

        tabLayout=(TabLayout)view.findViewById(R.id.tab_layout_auto);
        viewPager=(ViewPager)view.findViewById(R.id.viewPager_auto);

        adapter = AutoInsuranceAdapter.newInstance(getFragmentManager());

        mleftRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = (RelativeLayout) view.findViewById(R.id.relative_center_home);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        errorTextview = (TextView) view.findViewById(R.id.errorTextview);

        callGetMotorService();
        return view;
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_summary_and_detail,menu);
        MenuItem item=menu.findItem(R.id.menu_summary);
        MenuItem items=menu.findItem(R.id.menu_detail);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_detail:
                try{
                    addFragmenttoStack(new AutoInsuranceDetails());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:

                try{
                    addFragmenttoStack(new AutoinsuranceSummary());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void callGetMotorService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext, false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Motor_Proper_Health_Model> call = webServiceObj.callGetPropertyService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Motor_Proper_Health_Model>() {
            @Override
            public void onResponse(Call<Motor_Proper_Health_Model> call, Response<Motor_Proper_Health_Model> response) {
                UtileKit.dismisssSpinnerDialog();
                motor_Proper_Health_Model = response.body();
                if (motor_Proper_Health_Model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    adapter.addFragment(AutoInsuranceIndividual.newInstance(motor_Proper_Health_Model), getString(R.string.individual_auto));
                    adapter.addFragment(AutoInsuranceTotal.newInstance(motor_Proper_Health_Model),getString(R.string.total_auto));

                    viewPager.setAdapter(adapter);
                    tabLayout.setupWithViewPager(viewPager);
                    viewPager.setOffscreenPageLimit(fragmentsList.size());

                } else {
                    errorTextview.setVisibility(View.VISIBLE);
                }


            }
            @Override
            public void onFailure(Call<Motor_Proper_Health_Model> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
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
*/
