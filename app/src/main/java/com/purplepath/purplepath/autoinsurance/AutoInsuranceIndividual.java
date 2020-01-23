/*
package com.purplepath.purplepath.autoinsurance;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.healthinsurance.IndividualHealthPlan;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;

import java.math.BigInteger;
import java.util.ArrayList;

*/
/**
 * Created by pravinr on 11/23/17.
 *//*


public class AutoInsuranceIndividual extends BaseFragment implements View.OnClickListener {


    Context mContext;
    TextView errorTextview;
    private LinearLayout parentView;
    private HorizontalBarChart auto_individual_chart;
    Motor_Proper_Health_Model motor_Proper_Health_Model;
    private ArrayList<BarEntry> BARENTRYHORIZONTAL ;
    private ArrayList<Float> unitCount ;
    private Typeface tf;
    private BarDataSet BardatasetHorizontal ;
    private ScrollView layout_Scroll;

    private LinearLayout checkboxLayout;
    private  ArrayList<CheckBox>  userNameCheckBox=new ArrayList<>();

    ArrayList<String> xDatadetailscolor = new ArrayList<String>();
    ArrayList<String> xDatadetailsData = new ArrayList<String>();

    float[]  xINSURANCE=null;
    float[]  xBIKECAR=null;
    int [] colour=new int[5];

    private BigInteger bike_value=BigInteger.ZERO;
    private BigInteger car_value=BigInteger.ZERO;

    private BigInteger bike_insurance=BigInteger.ZERO;
    private BigInteger car_insurance=BigInteger.ZERO;

    private BigInteger car_bike_over_value=BigInteger.ZERO;
    private BigInteger car_bike_over_ins=BigInteger.ZERO;
    private BigInteger car_bike_recommend=BigInteger.ZERO;

    private ArrayList<String> motorAssetnames;


    public static AutoInsuranceIndividual newInstance(Motor_Proper_Health_Model motor_proper_health_model) {
        Bundle args = new Bundle();
        args.putSerializable("motor_Proper_Health_Model",motor_proper_health_model);
        AutoInsuranceIndividual fragment = new AutoInsuranceIndividual();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View individualview=inflater.inflate(R.layout.fragment_individual_auto, container, false);
        errorTextview = (TextView) individualview.findViewById(R.id.errorTextview);
        auto_individual_chart = (HorizontalBarChart) individualview.findViewById(R.id.auto_individual_chart);
        parentView=(LinearLayout)individualview.findViewById(R.id.parentViewId);
        layout_Scroll=(ScrollView)individualview.findViewById(R.id.layout_Scroll);

        checkboxLayout=(LinearLayout)individualview.findViewById(R.id.check_group1Id);


        BARENTRYHORIZONTAL = new ArrayList<>();
        unitCount = new ArrayList<>();
        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        XAxis xl = auto_individual_chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);


        YAxis yl = auto_individual_chart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawLabels(false);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.1f);
        yl.setStartAtZero(false);
        YAxis yr = auto_individual_chart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(false);
        yr.setValueFormatter(new ChartValueFormatter());

        Legend l = auto_individual_chart.getLegend();
        l.setEnabled(false);
        l.setTextSize(8 * getResources().getDisplayMetrics().density);
        l.setFormSize(15f);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(15f);

        if(null!=getArguments())
        {
            if(getArguments().containsKey("motor_Proper_Health_Model"))
                motor_Proper_Health_Model = (Motor_Proper_Health_Model) getArguments().getSerializable("motor_Proper_Health_Model");
               getAssetname(motor_Proper_Health_Model);
        }
        return individualview;
    }

    private void getAssetname(Motor_Proper_Health_Model motor_proper_health_model) {
        if (motor_Proper_Health_Model.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

            if(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets()!=null){

            int motor_asset_length=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().size();
                motorAssetnames=new ArrayList<String>();
            for (int i=0;i<motor_asset_length;i++){
                if(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).getAsset_name()!=null){

                    motorAssetnames.add(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().
                            getAssets().get(i).getAsset_name());
                }

            }
                AddCheckBoxView(motorAssetnames);
        }
        }
    }


    private void AddCheckBoxView(ArrayList<String> xData) {
        try {
            checkboxLayout.removeAllViews();
            for (int i = 0; i < xData.size(); i++) {
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

                userNameCheckBox.add(new CheckBox(mContext));
                userNameCheckBox.get(i).setId(i);
                UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
                userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        int position = compoundButton.getId();
                        checkBoxOnClick(position, b);
                    }
                });
                userNameCheckBox.get(i).setText(xData.get(i));
                userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
                left_layout.addView(userNameCheckBox.get(i));
                i++;
                if (xData.size() == i) {
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

                userNameCheckBox.add(new CheckBox(mContext));
                userNameCheckBox.get(i).setId(i);
                UtileKit.setTextAppearance(mContext, android.R.style.TextAppearance_Small, userNameCheckBox.get(i));
                userNameCheckBox.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        int position = compoundButton.getId();
                        checkBoxOnClick(position, b);
                    }
                });
                userNameCheckBox.get(i).setText(xData.get(i));
                userNameCheckBox.get(i).setTextColor(ContextCompat.getColor(mContext, R.color.app_text_color_gray));
                right_layout.addView(userNameCheckBox.get(i));

                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                checkboxLayout.addView(parent_layout);
            }
            if (!userNameCheckBox.isEmpty())
                userNameCheckBox.get(0).setChecked(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    private void checkBoxOnClick(int position, boolean b) {

        if(b){
            for (int i=0;i<userNameCheckBox.size();i++){
                if(i!=position)
                    userNameCheckBox.get(i).setChecked(false);
            }
            if(position==0){
                addSingleUserChart(position,motor_Proper_Health_Model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(position).getAsset_name());
            }


        }
    }

    private void addSingleUserChart(int position,String assetname) {


        if (motor_Proper_Health_Model.getStatus_code().equals(UtileKit.SUCCESSCODE)) {

            try{
                if(motor_Proper_Health_Model.getData().getMotor_ins_plan().getVechicle_asset().getAssets()!=null) {

                    BARENTRYHORIZONTAL = new ArrayList<>();
                    xDatadetailsData= new ArrayList<>();
                    xDatadetailscolor = new ArrayList<>();


                    */
