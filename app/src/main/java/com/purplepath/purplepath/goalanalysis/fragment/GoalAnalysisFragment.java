package com.purplepath.purplepath.goalanalysis.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.expensesanalysis.model.User_exp_analysis;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.goalanalysis.model.GoalAnalysisData;
import com.purplepath.purplepath.goalanalysis.model.GoalAnalysisModel;
import com.purplepath.purplepath.goalanalysis.model.Goal_det;
import com.purplepath.purplepath.goaltimeline.GoalTimelineFragmentMainPage;
import com.purplepath.purplepath.goaltimeline.fragment.GoalTimeLineTypeFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GoalAnalysisFragment extends BaseFragment implements
        View.OnClickListener{

    private GoalAnalysisModel goalAnalysisModel;
    private GoalAnalysisData goalAnalysisData;
    private Goal_det goal_det;
    private ArrayList<User_exp_analysis> userExpAnalysis;

    private String short_goal_per, medium_goal_per, long_goal_per;

    private float short_goal_value, medium_goal_value, long_goal_value;

    private RelativeLayout mainLayout;
    private LinearLayout titleLayout;
    private PieChart mChart;
    // we're going to display pie chart for smartphones martket shares
    private Float[] yData;
    private String[] xData;
    private Context mContext;

    private Legend l;
    private LinearLayout checkboxLayout;
    private  ArrayList<CheckBox>  userNameCheckBox=new ArrayList<>();
    private int chartsize, marginsize;
    int width;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout;
    private OnActivityBackPressedListener mCallBackListener;
    private RelativeLayout main_layout;



    public GoalAnalysisFragment() {
        // Required empty public constructor
    }

    public static GoalAnalysisFragment newInstance() {
        GoalAnalysisFragment fragment = new GoalAnalysisFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        setHasOptionsMenu(true);
        mCallBackListener = (OnActivityBackPressedListener) (mContext);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View expanalyView =  inflater.inflate(R.layout.goal_piechart, container, false);
        mCallBackListener.setActionBarTitle("Goal Tracker");

        mainLayout = expanalyView.findViewById(R.id.goal_mainLayout);
        titleLayout  = expanalyView.findViewById(R.id.goal_titleLayout);
        checkboxLayout = expanalyView.findViewById(R.id.goal_checkboxLayout);
        mleftRelativeLayout = expanalyView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = expanalyView.findViewById(R.id.relative_center_home);
        main_layout= expanalyView.findViewById(R.id.main_layout);

        callGoalAnalysisService();
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        width = getDeviceWidth();
         double d = width*0.15;
         chartsize = (int)(d);


         double margindouble = width*0.03;
         marginsize = (int)(margindouble);

         width = width-chartsize;
       //  marginsize = (int)

        mChart = new PieChart(getActivity());
        // add pie chart to main layout
        FrameLayout.LayoutParams frameLayout = new FrameLayout.LayoutParams(width, width);
        frameLayout.setMargins(0,0,0,0);
        mChart.setLayoutParams(frameLayout);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mainLayout.getLayoutParams();
        params.setMargins(0, marginsize, 0, marginsize);
        mainLayout.setLayoutParams(params);
        mainLayout.addView(mChart);

        RelativeLayout.LayoutParams title_params = (RelativeLayout.LayoutParams)titleLayout.getLayoutParams();
        title_params.setMargins(0, 0, 0, marginsize);
        titleLayout.setLayoutParams(title_params);

        mChart.setUsePercentValues(false);
        mChart.setDescription("");


//
//
//        // enable hole and configure
//        mChart.setDrawHoleEnabled(true);
//        mChart.setDrawCenterText(true);
//        mChart.setCenterTextColor(ContextCompat.getColor(mContext, R.color.Gray));
//        mChart.setCenterText("Goal");
//
////        mChart.setHoleColorTransparent(false);
//        mChart.setHoleColor(ContextCompat.getColor(mContext, R.color.white));
//        mChart.setHoleRadius(7);
//        mChart.setTransparentCircleRadius(10);
//
//        // enable rotation of the chart by touch
//        mChart.setRotationAngle(0);
//        mChart.setRotationEnabled(true);
//        mChart.setDrawSliceText(false);


        // set a chart value selected listener


        //   mainLayout.setBackgroundColor(Color.parseColor("#ffffff"));

        // configure pie chart
        mChart.setUsePercentValues(false);
        mChart.setDescription("");

        // enable hole and configure
        mChart.setDrawHoleEnabled(true);
//        mChart.setHoleColorTransparent(true);
//        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
        mChart.setRotationAngle(0);
        mChart.setRotationEnabled(true);

        mChart.setDrawSliceText(false);

        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.i("GoalAnalysisFragment","setOnChartValueSelectedListener"+ e.getXIndex() +"get value"+e.getVal());
                Log.i("GoalAnalysisFragment","setOnChartValueSelectedListenerdataSetIndex"+ dataSetIndex );
                Log.i("GoalAnalysisFragment","setOnChartValueSelectedListener Highlight"+ h );
                /*
                * e.getXIndex() is equal to ZERO means then set the value   MEDIUM GOAL  and send it next Fragment
                *e.getXIndex() is equal to ONE means then set the value   LONG GOAL   and send it next Fragment
                *
                * */



                if(e.getXIndex()==0){
                    addFragmenttoStack(GoalTimeLineTypeFragment.newInstance( "MEDIUM GOAL"));
                }else if(e.getXIndex() == 1){
                    addFragmenttoStack(GoalTimeLineTypeFragment.newInstance( "LONG GOAL"));
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });


        // add data

        // customize legends
        l = mChart.getLegend();
        l.setEnabled(false);
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setXEntrySpace(10);
        l.setYEntrySpace(10);

       return expanalyView;
    }

    private void AddCheckBoxView(String[] xData, Float[] yData) {
        for (int i = 0; i < xData.length; i++) {
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

            //Log.e("Possition To Add L", "" + i);
            userNameCheckBox.add( new CheckBox(mContext));
            userNameCheckBox.get(i).setId(i);
            userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = compoundButton.getId();
                    checkBoxOnClick(position,b);
                    //Log.e("Possition To Add L", "" + position);

                }
            });
            userNameCheckBox.get(i).setText(xData[i]);
            userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext,R.color.app_text_color_gray));
            left_layout.addView(userNameCheckBox.get(i));
            i++;
            if (xData.length == i) {
                parent_layout.addView(left_layout);
                checkboxLayout.addView(parent_layout);
                break;
            }

            LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_right_layout.weight = 1F;
            LinearLayout right_layout = new LinearLayout(mContext);
            right_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_layout.setGravity(Gravity.LEFT);
            parent_param_layout.setMargins(10, 0, 0, 10);
            right_layout.setLayoutParams(parms_right_layout);

            //Log.e("Possition To Add R", "" + i);
            userNameCheckBox.add(new CheckBox(mContext));
            userNameCheckBox.get(i).setId(i);
            userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    int position = compoundButton.getId();
                    checkBoxOnClick(position,b);
                }
            });
            userNameCheckBox.get(i).setText(xData[i]);
            right_layout.addView(userNameCheckBox.get(i));

            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            checkboxLayout.addView(parent_layout);

        }
        if(!userNameCheckBox.isEmpty())
            userNameCheckBox.get(userNameCheckBox.size()-1).setChecked(true);
    }

    private void checkBoxOnClick(int position, boolean b) {
        if(b)
        {
            for (int i=0;i<userNameCheckBox.size();i++)
            {
                if(i!=position)
                    userNameCheckBox.get(i).setChecked(false);
            }
            //addSingleUserChart(position);
        }

    }

    private void addData() {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i < yData.length; i++)
            yVals1.add(new Entry(yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < xData.length; i++)
            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "Market Share");
        dataSet.setSliceSpace(0);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

    //    int colorcodes[] = l.getColors();
        for(int i=0 ; i<xData.length; i++) {
            LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout parent_layout = new LinearLayout(mContext);
            parent_layout.setWeightSum(3);
            parent_layout.setOrientation(LinearLayout.HORIZONTAL);
            parent_param_layout.setMargins(10,0,0,10);
            parent_layout.setLayoutParams(parent_param_layout);

            LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_left_layout.weight = 1F;
            parms_left_layout.setMargins(5, 0, 5, 0);
            LinearLayout left_layout = new LinearLayout(mContext);
            left_layout.setOrientation(LinearLayout.HORIZONTAL);
            left_layout.setGravity(Gravity.LEFT);
            left_layout.setLayoutParams(parms_left_layout);

            LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(40, 40);
            parms_left_layout.setMargins(0, 0, 10, 0);
            LinearLayout legend_layout = new LinearLayout(mContext);
            legend_layout.setLayoutParams(parms_legen_layout);
            legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            legend_layout.setBackgroundColor(colors.get(i));
            left_layout.addView(legend_layout);

            TextView txt_unit = new TextView(mContext);
            LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            txt_unit.setLayoutParams(txtLeft_params);
            txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
            txt_unit.setText(xData[i]);
            left_layout.addView(txt_unit);

            i++;
            if(i>=xData.length){
                parent_layout.addView(left_layout);
                titleLayout.addView(parent_layout);
                break;
            }

            LinearLayout.LayoutParams parms_middle_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_middle_layout.weight = 1F;
            LinearLayout middle_layout = new LinearLayout(mContext);
            middle_layout.setOrientation(LinearLayout.HORIZONTAL);
            middle_layout.setGravity(Gravity.LEFT);
            parms_middle_layout.setMargins(5,0,5,0);
            middle_layout.setLayoutParams(parms_middle_layout);

            LinearLayout.LayoutParams parms_middlelegend_layout = new LinearLayout.LayoutParams(40, 40);
            parms_middlelegend_layout.setMargins(0, 0, 10, 0);
            LinearLayout middle_legend_layout = new LinearLayout(mContext);
            middle_legend_layout.setLayoutParams(parms_middlelegend_layout);
            middle_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            middle_legend_layout.setBackgroundColor(colors.get(i));
            middle_layout.addView(middle_legend_layout);

            TextView middle_txt_unit = new TextView(mContext);
            middle_txt_unit.setText(xData[i]);
            middle_layout.addView(middle_txt_unit);
            i++;
            if(i>=xData.length){
                parent_layout.addView(middle_layout);
                parent_layout.addView(left_layout);
                titleLayout.addView(parent_layout);
                break;
            }


            LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            parms_right_layout.weight = 1F;
            LinearLayout right_layout = new LinearLayout(mContext);
            right_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_layout.setGravity(Gravity.LEFT);
            parent_param_layout.setMargins(5,0,5,0);
            right_layout.setLayoutParams(parms_right_layout);

            LinearLayout.LayoutParams parms_rightlegend_layout = new LinearLayout.LayoutParams(40, 40);
            parms_rightlegend_layout.setMargins(0, 0, 10, 0);
            LinearLayout right_legend_layout = new LinearLayout(mContext);
            right_legend_layout.setLayoutParams(parms_rightlegend_layout);
            right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_legend_layout.setBackgroundColor(colors.get(i));
            right_layout.addView(right_legend_layout);

            TextView right_txt_unit = new TextView(mContext);
            LinearLayout.LayoutParams txtRight_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            right_txt_unit.setLayoutParams(txtRight_params);
            right_txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
            right_txt_unit.setText(xData[i]);
            right_layout.addView(right_txt_unit);


            parent_layout.addView(middle_layout);
            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            titleLayout.addView(parent_layout);

        }
        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void callGoalAnalysisService() {
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<GoalAnalysisModel> call = webServiceObj.callGoalAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<GoalAnalysisModel>() {
            @Override
            public void onResponse(Call<GoalAnalysisModel> call, Response<GoalAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success",""+response.body());
                goalAnalysisModel = response.body();
                ArrayList<Float> chartdatalist = new ArrayList<Float>();
                ArrayList<String> chartitledatalist = new ArrayList<String>();
                if (goalAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                    goalAnalysisData = goalAnalysisModel.getData();
                    goal_det = goalAnalysisData.getGoal_det();

                    short_goal_value = goal_det.getShort_goal().size();
                    medium_goal_value = goal_det.getMed_goal().size();
                    long_goal_value = goal_det.getLong_goal().size();

                    setChartDataAsList(chartdatalist, chartitledatalist, short_goal_value, mContext.getResources().getString(R.string.short_goal));
                    setChartDataAsList(chartdatalist, chartitledatalist, medium_goal_value, mContext.getResources().getString(R.string.medium_goal));
                    setChartDataAsList(chartdatalist, chartitledatalist, long_goal_value, mContext.getResources().getString(R.string.long_goal));
                }
                if(UtileKit.validateObjectValues(chartdatalist)
                        && UtileKit.validateObjectValues(chartitledatalist) &&
                        !chartdatalist.isEmpty() && !chartitledatalist.isEmpty()) {
                    yData = new Float[chartdatalist.size()];
                    yData = chartdatalist.toArray(yData);

                    xData = new String[chartitledatalist.size()];
                    xData = chartitledatalist.toArray(xData);
                    addData();
                    try {
                        AddCheckBoxView(xData, yData);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<GoalAnalysisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }



    private void setChartDataAsList(ArrayList<Float> chartdatalist, ArrayList<String> chartitledatalist ,
                                    float value, String title){

        if(UtileKit.validateObjectValues(value) && value>0) {
             Float  valuepervalue = value;
            chartdatalist.add(valuepervalue);
            chartitledatalist.add(title);
        }

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
//                MutipleGoalDialog mutilplegoaldialog = new MutipleGoalDialog();
//                showFragment(mutilplegoaldialog);
                GoalTimelineFragmentMainPage mGoalTimelineFragmentMainPage = new GoalTimelineFragmentMainPage();
                showFragment(mGoalTimelineFragmentMainPage);

                break;
            case R.id.menu_summary:

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
}
