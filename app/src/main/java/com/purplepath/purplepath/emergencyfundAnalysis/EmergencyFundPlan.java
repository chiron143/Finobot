package com.purplepath.purplepath.emergencyfundAnalysis;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.purplepath.purplepath.customview.ChartData;
import com.purplepath.purplepath.customview.WaterFallChartCustom;
import com.purplepath.purplepath.emergencyfundAnalysis.model.EmergencyFundModel;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmergencyFundPlan extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    CheckBox ef_planCheckBox,ef_actCheckBox;
    WaterFallChartCustom waterFallChart,waterFallChart2;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    static public List<ChartData> value1 = new ArrayList();
    static public List<ChartData> value2 = new ArrayList ();
    private LinearLayout parentView;
    EmergencyFundModel emergencyFundModel;
    Toolbar toolbar;
    Context mContext;
    TextView errorTextview;
    View emergencyFundView=null;
    private OnActivityBackPressedListener mCallBackListener;
    private Boolean isEmptyView;
    private String  xValueString[]={"Cash %","Savings A/C","Current A/C","Fixed/Reccuring deposit","Term Deposit","Overall %"};
    GridView gridview;
    List<String> values=new ArrayList<String>();
    public int order = 0;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        emergencyFundView =inflater.inflate(R.layout.fragment_emergency_fund_plan, container, false);
//            errorTextview = (TextView) emergencyFundView.findViewById(R.id.errorTextview);
        mleftRelativeLayout = emergencyFundView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = emergencyFundView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = emergencyFundView.findViewById(R.id.relative_right_arrow);
        parentView= emergencyFundView.findViewById(R.id.parentViewId);

        mContext=getContext();
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);

        waterFallChart = emergencyFundView. findViewById(R.id.waterfallView1Id);
        waterFallChart2 = emergencyFundView. findViewById(R.id.waterfallView2Id);
        gridview = emergencyFundView.findViewById(R.id.gridview2);


        value1=EmergencyFundParent.value1;
        value2=EmergencyFundParent.value2;
        loadChart();
        loadLegends();

           /* if(value1.isEmpty()&&value2.isEmpty())
                callEmergFundAnalyService();*/
        // }catch ( NullPointerException e) {} catch (Exception e) {e.printStackTrace();}

        return emergencyFundView;
    }

    private void loadLegends() {
//        for (int i = 0; i < (value1.size()); i++) {
//
//            LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            LinearLayout parent_layout = new LinearLayout(mContext);
//            parent_layout.setWeightSum(2);
//            parent_layout.setOrientation(LinearLayout.HORIZONTAL);
//            parent_param_layout.setMargins(10, 0, 0, 10);
//            parent_layout.setLayoutParams(parent_param_layout);
//
//
//            LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            parms_left_layout.weight = 1F;
//            LinearLayout left_layout = new LinearLayout(mContext);
//            left_layout.setOrientation(LinearLayout.HORIZONTAL);
//            left_layout.setGravity(Gravity.LEFT);
//            parent_param_layout.setMargins(10, 0, 0, 10);
//            left_layout.setLayoutParams(parms_left_layout);
//
//
//            TextView txt_unit = new TextView(mContext);
//            txt_unit.setText((i+1)+" : "+xValueString[i]);
//
//            left_layout.addView(txt_unit);
//            i++;
//            if ((xValueString.length ) == i) {
//                parent_layout.addView(left_layout);
//                parentView.addView(parent_layout);
//                break;
//            }
//
//            LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            parms_right_layout.weight = 1F;
//            LinearLayout right_layout = new LinearLayout(mContext);
//            right_layout.setOrientation(LinearLayout.HORIZONTAL);
//            right_layout.setGravity(Gravity.LEFT);
//            parent_param_layout.setMargins(10, 0, 0, 10);
//            right_layout.setLayoutParams(parms_right_layout);
//
//
//            TextView right_txt_unit = new TextView(mContext);
//            right_txt_unit.setText((i+1)+" : "+xValueString[i]);
//
//            right_layout.addView(right_txt_unit);
//
//
//            parent_layout.addView(left_layout);
//            parent_layout.addView(right_layout);
//            parentView.addView(parent_layout);
//        }
        if(EmergencyFundParent.emup1 == 1) {
            order++;
            values.add(String.valueOf(order)+" : "+xValueString[EmergencyFundParent.emup1 - 1]);

        }
        if(EmergencyFundParent.emup2 == 2 ) {
            order++;
            values.add(String.valueOf(order) + " : " + xValueString[EmergencyFundParent.emup2 - 1]);
        }
        if(EmergencyFundParent.emup3 == 3) {
            order++;
            values.add(String.valueOf(order) + " : " + xValueString[EmergencyFundParent.emup3 - 1]);
        }
        if(EmergencyFundParent.emup4 == 4 ) {
            order++;
            values.add(String.valueOf(order) + " : " + xValueString[EmergencyFundParent.emup4 - 1]);
        }
        if(EmergencyFundParent.emup5 == 5) {
            order++;
            values.add(String.valueOf(order) + " : " + xValueString[EmergencyFundParent.emup5 - 1]);
        }
        values.add(String.valueOf(value1.size())+" : "+xValueString[5]);

        gridview.setAdapter(new ArrayAdapter<String>(mContext,R.layout.cell2,values));

        EmergencyFundParent.emup1 = 0;
        EmergencyFundParent.emup2 = 0;
        EmergencyFundParent.emup3 = 0;
        EmergencyFundParent.emup4 = 0;
        EmergencyFundParent.emup5 = 0;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

      /*  if(ef_planCheckBox.isChecked()&&ef_actCheckBox.isChecked())
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
        }*/
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
        // value2.add(new ChartData((float)0, "act_overall_per"));
        if(!value2.isEmpty()) {
            waterFallChart2.setData(value2);
            waterFallChart2.setEnabled(true);
            waterFallChart2.setVisibility(View.GONE);

        }
        else
        {
//            value2.add(new ChartData(0f, ""));
            waterFallChart2.setVisibility(View.GONE);
        }
    }

//    public void addFragmentToActivity(Fragment fragment)
//    {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.commitAllowingStateLoss();
//    }
  /*  public void callEmergFundAnalyService() {
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
//                            Fragment fragment = new EmergencyFundChart();
//                            Bundle mvalue = new Bundle();
//                            mvalue.putSerializable("chart1", (Serializable) value1);
//                            mvalue.putSerializable("chart2", (Serializable) value2);
                            EmergencyFundChart.value1=value;
                            EmergencyFundChart.value2=value1;
                           // addFragmentToActivity(fragment);
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

*/
}
