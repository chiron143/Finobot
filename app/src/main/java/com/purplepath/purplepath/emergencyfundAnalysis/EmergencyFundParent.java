package com.purplepath.purplepath.emergencyfundAnalysis;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.customview.ChartData;
import com.purplepath.purplepath.emergencyfundAnalysis.Adapter.EmergencyFundAdapter;
import com.purplepath.purplepath.emergencyfundAnalysis.model.EmergencyFundModel;
import com.purplepath.purplepath.emergencyfundAnalysis.summary.EmergencyFundDetailSumary;
import com.purplepath.purplepath.emergencyfundAnalysis.summary.EmergencyFundSummaryFrag;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmergencyFundParent extends BaseFragment implements View.OnClickListener {


    private ViewPager viewPager;
    private EmergencyFundAdapter viewPagerAdapter;
    private TabLayout tabLayout;
    private String Title[]={"Emergency Fund Plan","Emergency Fund Actual"};
    private EmergencyFundModel emergencyFundModel;
    static List<ChartData> value1 = new ArrayList<>(), value2 = new ArrayList<>();
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;

    private TextView errorTextview;

    private FloatingActionButton fab_id;

    OnActivityBackPressedListener mListener;
    private Context mContext;

    public static int cou = 0 , cou2 = 0;
    public static int emu1,emu2,emu3,emu4,emu5;
    public static int emup1,emup2,emup3,emup4,emup5;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        mListener=(OnActivityBackPressedListener)mContext;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        mListener.setActionBarTitle("Emergency Fund");
        View view=inflater.inflate(R.layout.fragment_emergency_fund_parent, container, false);
        viewPager= view.findViewById(R.id.viewPager_EF);
        tabLayout= view.findViewById(R.id.tab_layout_EF);
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        fab_id = view.findViewById(R.id.fab_id);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        fab_id.setOnClickListener(this);

        errorTextview = view.findViewById(R.id.errorTextview);
        callEmergFundAnalyService();

       /* viewPagerAdapter=new EmergencyFundAdapter(getChildFragmentManager(),Title);
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
*/

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_detail:
                EmergencyFundDetailSumary taxCashFlowChart = new EmergencyFundDetailSumary();
                showFragment(taxCashFlowChart);
                break;
            case R.id.menu_summary:
                EmergencyFundSummaryFrag taxPlanSummary = new EmergencyFundSummaryFrag();
                showFragment(taxPlanSummary);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commitAllowingStateLoss();
    }

    public void callEmergFundAnalyService() {
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<EmergencyFundModel> call = webServiceObj.callEmergencyFundChartService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<EmergencyFundModel>() {
            @Override
            public void onResponse(Call<EmergencyFundModel> call, Response<EmergencyFundModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success", "overall_per" + response.body());
                emergencyFundModel = response.body();
                ArrayList<Float> chartdatalist = new ArrayList<Float>();
                ArrayList<String> chartitledatalist = new ArrayList<String>();
                if (emergencyFundModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    value1 = new ArrayList();


//                    value.add(new ChartData((float)100, "act_overall_per"));
                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_cash_per())) != 0.0) {
                        emup1 = 1;
                        cou2++;
                        value1.add(new ChartData((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_cash_per())),"   "+String.valueOf(cou2)));

                    }

                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_savings_acc_per())) != 0.0){

                        cou2++;
                        emup2 = 2;
                        value1.add(new ChartData((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_savings_acc_per())),"   "+String.valueOf(cou2)));

                    }

                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_curr_acc_per())) != 0.0){

                        cou2++;
                        emup3 = 3;
                        value1.add(new ChartData((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_curr_acc_per())),"   "+String.valueOf(cou2)));

                    }
                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_fix_recc_dep_per())) != 0.0){

                        cou2++;
                        emup4 = 4;
                        value1.add(new ChartData((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_fix_recc_dep_per())),"   "+String.valueOf(cou2)));
                    }

                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_term_dep_per())) != 0.0){

                        cou2++;
                        emup5 = 5;
                        value1.add(new ChartData((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_term_dep_per())),"   "+String.valueOf(cou2)));
                    }

                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_term_dep_per())) != 0.0 && (float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_fix_recc_dep_per())) != 0.0 && (float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_curr_acc_per())) != 0.0 && (float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_savings_acc_per())) != 0.0 && (float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_cash_per())) != 0.0){

                        value1.add(new ChartData(ChartData.issum, "\t\t" + String.valueOf(value1.size() + 1)));
                    }


                    cou2 =0;





