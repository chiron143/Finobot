package com.purplepath.purplepath.ScoreChartAnalysis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.finobot.finobot.R;
import com.purplepath.purplepath.ScoreChartAnalysis.model.ScoreAnalysisModel;
import com.finobot.finobot.activity.HomePageActivity;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.incomechartdetail.IncomePieChartFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by dinesh on 30/09/16.
 */
public class ScoreChartActivity extends BaseFragment implements View.OnClickListener{

    private RelativeLayout mainLayout;
    private RadarChart mChart;
    // we're going to display pie chart for smartphones martket shares
    private Float[] yData;
    private String[] xData,expectedyData;
    private Context mContext;
    private LinearLayout titleLayout, checkboxLayout;
    private  ArrayList<CheckBox>  userNameCheckBox=new ArrayList<>();
    DisplayMetrics displaymetrics = new DisplayMetrics();
    private ScoreAnalysisModel scoreAnalysisModel;
    private int chartsize, marginsize;
    private Typeface tf;
    GridView  gridview;
    List<String> values=new ArrayList<String>();
    private OnActivityBackPressedListener mCallBackListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        callScoreAnalysisService();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View incomeView =  inflater.inflate(R.layout.fragment_score_chart_view, container, false);
        mainLayout = incomeView.findViewById(R.id.mainLayout);
        titleLayout = incomeView.findViewById(R.id.titleLayout);
        checkboxLayout = incomeView.findViewById(R.id.checkboxLayout);
        mCallBackListener.setActionBarTitle("Prosperity Score");
        gridview = incomeView.findViewById(R.id.gridview);
        mleftRelativeLayout = incomeView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = incomeView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = incomeView.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);



        //chart background programatically fixing with tab
        ShapeDrawable sd = new ShapeDrawable();
        sd.setShape(new RectShape());
        sd.getPaint().setColor(Color.GRAY);
        sd.getPaint().setStrokeWidth(5f);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        mainLayout.setBackground(sd);



        int width = getDeviceWidth();
        double d = width*0.05;
        chartsize = (int)(d);
        width = width-chartsize;

        double margindouble = width*0.03;
        marginsize = (int)(margindouble);

        mChart = incomeView.findViewById(R.id.chart1);
        mChart.setLayoutParams(new RelativeLayout.LayoutParams(width, width));
        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");

        mChart.setDescription("");

        mChart.setWebLineWidth(1.5f);
        mChart.setWebLineWidthInner(0.75f);
        mChart.setWebAlpha(100);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
//        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
//        mChart.setMarkerView(mv);

