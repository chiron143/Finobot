package com.purplepath.purplepath.autoinsurance.ChartView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.finobot.finobot.R;
import com.finobot.finobot.activity.HomePageActivity;
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
import com.purplepath.purplepath.autoinsurance.AutoInsuranceDetails;
import com.purplepath.purplepath.autoinsurance.AutoinsuranceSummary;
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

/**
 * Created by pravinr on 2/20/18.
 */

public class AutoInsuranceAllChart extends BaseFragment implements View.OnClickListener {


    Context mContext;

    TextView errorTextview;

    private LinearLayout parentView;

    private HorizontalBarChart auto_individual_chart;

    private Motor_Proper_Health_Model motor_Proper_Health_Model;

    private ArrayList<BarEntry> BARENTRYHORIZONTAL ;

    private ArrayList<Float> unitCount ;

    private Typeface tf;

    private BarDataSet BardatasetHorizontal ;

    private ScrollView layout_Scroll;

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

    private BigInteger car_recommend=BigInteger.ZERO;

    private  CheckBox mcheckBox_car,mcheckbox_bike,mcheckbox_total;

    OnActivityBackPressedListener backPressedListener;

    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;


    public static AutoInsuranceAllChart newInstance(Motor_Proper_Health_Model motor_proper_health_model) {
        Bundle args = new Bundle();
        args.putSerializable("motor_Proper_Health_Model",motor_proper_health_model);
        AutoInsuranceAllChart fragment = new AutoInsuranceAllChart();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getContext();

        backPressedListener= (OnActivityBackPressedListener) getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View individualview=inflater.inflate(R.layout.fragment_autoinsurance_all_charts, container, false);
        setHasOptionsMenu(true);
        backPressedListener.setActionBarTitle("Auto Insurance");
        mleftRelativeLayout = individualview.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = individualview.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = individualview.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        errorTextview = individualview.findViewById(R.id.empty_chart_display);
        layout_Scroll= individualview.findViewById(R.id.layout_Scroll);


        auto_individual_chart = individualview.findViewById(R.id.auto_individual_chart);
        parentView= individualview.findViewById(R.id.parentViewId);


        mcheckBox_car= individualview.findViewById(R.id.checkBox_car);
        mcheckbox_bike= individualview.findViewById(R.id.checkbox_bike);
        mcheckbox_total= individualview.findViewById(R.id.checkbox_total);
        mcheckBox_car.setOnClickListener(this);
        mcheckbox_bike.setOnClickListener(this);
        mcheckbox_total.setOnClickListener(this);



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
            try {
            if(getArguments().containsKey("motor_Proper_Health_Model")) {
                motor_Proper_Health_Model = (Motor_Proper_Health_Model) getArguments().getSerializable("motor_Proper_Health_Model");
            }else {
                errorTextview.setVisibility(View.VISIBLE);
            }
            }catch (Exception e){
                e.printStackTrace();
            }

        }else {
            callGetMotorService();
        }