//                    value.add(new ChartData(15f, "plan_cash_per"));
//                    value.add(new ChartData(16f, "plan_savings_acc_per"));
//                    value.add(new ChartData(12f,"plan_curr_acc_per"));
//                    value.add(new ChartData(24f, "plan_fix_recc_dep_per"));
//                    value.add(new ChartData(ChartData.issum, "plan_term_dep_per"));
                    //Log.e("value1", "ChartData ArrayList" +emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_cash_per());

                    value2 = new ArrayList();

//                    value.add(new ChartData((float)100, "act_overall_per"));
                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_cash_per())) != 0.0) {
                        cou++;
                        value2.add(new ChartData((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_cash_per())),"   "+String.valueOf(cou)));

                        emu1 =1;
                    }

                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_savings_acc_per())) != 0.0){

                        cou++;
                        emu2 = 2;
                        value2.add(new ChartData((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_savings_acc_per())),"   "+String.valueOf(cou)));

                    }

                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_curr_acc_per())) != 0.0){

                        cou++;
                        emu3 = 3;
                        value2.add(new ChartData((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_curr_acc_per())),"   "+String.valueOf(cou)));
                    }

                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_fix_recc_dep_per())) != 0.0){

                        cou++;
                        emu4 = 4;
                        value2.add(new ChartData((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_fix_recc_dep_per())),"   "+String.valueOf(cou)));
                    }


                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_term_dep_per())) != 0.0){

                        cou++;
                        emu5 = 5;
                        value2.add(new ChartData((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_term_dep_per())),"   "+String.valueOf(cou)));
                    }


                    if((float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_term_dep_per())) != 0.0 && (float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_fix_recc_dep_per())) != 0.0 && (float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_curr_acc_per())) != 0.0 && (float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_savings_acc_per())) != 0.0 && (float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_cash_per())) != 0.0) {

                        value2.add(new ChartData(ChartData.issum, "\t\t" + String.valueOf(value2.size() + 1)));

                    }

                    cou = 0;
                    Log.i("goku","break  "+(float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_cash_per())));
                    Log.i("goku","break  "+(float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_savings_acc_per())));
                    Log.i("goku","break  "+(float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_curr_acc_per())));
                    Log.i("goku","break  "+(float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_fix_recc_dep_per())));
                    Log.i("goku","break  "+(float) Math.round(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_term_dep_per())));
                    if (!value1.isEmpty() || !value2.isEmpty()) {
                        viewPagerAdapter=new EmergencyFundAdapter(getChildFragmentManager(),Title);
                        viewPager.setAdapter(viewPagerAdapter);
                        tabLayout.setupWithViewPager(viewPager);
                    }else{
                        errorTextview.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "No graph to show", Toast.LENGTH_SHORT).show();
                    }


                }
                else {
                    errorTextview.setVisibility(View.VISIBLE);
                    Log.i("success", "value1 value2" + response.body());
//                    UtileKit.intitializeAlertDialog("No Emergency Fund details to retrieve. Please try again!", mContext);
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                }

            }

            @Override
            public void onFailure(Call<EmergencyFundModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
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

//            case R.id.fab_id:
//            {
//                addFragmenttoStack(new InsuranceDetailsFragment());
//            }
//            break;

        }
    }
}