//        setData();

        mChart.animateXY(
                1400, 1400,
                Easing.EasingOption.EaseInOutQuad,
                Easing.EasingOption.EaseInOutQuad);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(9f);

        YAxis yAxis = mChart.getYAxis();
        yAxis.setTypeface(tf);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinValue(0f);

        Legend l = mChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setTypeface(tf);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        return incomeView;
    }
    public void setData(Float[] yVals, Float[] yDataVals, String[] mPartiess, String[] expectedyData) {
         String[] mParties = new String[]{
                "Liquidity Score", "Insurance Score", "Retirement Score", "Goal Score", "Tax Score", "Income Score",
                 "Expense Score", "Asset Score","Liability Score","Investment Score"
        };
//        values=new ArrayList<String>();
//
//        for(int i=0; i < expectedyData.length ; i++) {
//            if (expectedyData != null) {
//                values.add((xData[i]));
//            }
//        }
//        gridview.setAdapter(new ArrayAdapter<String>(mContext,R.layout.cell,values));

        float mult = 150;
        int cnt = 9;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Entry> yVals2 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < cnt; i++) {
            yVals1.add(new Entry(yVals[i] +3, i));
        }

        for (int i = 0; i < cnt; i++) {
            yVals2.add(new Entry(yVals[i], i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < cnt; i++)
            xVals.add(mParties[i % mParties.length]);

        RadarDataSet set1 = new RadarDataSet(yVals1, "Expected");
        set1.setColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        set1.setFillColor(ColorTemplate.VORDIPLOM_COLORS[0]);
        set1.setDrawFilled(true);
        set1.setLineWidth(2f);

        RadarDataSet set2 = new RadarDataSet(yVals2, "Current");
        set2.setColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        set2.setFillColor(ColorTemplate.VORDIPLOM_COLORS[4]);
        set2.setDrawFilled(true);
        set2.setLineWidth(2f);

        ArrayList<IRadarDataSet> sets = new ArrayList<IRadarDataSet>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(mParties, sets);
        data.setValueTypeface(tf);
        data.setValueTextSize(12f);
        data.setDrawValues(false);
        for (int i = 0; i <  mParties.length-1; i++) {

            LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout parent_layout = new LinearLayout(mContext);
            parent_layout.setWeightSum(2);
            parent_layout.setOrientation(LinearLayout.HORIZONTAL);
            parent_param_layout.setMargins(10, 0, 0, 10);
            parent_layout.setLayoutParams(parent_param_layout);

            LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            parms_left_layout.weight = 1F;
            LinearLayout left_layout = new LinearLayout(mContext);
            left_layout.setOrientation(LinearLayout.HORIZONTAL);
            left_layout.setGravity(Gravity.LEFT);
            left_layout.setLayoutParams(parms_left_layout);

            LinearLayout.LayoutParams parms_legen_layout = new LinearLayout.LayoutParams(45, 45);
            parms_legen_layout.setMargins(20, 10, 20, 10);
            LinearLayout legend_layout = new LinearLayout(mContext);
            legend_layout.setLayoutParams(parms_legen_layout);
            legend_layout.setOrientation(LinearLayout.HORIZONTAL);
//            legend_layout.setBackgroundColor(colors.get(i));
            left_layout.addView(legend_layout);

            TextView txt_unit = new TextView(mContext);
            LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            txt_unit.setLayoutParams(txtLeft_params);
            txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
            txt_unit.setText( xData[i]+" - "+yVals[i]);
            left_layout.addView(txt_unit);
            i++;
            if ( (xData.length-1) == i) {
                break;
            }

            LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
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
//            right_legend_layout.setBackgroundColor(colors.get(i));
            right_layout.addView(right_legend_layout);

            TextView right_txt_unit = new TextView(mContext);
            LinearLayout.LayoutParams txtRight_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            right_txt_unit.setLayoutParams(txtRight_params);
            right_txt_unit.setText( xData[i]+" - "+yVals[i]);
            right_layout.addView(right_txt_unit);

            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            titleLayout.addView(parent_layout);
        }
        mChart.setData(data);

        mChart.invalidate();
    }
    public static IncomePieChartFragment newInstance() {
        Bundle args = new Bundle();
        IncomePieChartFragment fragment = new IncomePieChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

  /*  private void addData(String[] xData, Float[] yData) {
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        for (int i = 0; i <  yData.length-1; i++)
            yVals1.add(new Entry( yData[i], i));

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i <  xData.length-1; i++)
            xVals.add( xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals1, "");
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

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        for (int i = 0; i <  xData.length-1; i++) {

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
            parms_legen_layout.setMargins(0, 0, 20, 0);
            LinearLayout legend_layout = new LinearLayout(mContext);
            legend_layout.setLayoutParams(parms_legen_layout);
            legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            legend_layout.setBackgroundColor(colors.get(i));
            left_layout.addView(legend_layout);

            TextView txt_unit = new TextView(mContext);
            txt_unit.setText( xData[i]);
            left_layout.addView(txt_unit);
            i++;
            if ( (xData.length-1) == i) {
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
            parms_rightlegend_layout.setMargins(0, 0, 20, 0);
            LinearLayout right_legend_layout = new LinearLayout(mContext);
            right_legend_layout.setLayoutParams(parms_rightlegend_layout);
            right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
            right_legend_layout.setBackgroundColor(colors.get(i));
            right_layout.addView(right_legend_layout);

            TextView right_txt_unit = new TextView(mContext);
            right_txt_unit.setText( xData[i]);
            right_layout.addView(right_txt_unit);

            parent_layout.addView(right_layout);
            parent_layout.addView(left_layout);
            titleLayout.addView(parent_layout);
        }
        //  AddCheckBoxView( xData,  yData);
//        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();

    }*/

//    private void AddCheckBoxView(String[] xData, Float[] yData) {
//        for (int i = 0; i < xData.length; i++) {
//            LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            LinearLayout parent_layout = new LinearLayout(mContext);
//            parent_layout.setWeightSum(2);
//            parent_layout.setOrientation(LinearLayout.HORIZONTAL);
//            parent_param_layout.setMargins(10, 0, 0, 10);
//            parent_layout.setLayoutParams(parent_param_layout);
//
//            LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            parms_left_layout.weight = 1F;
//            LinearLayout left_layout = new LinearLayout(mContext);
//            left_layout.setOrientation(LinearLayout.HORIZONTAL);
//            left_layout.setGravity(Gravity.LEFT);
//            left_layout.setLayoutParams(parms_left_layout);
//
//            //Log.e("Possition To Add L", "" + i);
//            userNameCheckBox.add( new CheckBox(mContext));
//            userNameCheckBox.get(i).setId(i);
//            UtileKit.setTextAppearance(mContext,android.R.style.TextAppearance_Small,userNameCheckBox.get(i));
//            userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    int position = compoundButton.getId();
//                    checkBoxOnClick(position,b);
//                    //Log.e("Possition To Add L", "" + position);
//
//                }
//            });
//            userNameCheckBox.get(i).setText(xData[i]);
//            userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext,R.color.app_text_color_gray));
//            left_layout.addView(userNameCheckBox.get(i));
//            i++;
//            if (xData.length == i) {
//                parent_layout.addView(left_layout);
//                checkboxLayout.addView(parent_layout);
//                break;
//            }
//
//            LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            parms_right_layout.weight = 1F;
//            LinearLayout right_layout = new LinearLayout(mContext);
//            right_layout.setOrientation(LinearLayout.HORIZONTAL);
//            right_layout.setGravity(Gravity.LEFT);
//            parent_param_layout.setMargins(10, 0, 0, 10);
//            right_layout.setLayoutParams(parms_right_layout);
//
//            //Log.e("Possition To Add R", "" + i);
//            userNameCheckBox.add(new CheckBox(mContext));
//            userNameCheckBox.get(i).setId(i);
//            UtileKit.setTextAppearance(mContext,android.R.style.TextAppearance_Small,userNameCheckBox.get(i));
//            userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    int position = compoundButton.getId();
//                    checkBoxOnClick(position,b);
//                }
//            });
//            userNameCheckBox.get(i).setText(xData[i]);
//            right_layout.addView(userNameCheckBox.get(i));
//
//            parent_layout.addView(right_layout);
//            parent_layout.addView(left_layout);
//            checkboxLayout.addView(parent_layout);
//
//
//
//
//        }
//        if(!userNameCheckBox.isEmpty())
//            userNameCheckBox.get(userNameCheckBox.size()-1).setChecked(true);
//    }

//    private void checkBoxOnClick(int position, boolean b) {
//        if(position==userNameCheckBox.size()-1&b)
//        {
//            for (int i=0;i<userNameCheckBox.size()-1;i++)
//            {
//                userNameCheckBox.get(i).setChecked(false);
//            }
//            titleLayout.removeAllViews();
//            addData(xData,yData);
//        }
//        else if(b)
//        {
//
//            for (int i=0;i<userNameCheckBox.size();i++)
//            {
//                if(i!=position)
//                    userNameCheckBox.get(i).setChecked(false);
//            }
////            addSingleUserChart(position);
//        }
//        else
//        {
//            //userNameCheckBox.get(userNameCheckBox.size()-1).setChecked(false);
//        }
//    }

//    private void addSingleUserChart(int position) {
//        Float  yDataPercent[]=new Float[6];
//        String xDataName[]= new String[6];
//        if (scoreAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getSi_percent() != null) {
//            yDataPercent[0] = Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getSi_percent());
//            xDataName[0]="Salery Income";
//        }
//        else {
//            yDataPercent[0] = Float.valueOf(0);
//            xDataName[0]="Salery Income";
//        }
//        if (incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIp_percent() != null) {
//            yDataPercent[1] = Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIp_percent());
//            xDataName[1]="Income from Property";
//        }
//        else {
//            yDataPercent[1] = Float.valueOf(0);
//            xDataName[1]="Income from Property";
//        }
//        if (incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIb_percent() != null) {
//            yDataPercent[2] = Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIb_percent());
//            xDataName[2]="Income from Business";
//        }
//        else {
//            yDataPercent[2] = Float.valueOf(0);
//            xDataName[2]="Income from Business";
//        }
//        if (incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getCg_percent() != null) {
//            yDataPercent[3] = Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getCg_percent());
//            xDataName[3]="Capital Gain";
//        }
//        else {
//            yDataPercent[3] = Float.valueOf(0);
//            xDataName[3]="Capital Gain";
//        }
//        if (incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIfs_percent() != null) {
//            yDataPercent[4] = Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(position).getFam_det().getIfs_percent());
//            xDataName[4]="Income from Other Sources";
//        }
//        else {
//            yDataPercent[4] = Float.valueOf(0);
//            xDataName[4]="Income from Other Sources";
//        }
//        yDataPercent[5]=Float.valueOf(0);
//        xDataName[5]="Total";
//        titleLayout.removeAllViews();
//        addData(xDataName,yDataPercent);
//    }

    public void callScoreAnalysisService() {
        WebServiceCalls webServiceObj;
        UtileKit.showSpinnerDialog(mContext,false);
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<ScoreAnalysisModel> call = webServiceObj.callPPScoreAnalysisService(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<ScoreAnalysisModel>() {
            @Override
            public void onResponse(Call<ScoreAnalysisModel> call, Response<ScoreAnalysisModel> response) {
                UtileKit.dismisssSpinnerDialog();
                //Log.e("success",""+response.body());
                scoreAnalysisModel = response.body();
//                ArrayList<Float> chartdatalist = new ArrayList<Float>();
//                ArrayList<String> chartitledatalist = new ArrayList<String>();

                if (scoreAnalysisModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {

                    if(null!=scoreAnalysisModel.getData().getPp_score())
                    {
//                        int size=incomeAnalysisModel.getData().getUser_inc_analysis().size();
                        yData=new Float[11];
                        expectedyData=new String[11];
                        xData= new String[11];
                        yData[0]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getLiq_score());
                        xData[0]= "Liquidity Score";
                        yData[1]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getIns_score());
                        xData[1]= "Insurance Score";

                        yData[2]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getRet_score());
                        xData[2]= "Retirement Score";
                        yData[3]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getGoal_score());
                        xData[3]= "Goal Score";
                        yData[4]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getTax_score());
                        xData[4]= "Tax Score";
                        yData[5]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getInc_score());
                        xData[5]= "Income Score";
                        yData[6]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getExp_score());
                        xData[6]= "Expense Score";
                        yData[7]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getAsst_score());
                        xData[7]= "Asset Score";
                        yData[8]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getLiab_score());
                        xData[8]= "Liability Score";
                        yData[9]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getInvest_score());
                        xData[9]= "Investment Score";




                        expectedyData[0]= " Liquidity Score - " +checkthevalueisnullorNot(scoreAnalysisModel.getData().getPp_score().getLiq_score());

                        expectedyData[1]= "  Insurance Score - "+ checkthevalueisnullorNot(scoreAnalysisModel.getData().getPp_score().getIns_score());

                        expectedyData[2]= "  Retirement Score - " + checkthevalueisnullorNot(scoreAnalysisModel.getData().getPp_score().getRet_score());

                        expectedyData[3]= "  Goal Score - "+ checkthevalueisnullorNot(scoreAnalysisModel.getData().getPp_score().getGoal_score());

                        expectedyData[4]= "  Tax Score - "+ checkthevalueisnullorNot(scoreAnalysisModel.getData().getPp_score().getTax_score());

                        expectedyData[5]= "  Income Score - "+ checkthevalueisnullorNot(scoreAnalysisModel.getData().getPp_score().getInc_score());

                        expectedyData[6]= "  Expense Score - "+ checkthevalueisnullorNot(scoreAnalysisModel.getData().getPp_score().getExp_score());

                        expectedyData[7]= "  Asset Score - "+ checkthevalueisnullorNot(scoreAnalysisModel.getData().getPp_score().getAsst_score());

                        expectedyData[8]= "  Liability Score - "+checkthevalueisnullorNot(scoreAnalysisModel.getData().getPp_score().getLiab_score());

                        expectedyData[9]= "  Investment Score - "+checkthevalueisnullorNot(scoreAnalysisModel.getData().getPp_score().getInvest_score());



                        setData(yData,yData,xData,expectedyData);

