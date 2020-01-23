package com.purplepath.purplepath.healthinsurance.Details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import com.purplepath.purplepath.healthinsurance.HealthInsuranceChart;
import com.purplepath.purplepath.healthinsurance.HealthInsuranceSummary;
import com.purplepath.purplepath.healthinsurance.adapter.HealthInsuranceAdapter;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by pravinr on 2/20/18.
 */

public class HealthInsuranceDetailViewpager extends BaseFragment implements View.OnClickListener {


    private ViewPager viewPager;

    private TabLayout tabLayout;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;

    private TextView errorTextview;

    OnActivityBackPressedListener mListener;

    private Context mContext;

    private ArrayList<Fragment> fragmentsList = new ArrayList<Fragment>();

    private HealthInsuranceAdapter adapter;

    Motor_Proper_Health_Model motor_Proper_Health_Model;

    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        mListener=(OnActivityBackPressedListener)mContext;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        mListener.setActionBarTitle("Health Insurance Detail");
        if(view==null){
            view=inflater.inflate(R.layout.fragment_healthinsurance_detail_viewpager, container, false);
        }

        viewPager= view.findViewById(R.id.viewPager_health_deatils);

        adapter = HealthInsuranceAdapter.newInstance(getChildFragmentManager());

        tabLayout= view.findViewById(R.id.tab_layout_health_detail);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        errorTextview = view.findViewById(R.id.errorTextview);

        callHealthInsuranceService();
        return view;
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
                    addFragmenttoStack(new HealthInsuranceChart());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;


            case R.id.menu_summary:
                try{
                    addFragmenttoStack(new HealthInsuranceSummary());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }


    public void callHealthInsuranceService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<Motor_Proper_Health_Model> call = webServiceObj.callGetPropertyService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<Motor_Proper_Health_Model>() {
            @Override
            public void onResponse(Call<Motor_Proper_Health_Model> call, Response<Motor_Proper_Health_Model> response) {
                UtileKit.dismisssSpinnerDialog();
                motor_Proper_Health_Model = response.body();
                if (motor_Proper_Health_Model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    adapter.addFragment(HeathInsuranceDetailIndividualPlan.newInstance(motor_Proper_Health_Model), getString(R.string.individualhealthplan));
                    adapter.addFragment(HealthInsuranceDetailFamilyPlan.newInstance(motor_Proper_Health_Model), getString(R.string.familyfloaterplan));

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