        mcheckBox_car.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    loadCarValue(motor_Proper_Health_Model);
                    mcheckbox_bike.setChecked(false);
                    mcheckbox_total.setChecked(false);
                }}
        });

        mcheckbox_bike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    loadBikeValue(motor_Proper_Health_Model);
                    mcheckBox_car.setChecked(false);
                    mcheckbox_total.setChecked(false);
                }}
        });
        mcheckbox_total.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                    loadCarBikeTotalValue(motor_Proper_Health_Model);
                    mcheckBox_car.setChecked(false);
                    mcheckbox_bike.setChecked(false);
                }}
        });

        mcheckBox_car.setChecked(true);
        mcheckbox_bike.setChecked(false);
        mcheckbox_total.setChecked(false);

        return individualview;
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


                    mcheckBox_car.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){

                                loadCarValue(motor_Proper_Health_Model);
                                mcheckbox_bike.setChecked(false);
                                mcheckbox_total.setChecked(false);
                            }}
                    });

                    mcheckbox_bike.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){

                                loadBikeValue(motor_Proper_Health_Model);
                                mcheckBox_car.setChecked(false);
                                mcheckbox_total.setChecked(false);
                            }}
                    });
                    mcheckbox_total.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){

                                loadCarBikeTotalValue(motor_Proper_Health_Model);
                                mcheckBox_car.setChecked(false);
                                mcheckbox_bike.setChecked(false);
                            }}
                    });


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

    private void loadCarValue(Motor_Proper_Health_Model motor_proper_health_model) {
        if (motor_Proper_Health_Model.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
            BARENTRYHORIZONTAL = new ArrayList<>();
            xDatadetailsData = new ArrayList<>();
            xDatadetailscolor = new ArrayList<>();

            car_value = BigInteger.ZERO;

            car_bike_recommend = BigInteger.ZERO;

            car_recommend = BigInteger.ZERO;

            int res;
            if (motor_proper_health_model.getData().getMotor_ins_plan() != null) {

                // car  values
                int car_bike_val_length = motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().size();
                for (int i = 0; i < car_bike_val_length; i++) {
                    if (motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).
                            getLev2_name().equalsIgnoreCase("Car")) {
                        car_value = car_value.add(new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().
                                get(i).getCurrent_value()));

                    }

                    //car  insurance
                    int car_bike_ins_length = motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().getIns_by_motor_type().size();

                    car_insurance = BigInteger.ZERO;
                    for (int j = 0; j < car_bike_ins_length; j++) {

                        if (motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).
                                getCurrent_value().length() != 0 && motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().
                                getIns_by_motor_type().get(j).getMotor_type().equalsIgnoreCase("Car")) {

                            car_insurance = car_insurance.add(new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().
                                    getIns_by_motor_type().get(j).getCover_availed()));
                        }
                    }
                }
                //car values

                car_bike_recommend = car_value.subtract(car_insurance);

                if (car_bike_recommend.signum() == 1) {
                    car_recommend = car_bike_recommend;
                } else {
                    car_recommend = BigInteger.ZERO;
                }


            }


            if (motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getOverall_value().equalsIgnoreCase("0")) {
                errorTextview.setVisibility(View.VISIBLE);
                layout_Scroll.setVisibility(View.GONE);
            } else {

                /**
                 * 0th car bar
                 */
                xBIKECAR = new float[1];

                if (car_value != null) {
                    xBIKECAR[0] = Float.parseFloat(String.valueOf(car_value));
                    colour[0] = ColorTemplate.rgb("#008000");
                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#008000")));
                    xDatadetailsData.add("Car Value : " + "₹ " + UtileKit.formatedNumbers(car_value));
                }

                /**
                 * 1th insurance recommented bar
                 */
                xINSURANCE = new float[2];

                if (car_insurance != null) {
                    xINSURANCE[0] = Float.parseFloat(String.valueOf(car_insurance));
                    colour[1] = ColorTemplate.rgb("#610B5E");
                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#610B5E")));
                    xDatadetailsData.add("Car Insurance : " + "₹ " + UtileKit.formatedNumbers(car_insurance));
                }

                if (car_recommend != null) {
                    xINSURANCE[1] = Float.parseFloat(String.valueOf(car_recommend));
                    colour[2] = ColorTemplate.rgb("#FF0000");
                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                    xDatadetailsData.add("Recommended : " + "₹ " + UtileKit.formatedNumbers(car_recommend));
                }
                parentView.removeAllViews();
            }
        }
            BARENTRYHORIZONTAL.add(new BarEntry(xBIKECAR, 0));
            BARENTRYHORIZONTAL.add(new BarEntry(xINSURANCE, 1));
            BardatasetHorizontal = new BarDataSet(BARENTRYHORIZONTAL, "");
            BardatasetHorizontal.setColors(colour);
            BardatasetHorizontal.setValueFormatter(new ChartValueFormatter());
            BarData data;
            data = new BarData(getXAxisValuesCar(), BardatasetHorizontal);
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


    }
    private void loadBikeValue(Motor_Proper_Health_Model motor_proper_health_model) {
        if (motor_Proper_Health_Model.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
            BARENTRYHORIZONTAL = new ArrayList<>();
            xDatadetailsData= new ArrayList<>();
            xDatadetailscolor = new ArrayList<>();

            bike_value=BigInteger.ZERO;

            car_bike_recommend=BigInteger.ZERO;

            car_recommend=BigInteger.ZERO;

            int res;
            if(motor_proper_health_model.getData().getMotor_ins_plan()!=null){

                // Bike  values
                int car_bike_val_length=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().size();
                for(int i=0;i<car_bike_val_length;i++){
                    if (motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).
                            getLev2_name().equalsIgnoreCase("Bike")){
                        bike_value=bike_value.add(new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().
                                get(i).getCurrent_value()));

                    }

                    //Bike  insurance
                    int car_bike_ins_length=motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().getIns_by_motor_type().size();
                    bike_insurance=BigInteger.ZERO;
                    for(int j=0;j<car_bike_ins_length;j++){

                        if(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getAssets().get(i).
                                getCurrent_value().length()!=0&&motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().getIns_by_motor_type()
                               .get(j).getMotor_type().equalsIgnoreCase("Bike")) {

                            bike_insurance = bike_insurance.add(new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().
                                    getIns_by_motor_type().get(j).getCover_availed()));
                        }
                    }
                }
                //Bike values

                    car_bike_recommend=bike_value.subtract(bike_insurance);


                if(car_bike_recommend.signum() == 1){
                    car_recommend=car_bike_recommend;
                }else {
                    car_recommend=BigInteger.ZERO;
                }

            }


            if (motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getOverall_value().equalsIgnoreCase("0")) {
                errorTextview.setVisibility(View.VISIBLE);
                layout_Scroll.setVisibility(View.GONE);
            } else {
            /**
             * 0th car bar
             */
            xBIKECAR = new float[1];

            if (car_value != null) {
                xBIKECAR[0] = Float.parseFloat(String.valueOf(bike_value));
                colour[0] = ColorTemplate.rgb("#53adfc");
                xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#53adfc")));
                xDatadetailsData.add("Bike Value : " + "₹ " + UtileKit.formatedNumbers(bike_value));
            }

            /**
             * 1th insurance recommented bar
             */
            xINSURANCE=new float[2];

            if (car_insurance != null) {
                xINSURANCE[0] = Float.parseFloat(String.valueOf(bike_insurance));
                colour[1] = ColorTemplate.rgb("#610B5E");
                xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#610B5E")));
                xDatadetailsData.add("Bike Insurance : " + "₹ " +UtileKit.formatedNumbers(bike_insurance));
            }

            if (car_recommend != null) {
                xINSURANCE[1] = Float.parseFloat(String.valueOf(car_recommend));
                colour[2] = ColorTemplate.rgb("#FF0000");
                xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                xDatadetailsData.add("Recommended : " + "₹ " + UtileKit.formatedNumbers(car_recommend));
            }
            parentView.removeAllViews();
        }
        }
        BARENTRYHORIZONTAL.add(new BarEntry(xBIKECAR, 0));
        BARENTRYHORIZONTAL.add(new BarEntry(xINSURANCE,1));
        BardatasetHorizontal = new BarDataSet(BARENTRYHORIZONTAL, "");
        BardatasetHorizontal.setColors(colour);
        BardatasetHorizontal.setValueFormatter(new ChartValueFormatter());
        BarData data;
        data = new BarData(getXAxisValuesBike(), BardatasetHorizontal);
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
    }

    private void loadCarBikeTotalValue(Motor_Proper_Health_Model motor_proper_health_model) {
        if (motor_Proper_Health_Model.getStatus_code().equals(UtileKit.SUCCESSCODE)) {
            BARENTRYHORIZONTAL = new ArrayList<>();
            xDatadetailsData = new ArrayList<>();
            xDatadetailscolor = new ArrayList<>();
            if(motor_proper_health_model.getData().getMotor_ins_plan()!=null){

                bike_value=BigInteger.ZERO;
                car_value=BigInteger.ZERO;
                car_bike_recommend=BigInteger.ZERO;

                bike_insurance=BigInteger.ZERO;
                car_insurance=BigInteger.ZERO;
                car_bike_over_value=BigInteger.ZERO;
                car_bike_over_ins=BigInteger.ZERO;

                car_recommend=BigInteger.ZERO;

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
                //total car_bike_value minus total car_bike_ins ==recommented
                car_bike_over_value=new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getOverall_value());
                car_bike_over_ins=new BigInteger(motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_insurance().getOverall_value());

                car_bike_recommend=car_bike_over_value.subtract(car_bike_over_ins);

                //total car_bike_value minus total car_bike_ins ==recommented


                if(car_bike_recommend.signum() == 1){
                    car_recommend=car_bike_recommend;
                }else {
                    car_recommend=BigInteger.ZERO;
                }


            }


            if (motor_proper_health_model.getData().getMotor_ins_plan().getVechicle_asset().getOverall_value().equalsIgnoreCase("0")) {
                errorTextview.setVisibility(View.VISIBLE);
                layout_Scroll.setVisibility(View.GONE);
            } else {
            /**
             * 0th car vs bike bar
             */
            xBIKECAR = new float[2];
            if (bike_value != null) {
                xBIKECAR[0] = Float.parseFloat(String.valueOf(bike_value));
                colour[0] = ColorTemplate.rgb("#53adfc");
                xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#53adfc")));
                xDatadetailsData.add("Bike Value : " + "₹ " + UtileKit.formatedNumbers(bike_value));
            }
            if (car_value != null) {
                xBIKECAR[1] = Float.parseFloat(String.valueOf(car_value));
                colour[1] = ColorTemplate.rgb("#008000");
                xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#008000")));
                xDatadetailsData.add("Car Value : " + "₹ " + UtileKit.formatedNumbers(car_value));
            }

            /**
             * 1th insurance recommented bar
             */
            xINSURANCE=new float[3];
            if (bike_insurance != null) {
                xINSURANCE[0] = Float.parseFloat(String.valueOf(bike_insurance));
                colour[2] = ColorTemplate.rgb("#FFC107");
                xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FFC107")));
                xDatadetailsData.add("Bike Insurance : " + "₹ " + UtileKit.formatedNumbers(bike_insurance));
            }
            if (car_insurance != null) {
                xINSURANCE[1] = Float.parseFloat(String.valueOf(car_insurance));
                colour[3] = ColorTemplate.rgb("#610B5E");
                xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#610B5E")));
                xDatadetailsData.add("Car Insurance : " + "₹ " + UtileKit.formatedNumbers(car_insurance));
            }

            if (car_recommend != null) {
                xINSURANCE[2] = Float.parseFloat(String.valueOf(car_recommend));
                colour[4] = ColorTemplate.rgb("#FF0000");
                xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                xDatadetailsData.add("Recommended : " + "₹ " + UtileKit.formatedNumbers(car_recommend));
            }
            parentView.removeAllViews();
        }
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

    }

    private ArrayList<String> getXAxisValuesCar() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Car");
        xAxis.add("Insurance");
        return xAxis;
    }

    private ArrayList<String> getXAxisValuesBike() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Bike");
        xAxis.add("Insurance");
        return xAxis;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("Car and Bike");
        xAxis.add("Insurance");
        return xAxis;
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






    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relative_left_arrow:
                backPressedListener.onActivityBackPressed();
                break;
            case R.id.relative_center_home:
                Intent i=new Intent(getActivity(), HomePageActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                break;

        }

    }
}