//                        yData[10]= Float.parseFloat(scoreAnalysisModel.getData().getPp_score().getPp_score());
//                        xData[10]= "pp_score";

//                        mChart.setCenterText(generateCenterSpannableText());
//                        for(int i=0;i<size;i++)
//                        {
//                            if (incomeAnalysisModel.getData().getUser_inc_analysis().get(i).getIncome_percent() != null)
//
//                                yData[i]=Float.parseFloat(incomeAnalysisModel.getData().getUser_inc_analysis().get(i).getIncome_percent());
//                            else {
//                                yData[i] = Float.valueOf(0);
//                            }
//                            if (!incomeAnalysisModel.getData().getUser_inc_analysis().get(i).getFamilyname().equalsIgnoreCase("null"))
//                                xData[i]=incomeAnalysisModel.getData().getUser_inc_analysis().get(i).getFamilyname();
//                            else
//                                xData[i] = "User";
//                            //Log.e("Sucess","Error"+ yData[i]);
//                        }
//                        yData[size]=Float.valueOf(0);
//                        xData[size]="Total";

                    }
                }
                if(yData!=null) {
//                    addData(xData,yData);
                    //addData(xData,yData);
                }
                UtileKit.dismisssSpinnerDialog();
            }

            @Override
            public void onFailure(Call<ScoreAnalysisModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.dismisssSpinnerDialog();
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
            }
        });

    }

    private String checkthevalueisnullorNot(String check_the_value) {

        String notvalue ="0";
        try{
           if(check_the_value!= null){
               notvalue = check_the_value;

            }
            return notvalue;
        }catch (Exception e){
            e.printStackTrace();
        }
        return notvalue;
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("PurplePath\nTotal Point");
//        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }
    private void setChartDataAsList(ArrayList<Float> chartdatalist, ArrayList<String> chartitledatalist , String value){

        if(UtileKit.validateObjectValues(value) && !value.equalsIgnoreCase("0")) {
            Float  valuepervalue = Float.parseFloat(value);
            chartdatalist.add(valuepervalue);
            chartitledatalist.add(value);
        }

    }

    public int getDeviceWidth(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        return width;
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                mCallBackListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);

                startActivity(i);
                break;

        }

    }
}
