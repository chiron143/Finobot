/*
package com.purplepath.purplepath.emergencyfundAnalysis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.numetriclabz.numandroidcharts.ChartData;
import com.numetriclabz.numandroidcharts.WaterFallChart;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.emergencyfundAnalysis.model.EmergencyFundModel;
import com.purplepath.purplepath.goaltimeline.GoalTimeLineFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

*/
/**
 * Created by dinesh on 12/10/16.
 *//*

public class EmergencyFundChart extends Fragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    CheckBox ef_planCheckBox,ef_actCheckBox;
    WaterFallChart waterFallChart,waterFallChart2;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    static public List<ChartData> value1 = new ArrayList ();
    static public List<ChartData> value2 = new ArrayList ();
    EmergencyFundModel emergencyFundModel;
    Toolbar toolbar;
    Context mContext;
    TextView errorTextview;
    View emergencyFundView=null;
    private OnActivityBackPressedListener mCallBackListener;
    private Boolean isEmptyView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        try{
//            if(getArguments()!=null) {
//                if (getArguments().containsKey("chart1"))
//                    value1 = (List<ChartData>) getArguments().getSerializable("chart1");
//                if (getArguments().containsKey("chart2"))
//                    value2 = (List<ChartData>) getArguments().getSerializable("chart2");
//                if (getArguments().containsKey("IsEmpty"))
//                    isEmptyView = (Boolean) getArguments().getBoolean("IsEmpty");
//            }
//            else{
//
//            }

            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
//        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            mCallBackListener.setActionBarTitle("Emergency Fund");

            emergencyFundView =  inflater.inflate(R.layout.fragment_water_fall_layout, container, false);
            errorTextview = (TextView) emergencyFundView.findViewById(R.id.errorTextview);
            mleftRelativeLayout = (RelativeLayout) emergencyFundView.findViewById(R.id.relative_left_arrow);
            mcenterRelativeLayout = (RelativeLayout) emergencyFundView.findViewById(R.id.relative_center_home);
            mRightRelativeLayout = (RelativeLayout) emergencyFundView.findViewById(R.id.relative_right_arrow);
            mRightRelativeLayout.setVisibility(View.GONE);
            mleftRelativeLayout.setOnClickListener(this);
            mcenterRelativeLayout.setOnClickListener(this);
            mRightRelativeLayout.setOnClickListener(this);

            waterFallChart = (WaterFallChart)emergencyFundView. findViewById(R.id.waterfallView1Id);
            waterFallChart2 = (WaterFallChart)emergencyFundView. findViewById(R.id.waterfallView2Id);
//            WaterFallChart obj=new WaterFallChart();

            ef_planCheckBox=(CheckBox) emergencyFundView.findViewById(R.id.ef_planId);
            ef_actCheckBox=(CheckBox) emergencyFundView.findViewById(R.id.ef_actId);
            ef_planCheckBox.setVisibility(View.VISIBLE);
            ef_actCheckBox.setVisibility(View.VISIBLE);

            ef_actCheckBox.setOnCheckedChangeListener(this);
            ef_planCheckBox.setOnCheckedChangeListener(this);
            ef_planCheckBox.setChecked(true);
//            value1.add(new ChartData(0f, ""));
//        value1.add(new ChartData(12f, "plan_savings_acc_per"));
//        value2.add(new ChartData(0f,""));
//        value1.add(new ChartData(16f,"plan_curr_acc_per"));
//        value1.add(new ChartData(24f, "plan_fix_recc_dep_per"));

            loadChart();
//            waterFallChart.setVisibility(View.GONE);
//            waterFallChart2.setVisibility(View.GONE);
            if (isEmptyView) {
                waterFallChart.setVisibility(View.GONE);
                waterFallChart2.setVisibility(View.GONE);
                errorTextview.setVisibility(View.VISIBLE);
                ef_planCheckBox.setVisibility(View.GONE);
                ef_actCheckBox.setVisibility(View.GONE);
                errorTextview.setText(HomePageActivity.errorMessageInChart);
                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
            }
            else {
                waterFallChart.setVisibility(View.VISIBLE);
                waterFallChart2.setVisibility(View.VISIBLE);
                ef_planCheckBox.setVisibility(View.VISIBLE);
                ef_actCheckBox.setVisibility(View.VISIBLE);
            }

        if(value1.isEmpty()&&value2.isEmpty())
            callEmergFundAnalyService();
        }catch ( NullPointerException e) {} catch (Exception e) {e.printStackTrace();}
        return emergencyFundView;
    }

    private void loadChart() {
        if(!value1.isEmpty()) {
            waterFallChart.setData(value1);
            waterFallChart.setEnabled(true);

        }
        else {
            waterFallChart.setVisibility(View.GONE);
//                value1.add(new ChartData(0f, ""));
        }
//        value2.add(new ChartData((float)0, "act_overall_per"));
        if(!value2.isEmpty()) {
            waterFallChart2.setData(value2);
            waterFallChart2.setEnabled(true);

        }
        else
        {
//            value2.add(new ChartData(0f, ""));
            waterFallChart2.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(ef_planCheckBox.isChecked()&&ef_actCheckBox.isChecked())
        {

            waterFallChart.setVisibility(View.VISIBLE);
            waterFallChart2.setVisibility(View.VISIBLE);
            loadChart();
        }
        else if(ef_planCheckBox.isChecked())
        {
            waterFallChart.setVisibility(View.VISIBLE);
            waterFallChart2.setVisibility(View.INVISIBLE);
            loadChart();


        }
        else if(ef_actCheckBox.isChecked())
        {
            waterFallChart.setVisibility(View.INVISIBLE);
            waterFallChart2.setVisibility(View.VISIBLE);
            loadChart();


        }
        else
        {
            waterFallChart.setVisibility(View.INVISIBLE);
            waterFallChart2.setVisibility(View.INVISIBLE);
            loadChart();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
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
                //Log.e("success","overall_per"+response.body());
                emergencyFundModel = response.body();
                ArrayList<Float> chartdatalist = new ArrayList<Float>();
                ArrayList<String> chartitledatalist = new ArrayList<String>();
                if (emergencyFundModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    if (emergencyFundModel.getData().getEf_result().getEf_plan() != null && emergencyFundModel.getData().getEf_result().getEf_act() != null ) {
                        ArrayList  value = new ArrayList();
                        value.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_cash_per()), "Cash %"));
                        value.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_savings_acc_per()), "Savings A/C"));
                        value.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_curr_acc_per()), "Curr A/C"));
                        value.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_fix_recc_dep_per()), "fix_recc_dep"));
                        value.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_plan().getPlan_term_dep_per()), "Term Deposit"));
                        value.add(new ChartData(ChartData.issum, "Overall %"));
                        waterFallChart.invalidate();
                        waterFallChart.setData(value);
                        waterFallChart.setVisibility(View.VISIBLE);
//                    value.add(new ChartData(15f, "plan_cash_per"));
//                    value.add(new ChartData(16f, "plan_savings_acc_per"));
//                    value.add(new ChartData(12f,"plan_curr_acc_per"));
//                    value.add(new ChartData(24f, "plan_fix_recc_dep_per"));
//                    value.add(new ChartData(ChartData.issum, "plan_term_dep_per"));
//

                        ArrayList   value1 = new ArrayList();
//                    value.add(new ChartData((float)100, "act_overall_per"));
                        value1.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_cash_per()),"Cash %"));
                        value1.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_savings_acc_per()),"Savings A/C"));
                        value1.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_curr_acc_per()),  "Curr A/C"));
                        value1.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_fix_recc_dep_per()), "fix_recc_dep"));
                        value1.add(new ChartData(Float.parseFloat(emergencyFundModel.getData().getEf_result().getEf_act().getAct_term_dep_per()), "Term Deposit"));
                        value1.add(new ChartData(ChartData.issum, "Overall %"));
                        waterFallChart2.setVisibility(View.VISIBLE);
                        waterFallChart2.invalidate();
                        waterFallChart2.setData(value2);
                        waterFallChart2.setEnabled(true);

                        if (!value1.isEmpty() || !value2.isEmpty()) {
                            Fragment fragment = new EmergencyFundChart();
                            Bundle mvalue = new Bundle();
                            mvalue.putSerializable("chart1", (Serializable) value1);
                            mvalue.putSerializable("chart2", (Serializable) value2);
                            EmergencyFundChart.value1=value;
                            EmergencyFundChart.value2=value1;
                            addFragmentToActivity(fragment);
                        }
                    }
                } else{
                    errorTextview.setVisibility(View.VISIBLE);
                    errorTextview.setText(HomePageActivity.errorMessageInChart);
                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }


            }

            @Override
            public void onFailure(Call<EmergencyFundModel> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog(mContext,t);

                UtileKit.dismisssSpinnerDialog();
            }
        });
    }
    public void addFragmentToActivity(Fragment fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_empty_items, menu);
        super.onCreateOptionsMenu(menu, inflater);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.relative_left_arrow:
            {
                mCallBackListener.onActivityBackPressed();
            }
            break;
            case R.id.relative_center_home:
            {
                Intent i = new Intent(getActivity(), HomePageActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
//                getActivity().finish();
            }
            break;
            case R.id.relative_right_arrow:
            {
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // ExpensesDetailsFragment fragment = new ExpensesDetailsFragment();
                //ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
                GoalTimeLineFragment fragment = new GoalTimeLineFragment();
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();

            }
            break;

        }
    }



}
*/
