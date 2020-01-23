/*
package com.purplepath.purplepath.autoinsurance;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
import com.finobot.finobot.R;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.propertyinsurance.model.Motor_Proper_Health_Model;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;

import java.math.BigInteger;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


*/
/**
 * Created by pravinr on 11/23/17.
 *//*


public class AutoInsuranceTotal extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private Context mContext;
    private TextView errorTextview;
    private HorizontalBarChart auto_total_chart;
    private LinearLayout parentView;
    private ScrollView layout_Scroll;

    private Motor_Proper_Health_Model motor_proper_Health_model;

    private ArrayList<BarEntry> BARENTRYHORIZONTAL ;
    private Typeface tf;
    private BarDataSet BardatasetHorizontal;
    private ArrayList<Float> unitCount ;

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


    public static AutoInsuranceTotal newInstance(Motor_Proper_Health_Model motor_proper_health_model) {
        Bundle args = new Bundle();
        args.putSerializable("motor_Proper_Health_Model",motor_proper_health_model);
        AutoInsuranceTotal fragment = new AutoInsuranceTotal();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backPressedListener= (OnActivityBackPressedListener) getContext();
        mContext = getContext();
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_autoinurance_total_chart, container, false);
        backPressedListener.setActionBarTitle("Auto Insurance");


        errorTextview = (TextView) view.findViewById(R.id.empty_text);
        auto_total_chart = (HorizontalBarChart) view.findViewById(R.id.auto_total_chart);
        parentView=(LinearLayout)view.findViewById(R.id.parentViewId);
        layout_Scroll=(ScrollView)view.findViewById(R.id.scrollview);

        BARENTRYHORIZONTAL = new ArrayList<>();
        unitCount = new ArrayList<>();
        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        XAxis xl = auto_total_chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);


        YAxis yl = auto_total_chart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawLabels(false);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.1f);
        yl.setStartAtZero(false);
        YAxis yr = auto_total_chart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(false);
        yr.setValueFormatter(new ChartValueFormatter());

        Legend l = auto_total_chart.getLegend();
        l.setEnabled(false);
        l.setTextSize(8 * getResources().getDisplayMetrics().density);
        l.setFormSize(15f);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(15f);

        callGetMotorService();

        return view;
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
                motor_proper_Health_model = response.body();
                getvaluefromservices(motor_proper_Health_model);
            }
            @Override
            public void onFailure(Call<Motor_Proper_Health_Model> call, Throwable t) {
                UtileKit.alertRetrofitExceptionDialog( mContext, t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }


    private void getvaluefromservices(Motor_Proper_Health_Model motor_proper_health_model) {
        if (motor_proper_health_model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)){
            try {
                if(motor_proper_health_model.getData().getMotor_ins_plan()!=null){

                    // car and bike values
                    int car_bike_val_length=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().size();
                    for(int i=0;i<car_bike_val_length;i++){
                        if (motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).
                                getLev2_name().equalsIgnoreCase("Bike")){
                            bike_value=bike_value.add(new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().
                                    get(i).getCurrent_value()));

                        }else {
                            car_value=car_value.add(new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().
                                    get(i).getCurrent_value()));
                        }





                        //car and bike insurance
                        int car_bike_ins_length=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().getIns_by_motor_type().size();
                        for(int j=0;j<car_bike_ins_length;j++){

                            if(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).
                                    getCurrent_value().length()!=0&&motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().
                                    getAssets().get(i).getLev2_name().equalsIgnoreCase("Bike")) {

                                bike_insurance = bike_insurance.add(new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().
                                        getIns_by_motor_type().get(j).getCover_availed()));
                            }
                            else if(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).
                                    getCurrent_value().length()!=0&&motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().
                                    getAssets().get(i).getLev2_name().equalsIgnoreCase("Car")){
                                car_insurance=car_insurance.add(new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().
                                        getIns_by_motor_type().get(j).getCover_availed()));
                            }
                        }



                        //car and bike insurance







                    }
                    //car and bike values



                    //total car_bike_value minus total car_bike_ins ==recommented
                    car_bike_over_value=new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getOverall_value());
                    car_bike_over_ins=new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().getOverall_value());

                    car_bike_recommend=car_bike_over_value.subtract(car_bike_over_ins);

                    //total car_bike_value minus total car_bike_ins ==recommented


                }
                else {
                    errorTextview.setVisibility(View.VISIBLE);
                }


                if(car_bike_over_value.equals("0")){
                    errorTextview.setVisibility(View.VISIBLE);
                    layout_Scroll.setVisibility(View.GONE);
                }else {

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



                BARENTRYHORIZONTAL.add(new BarEntry(xBIKECAR, 0));
                BARENTRYHORIZONTAL.add(new BarEntry(xINSURANCE, 1));
                BardatasetHorizontal = new BarDataSet(BARENTRYHORIZONTAL, "");

                BardatasetHorizontal.setColors(colour);
                BardatasetHorizontal.setValueFormatter(new ChartValueFormatter());
                BarData data;
                data = new BarData(getXAxisValues(), BardatasetHorizontal);
                auto_total_chart.setData(data);
                auto_total_chart.setDescription(" ");
                data.setDrawValues(false);
                Legend horizontalline = auto_total_chart.getLegend();
                horizontalline.setEnabled(false);
                horizontalline.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                auto_total_chart.setPinchZoom(false);
                auto_total_chart.setDoubleTapToZoomEnabled(false);
                auto_total_chart.setTouchEnabled(false);
                auto_total_chart.invalidate();
                setDataForCheckbox(xDatadetailsData, xDatadetailscolor);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void setDataForCheckbox(ArrayList<String> xDataCheckbox, ArrayList<String> colour) {
        try {

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
        xAxis.add("Bike vs Car");
        xAxis.add("Insurance");
        return xAxis;
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

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                startSettingHomeActivity();
                break;

        }

    }

}
*/
