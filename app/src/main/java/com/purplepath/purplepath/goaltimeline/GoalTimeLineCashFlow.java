package com.purplepath.purplepath.goaltimeline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goalanalysis.singlegoal.SingleGoalDonutview;
import com.purplepath.purplepath.goaltimeline.getGoalPlanModel.GoalPlanModel;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Suresh on 27/07/17.
 */

public class GoalTimeLineCashFlow extends BaseFragment implements
        OnChartValueSelectedListener, View.OnClickListener {

    private Context mContext;

    private OnActivityBackPressedListener mCallBackListener;

    private HorizontalBarChart mChart;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private GoalPlanModel mGoalPlanModel;

    private LinearLayout parentView;

    BarData d;

    private String mCost, mEarning, mSavings , mBegValue;

    private String goalId,goalName,yearof_goals;

    private String expected_increment;

    ArrayList<IBarDataSet> dataSets = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e){
            e.printStackTrace();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_cash_flow, container, false);
        mChart = view.findViewById(R.id.chartview);

        parentView= view.findViewById(R.id.parentViewId);

        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);


        try{
            Bundle args=getArguments();

            if(args!=null){
                if(args.containsKey("mGoalplanModel")) {
                    mGoalPlanModel = (GoalPlanModel) args.getSerializable("mGoalplanModel");
                }
                if(getArguments().containsKey("mGoalId")) {
                    goalId = getArguments().getString("mGoalId");
                }

                if(getArguments().containsKey("mGoalName")) {
                    goalName = getArguments().getString("mGoalName");
                }
                if(getArguments().containsKey("mGoalyears"))
                    yearof_goals = getArguments().getString("mGoalyears");

                if(getArguments().containsKey("mExpectedincrement"))
                    expected_increment=getArguments().getString("mExpectedincrement");
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        if(mGoalPlanModel!= null){
            setOverallChart(mGoalPlanModel);
            String[] xData={"Fulfilment","Shortfall","Savings","Earnings"};
            int[] chartColor = {ColorTemplate.rgb("#05bc05"),ColorTemplate.rgb("#ff1919")
                    ,ColorTemplate.rgb("#6f79ff"),ColorTemplate.rgb("#ff8a00") };
            setDataForCheckbox(xData,chartColor);
        }else{
            callGetGoalService();
        }

        try {

            int width = getDeviceWidth();
            double d = width * 0.25;
            int chartsize = (int) (d);

            mChart.setOnChartValueSelectedListener(this);
            mChart.setLayoutParams(new LinearLayout.LayoutParams(width, width + width / 4));
            mChart.setDrawGridBackground(false);
//            mChart.setDescription("");
            mChart.getXAxis().setEnabled(true);
            mChart.getAxisLeft().setEnabled(true);
            mChart.getAxisRight().setEnabled(true);

            // scaling can now only be done on x- and y-axis separately
            mChart.setPinchZoom(false);
            mChart.setDoubleTapToZoomEnabled(false);
            mChart.setDrawBarShadow(false);
            mChart.setDrawValueAboveBar(false);
            mChart.setTouchEnabled(false);
            mChart.getAxisLeft().setEnabled(false);

            mChart.getAxisRight().setDrawGridLines(false);
            mChart.getAxisRight().setDrawZeroLine(true);
            mChart.getAxisRight().setLabelCount(7, false);
            //Dinesh value formate
//            mChart.getAxisRight().setValueFormatter(new ChartValueFormatter());


            mChart.getAxisRight().setTextSize(9f);

            XAxis xAxis = mChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED);
            xAxis.setDrawGridLines(true);
            xAxis.setDrawAxisLine(false);
            xAxis.setTextSize(9f);

            Legend l = mChart.getLegend();
            l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
            l.setFormSize(8f);
            l.setFormToTextSpace(4f);
            l.setXEntrySpace(6f);


        }catch (Exception e){
            e.printStackTrace();
        }
            return view;
    }



    public void callGetGoalService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(getActivity(), false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalPlanModel> call = webServiceObj.callGetGoalService(UtileKit.getPersistedPurplePathPref("user_id"),"1");
        call.enqueue(new Callback<GoalPlanModel>() {
            @Override
            public void onResponse(Call<GoalPlanModel> call, Response<GoalPlanModel> response) {
                UtileKit.dismisssSpinnerDialog();
                Log.i("success", "callGetGoalService" + response.body());
                mGoalPlanModel = response.body();
                if(mGoalPlanModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    setOverallChart(mGoalPlanModel);

                    String[] xData={"Fulfilment","Shortfall","Savings","Earnings"};
                    int[] chartColor = {ColorTemplate.rgb("#05bc05"),ColorTemplate.rgb("#ff1919")
                            ,ColorTemplate.rgb("#6f79ff"),ColorTemplate.rgb("#ff8a00") };
                    setDataForCheckbox(xData,chartColor);
                }else{

                    UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                }


            }
            @Override
            public void onFailure(Call<GoalPlanModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( getActivity(),t);
            }
        });
    }

    private void setOverallChart(GoalPlanModel mGoalPlanModel) {
        String[] xData={"Fulfilment","Shortfall","Savings","Earnings"};
        int[] chartColor1 = {ColorTemplate.rgb("#6f79ff")};
        int[] chartColor2 = {ColorTemplate.rgb("#ff8a00")};
        int[] chartColor3 = {  ColorTemplate.rgb("#05bc05")};
        int[] chartColor4 = {ColorTemplate.rgb("#ff1919")};

        if (mGoalPlanModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {


            int size = mGoalPlanModel.getData().getGoal_plan().size();
            String[] xVals = new String[size];


//
            for(int i=0; i<size; i++){
                int k=i;
                try {

                    if(mGoalPlanModel.getData().getGoal_plan().get(size-1-i).getCost()!= null){
                        mCost = mGoalPlanModel.getData().getGoal_plan().get(size-1-i).getCost();
                    }else{
                        mCost ="0";
                    }
                    if(mGoalPlanModel.getData().getGoal_plan().get(size-1-i).getSavings()!= null){
                        mSavings = mGoalPlanModel.getData().getGoal_plan().get(size-1-i).getSavings();
                    }else{
                        mSavings ="0";
                    }
                    if(mGoalPlanModel.getData().getGoal_plan().get(size-1-i).getEarnings()!= null){
                        mEarning = mGoalPlanModel.getData().getGoal_plan().get(size-1-i).getEarnings();
                    }else{
                        mEarning ="0";
                    }
                    if(mGoalPlanModel.getData().getGoal_plan().get(size-1-i).getBeg_val()!= null){
                        mBegValue = mGoalPlanModel.getData().getGoal_plan().get(size-1-i).getBeg_val();
                    }else{
                        mBegValue ="0";
                    }
                    xVals[i] = mGoalPlanModel.getData().getGoal_plan().get(size-1-i).getYear();


//                    ArrayList<Float> cashFlowArray = new ArrayList<Float>();

//                        cashFlowArray.add(floatConvertion(mSavings.replace("-", "")));
                    ArrayList<BarEntry> yAxisChartValues = new ArrayList<BarEntry>();
                    yAxisChartValues.add(new BarEntry(floatConvertion(mSavings.replace("-", "")), i));

//                    cashFlowArray.add(floatConvertion(mEarning.replace("-", "")));
                    BarDataSet set = new BarDataSet( yAxisChartValues,xVals[i]);

//                    set.setValueTextSize(7f);
                    set.setAxisDependency(YAxis.AxisDependency.RIGHT);
                    set.setBarSpacePercent(50f);
                    set.setColors(chartColor1);
                    //set.setBarSpacePercent(10f);
//                    set.setValueFormatter(new ChartValueFormatter());
                    dataSets.add(set);
                    ArrayList<BarEntry> yAxisChartValues2 = new ArrayList<BarEntry>();

                    yAxisChartValues2.add(new BarEntry(floatConvertion(mEarning.replace("-", "")), i));
//                    cashFlowArray.add(floatConvertion(mEarning.replace("-", "")));
                    BarDataSet set2 = new BarDataSet( yAxisChartValues2,xVals[i]);

//                    set2.setValueTextSize(7f);
                    set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
                    set2.setBarSpacePercent(50f);
                    set2.setColors(chartColor2);
                   // set2.setBarBorderWidth(50f);
                    //set2.setBarSpacePercent(10f);
//                    set2.setValueFormatter(new ChartValueFormatter());
                    dataSets.add(set2);
                    ArrayList<BarEntry> yAxisChartValues3 = new ArrayList<BarEntry>();
                    ArrayList<BarEntry> yAxisChartValues4 = new ArrayList<BarEntry>();
                    if(mGoalPlanModel.getData().getGoal_plan().get(size-i-1).getShade().equalsIgnoreCase("100")) {
//                        cashFlowArray.add(-floatConvertion(mCost.replace("-", "")));
//                        cashFlowArray.add(-floatConvertion(mBegValue.replace("-", "")));
                        yAxisChartValues3.add(new BarEntry(-floatConvertion(mCost.replace("-", "")), i));
                        yAxisChartValues4.add(new BarEntry(-floatConvertion(mBegValue.replace("-", "")), i));
                    }
                    else {
                        yAxisChartValues3.add(new BarEntry(floatConvertion("0"), i));
                        yAxisChartValues4.add(new BarEntry(floatConvertion("0"), i));
//                        cashFlowArray.add(floatConvertion("0"));
//                        cashFlowArray.add(floatConvertion("0"));
                    }

                    BarDataSet set3 = new BarDataSet( yAxisChartValues3,xVals[i]);

//                    set3.setValueTextSize(7f);
                    set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
                    set3.setBarSpacePercent(50f);
                    set3.setColors(chartColor3);
                   // set3.setBarSpacePercent(10f);
//                    set3.setValueFormatter(new ChartValueFormatter());
                    dataSets.add(set3);
                    BarDataSet set4 = new BarDataSet( yAxisChartValues4,xVals[i]);
                   // set4.setBarSpacePercent(10f);

//                    set4.setValueTextSize(7f);
                    set4.setAxisDependency(YAxis.AxisDependency.RIGHT);
                    set4.setBarSpacePercent(50f);
//                    barDataSet.setBarSpacePercent(((float) (mGoalPlanModel.getData().getGoal_plan().size() - barData.getXValCount()) / (float) COUNT) * 100f);

                    set4.setColors(chartColor4);
//                    set4.setValueFormatter(new ChartValueFormatter());
                    dataSets.add(set4);
//                    yAxisChartValues.add(new BarEntry(getFloatArray(cashFlowArray), i));

//                    BarDataSet set = new BarDataSet(yAxisChartValues, xVals[i]);
//                    set.setValueTextSize(7f);
//                    set.setAxisDependency(YAxis.AxisDependency.RIGHT);
////                    set.setBarSpacePercent(40f);
//                    set.setColors(chartColor);
//                    set.setDrawValues(false);
//                    set.setValueFormatter(new ChartValueFormatter());



//                    float groupSpace = 0.06f;
//                    float barSpace = 0.02f; // x2 dataset
//                    float barWidth = 0.45f; // x2 dataset
//                    // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
//
//                   // BarData d = new BarData(dataSets);
//                   // d.setBarWidth(barWidth);
//
//                    // make this BarData object grouped
//                    //d.groupBars(0, groupSpace, barSpace);
//
//
//
//                    // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
//
//                     d = new BarData(set, set2,set3,set4);
//                    d.setBarWidth(barWidth);
//
//                    // make this BarData object grouped
//                    d.groupBars(0, groupSpace, barSpace);



                }catch (Exception e){
                    e.printStackTrace();
                }
            }

//            mChart.setData(d);
//            mChart.invalidate();
//            BarDataSet set = new BarDataSet(yAxisChartValues, "");
//            set.setValueTextSize(7f);
//            set.setAxisDependency(YAxis.AxisDependency.RIGHT);
//            set.setBarSpacePercent(40f);
//            set.setColors(chartColor);
//            set.setDrawValues(false);
//            set.setValueFormatter(new ChartValueFormatter());
//
//            float groupSpace = 0.06f;
//            float barSpace = 0.02f; // x2 dataset
//            float barWidth = 0.45f; // x2 dataset
//
//             BarData data = new BarData(xData,dataSets);
//            //data.setGroupSpace(0.005f);
//            data.setDrawValues(false);
           // data.setBarWidth(barWidth); // set the width of each bar
//            mChart.setData(data);
//            mChart.groupBars(1980f, groupSpace, barSpace); // perform the "explicit" grouping
//            mChart.invalidate(); // refresh


            float groupSpace = 0.6f;
            float barSpace = 10f; // x2 dataset
            float barWidth = 2f; // x2 dataset
            // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

            // BarData d = new BarData(dataSets);
            // d.setBarWidth(barWidth);

            // make this BarData object grouped
            //d.groupBars(0, groupSpace, barSpace);git add  MPChartLib/src



            // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"

            d = new BarData(xVals,dataSets);
            d.setBarWidth(barWidth);
            d.setDrawValues(false);

            // make this BarData object grouped
           // d.groupBars(10, groupSpace, barSpace);

            //mChart.setMinimumWidth(50);
            mChart.setData(d);
//            mChart.setDescription("");
            mChart.invalidate();
            mChart.setDrawValueAboveBar(false);
           // mChart.getBarData().setBarWidth(50f);
           // mChart.groupBars(15f,0.05f,0.02f);



            Legend l = mChart.getLegend();
            l.setEnabled(false);
            l.setTextSize(8 * getResources().getDisplayMetrics().density);
            l.setFormSize(15f);
            l.setWordWrapEnabled(true);
            l.setXEntrySpace(15f);


        }else{

        }

    }
    private float[] getFloatArray(List<Float> comm_goldArray) {
        float array[] = new float[comm_goldArray.size()];
        for (int i = 0; i < comm_goldArray.size(); i++) {
            array[i] = comm_goldArray.get(i);
        }
        return array;
    }





    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public static GoalTimeLineCashFlow newInstance(GoalPlanModel mGoalPlanModel,
                                                   String goalId, String goldName,
                                                   String years,String mExpectedincrement) {

        Bundle args = new Bundle();
        args.putSerializable("mGoalplanModel",mGoalPlanModel);
        args.putString("mGoalId",goalId);
        args.putString("mGoalName",goldName);
        args.putString("mGoalyears",years);
        args.putString("mExpectedincrement",mExpectedincrement);
        GoalTimeLineCashFlow fragment = new GoalTimeLineCashFlow();
        fragment.setArguments(args);
        return fragment;
    }




    private float floatConvertion(String val) {
        float convVal = 0;
        try {
            convVal = Math.round(Float.parseFloat(val));
        } catch (NumberFormatException e) {
            e.printStackTrace();

        }
        return convVal;
    }


    private void setDataForCheckbox(String[] xDataCheckbox, int [] colour) {
        try {
            for (int i = 0; i <  xDataCheckbox.length; i++) {

                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                parent_param_layout.setMargins(10, 0, 0, 10);
                parent_layout.setLayoutParams(parent_param_layout);

                LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parms_left_layout.weight = 1F;
                LinearLayout left_layout = new LinearLayout(mContext);
                left_layout.setOrientation(LinearLayout.HORIZONTAL);
                left_layout.setGravity(Gravity.LEFT);
                left_layout.setLayoutParams(parms_left_layout);

                LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(45, 45);
                parms_legen_layout.setMargins(20,10,20,10);
                LinearLayout legend_layout = new LinearLayout(mContext);
                legend_layout.setLayoutParams(parms_legen_layout);
                legend_layout.setOrientation(LinearLayout.HORIZONTAL);
                legend_layout.setBackgroundColor(colour[i]);
                left_layout.addView(legend_layout);

                TextView txt_unit = new TextView(mContext);
                parms_legen_layout.setMargins(20, 10, 20, 10);
                LinearLayout.LayoutParams left_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txt_unit.setLayoutParams(left_params);
                txt_unit.setText( xDataCheckbox[i]);
                txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                left_layout.addView(txt_unit);
                i++;
                if ( (xDataCheckbox.length) == i) {
                    parent_layout.addView(left_layout);
                    parentView.addView(parent_layout);
                    break;
                }

                LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parms_right_layout.weight = 1F;
                LinearLayout right_layout = new LinearLayout(mContext);
                right_layout.setOrientation(LinearLayout.HORIZONTAL);
                right_layout.setGravity(Gravity.LEFT);
                parent_param_layout.setMargins(10, 0, 0, 10);
                right_layout.setLayoutParams(parms_right_layout);


                LinearLayout.LayoutParams parms_rightlegend_layout = new LinearLayout.LayoutParams(45, 45);
                parms_rightlegend_layout.setMargins(20, 10, 20, 10);
                LinearLayout right_legend_layout = new LinearLayout(mContext);
                right_legend_layout.setLayoutParams(parms_rightlegend_layout);
                right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
                right_legend_layout.setBackgroundColor(colour[i]);
                right_layout.addView(right_legend_layout);

                TextView right_txt_unit = new TextView(mContext);
                LinearLayout.LayoutParams txtRight_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                right_txt_unit.setLayoutParams(txtRight_params);
                right_txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                right_txt_unit.setText( xDataCheckbox[i]);
                right_layout.addView(right_txt_unit);

                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

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
                GoalTableViewFragment mGoalTableLineCashFlow = GoalTableViewFragment.newInstance( goalId,goalName,
                        yearof_goals,expected_increment);
                showFragment(mGoalTableLineCashFlow);
                break;
            case R.id.menu_summary:
                SingleGoalDonutview mSingleGoalDonutview = SingleGoalDonutview.newInstance( goalId,goalName,
                        "GoalView", yearof_goals,expected_increment);
                showFragment(mSingleGoalDonutview);
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
            }
            break;
        }
    }
//    @Override
//    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//
//    }

}