/*
                    need to work
                     *//*




                    if (car_bike_over_value.equals(0)) {
                        errorTextview.setVisibility(View.VISIBLE);
                        layout_Scroll.setVisibility(View.GONE);
                    } else {

                        */
/**
                         * 0th car vs bike bar
                         *//*

                        xBIKECAR = new float[2];
                        if (bike_value != null) {
                            xBIKECAR[0] = Float.parseFloat(String.valueOf(bike_value));
                            colour[0] = ColorTemplate.rgb("#53adfc");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#53adfc")));
                            xDatadetailsData.add("Bike Value : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(bike_value)))));
                        }
                        if (car_value != null) {
                            xBIKECAR[1] = Float.parseFloat(String.valueOf(car_value));
                            colour[1] = ColorTemplate.rgb("#008000");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#008000")));
                            xDatadetailsData.add("Car Value : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(car_value)))));
                        }

                        */
/**
                         * 1th insurance recommented bar
                         *//*

                        xINSURANCE=new float[3];
                        if (bike_insurance != null) {
                            xINSURANCE[0] = Float.parseFloat(String.valueOf(bike_insurance));
                            colour[2] = ColorTemplate.rgb("#FFC107");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FFC107")));
                            xDatadetailsData.add("Bike Insurance : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(bike_insurance)))));
                        }
                        if (car_insurance != null) {
                            xINSURANCE[1] = Float.parseFloat(String.valueOf(car_insurance));
                            colour[3] = ColorTemplate.rgb("#610B5E");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#610B5E")));
                            xDatadetailsData.add("Car Insurance : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(car_insurance)))));
                        }

                        if (car_bike_recommend != null) {
                            xINSURANCE[2] = Float.parseFloat(String.valueOf(car_bike_recommend));
                            colour[4] = ColorTemplate.rgb("#FF0000");
                            xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                            xDatadetailsData.add("Recommended : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(car_bike_recommend)))));
                        }

                    }




                    parentView.removeAllViews();

                }




                BARENTRYHORIZONTAL.add(new BarEntry(xBIKECAR, 0));
                BARENTRYHORIZONTAL.add(new BarEntry(xINSURANCE,1));

                BardatasetHorizontal = new BarDataSet(BARENTRYHORIZONTAL, "");



                BardatasetHorizontal.setColors(colour);
                BardatasetHorizontal.setValueFormatter(new ChartValueFormatter());
                BarData data;
                data = new BarData(getXAxisValues(), BardatasetHorizontal);
                auto_individual_chart.setData(data);
                auto_individual_chart.setDescription(" ");
                data.setDrawValues(false);
                Legend horizontalline = auto_individual_chart.getLegend();
                horizontalline.setEnabled(false);
                horizontalline.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                auto_individual_chart.setPinchZoom(false);
                auto_individual_chart.setDoubleTapToZoomEnabled(false);
                auto_individual_chart.setTouchEnabled(false);
                auto_individual_chart.invalidate();
                setDataForCheckbox(xDatadetailsData, xDatadetailscolor);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        else {

        }
    }


    private void setDataForCheckbox(ArrayList<String> xDataCheckbox, ArrayList<String> colour) {
        try {
            Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is " + xDataCheckbox.size() );

            for (int i = 0; i <  xDataCheckbox.size(); i++) {

                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
                parent_layout.setOrientation(LinearLayout.HORIZONTAL);
                parent_param_layout.setMargins(10, 0, 0, 10);
                parent_layout.setLayoutParams(parent_param_layout);

                LinearLayout.LayoutParams parms_left_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
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
                legend_layout.setBackgroundColor(Integer.parseInt(colour.get(i)));
                left_layout.addView(legend_layout);

                TextView txt_unit = new TextView(mContext);
                txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txt_unit.setLayoutParams(txtLeft_params);

                txt_unit.setText(xDataCheckbox.get(i));
                left_layout.addView(txt_unit);

                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Car and Bike");
        xAxis.add("Insurance");
        return xAxis;
    }



    @Override
    public void onClick(View v) {

    }
}
*/
