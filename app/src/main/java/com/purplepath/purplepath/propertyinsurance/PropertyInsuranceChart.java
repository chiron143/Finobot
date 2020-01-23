package com.purplepath.purplepath.propertyinsurance;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
 * Created by pravinr on 11/6/17.
 */

public class PropertyInsuranceChart extends BaseFragment implements View.OnClickListener {

    OnActivityBackPressedListener backPressedListener;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;

    private Context mContext;
    private TextView errorTextview;
    private HorizontalBarChart property_chart;
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
    float[]  xHOUSECONTENT=null;
    int [] colour=new int[4];

    private BigInteger house_value = BigInteger.ZERO;
    private BigInteger house_content_value=BigInteger.ZERO;
    private BigInteger house_insurance_coverage_value=BigInteger.ZERO;
    private BigInteger add_house_value_content_value=BigInteger.ZERO;
    private BigInteger sub_house_content_insurance=BigInteger.ZERO;
    private String own_or_rent;


    @Override
    public void onAttach(Context context) {
        backPressedListener= (OnActivityBackPressedListener) context;
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view=inflater.inflate(R.layout.fragment_propertyinurance_chart, container, false);
        backPressedListener.setActionBarTitle("Property Insurance");
        mleftRelativeLayout = view.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = view.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = view.findViewById(R.id.relative_right_arrow);
        mRightRelativeLayout.setVisibility(View.GONE);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);

        errorTextview = view.findViewById(R.id.errorTextview);
        property_chart = view.findViewById(R.id.property_chart);
        parentView= view.findViewById(R.id.parentViewId);
        layout_Scroll= view.findViewById(R.id.scrollview);

        BARENTRYHORIZONTAL = new ArrayList<>();
        unitCount = new ArrayList<>();
        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        XAxis xl = property_chart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);


        YAxis yl = property_chart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawLabels(false);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.1f);
        yl.setStartAtZero(false);
        YAxis yr = property_chart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(false);
        yr.setValueFormatter(new ChartValueFormatter());

        Legend l = property_chart.getLegend();
        l.setEnabled(false);
        l.setTextSize(8 * getResources().getDisplayMetrics().density);
        l.setFormSize(15f);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(15f);


        callGetPropertyService();

        return view;
    }


    public void callGetPropertyService() {
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

        if(motor_proper_Health_model.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
            try {
                if (null != motor_proper_Health_model.getData().getProp_ins_plan()) {

                     own_or_rent=motor_proper_health_model.getData().getProp_ins_plan().getIs_own_house();

                    if(own_or_rent.equalsIgnoreCase("true")){

                        house_value=new BigInteger(motor_proper_health_model.getData().getProp_ins_plan().getHouse_value().getOverall_value());
                        house_content_value=new BigInteger(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().getOverall_value());
                        house_insurance_coverage_value=new BigInteger(motor_proper_health_model.getData().getProp_ins_plan().getInsurance().getOverall_value());

                        add_house_value_content_value=house_value.add(house_content_value);

                        sub_house_content_insurance=add_house_value_content_value.subtract(house_insurance_coverage_value);

                    }else {

                        house_content_value=new BigInteger(motor_proper_health_model.getData().getProp_ins_plan().getHouse_hold_content().getOverall_value());
                        house_insurance_coverage_value=new BigInteger(motor_proper_health_model.getData().getProp_ins_plan().getInsurance().getOverall_value());

                        add_house_value_content_value=house_value;

                        sub_house_content_insurance=add_house_value_content_value.subtract(house_insurance_coverage_value);

                    }

                }


                if (motor_proper_health_model.getData().getProp_ins_plan().getHouse_value().equals("0")) {
                    errorTextview.setVisibility(View.VISIBLE);
                    layout_Scroll.setVisibility(View.GONE);
                } else {
                    /**
                     * 0th house and rent bar
                     */
                    xHOUSECONTENT = new float[2];
                    if (house_value != null) {
                        xHOUSECONTENT[0] = Float.parseFloat(String.valueOf(house_value));
                        colour[0] = ColorTemplate.rgb("#53adfc");
                        xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#53adfc")));
                        xDatadetailsData.add("House Value : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(house_value)))));
                    }
                    if (house_content_value != null) {
                        xHOUSECONTENT[1] = Float.parseFloat(String.valueOf(house_content_value));
                        colour[1] = ColorTemplate.rgb("#008000");
                        xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#008000")));
                        xDatadetailsData.add("Content Value : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(house_content_value)))));
                    }
                    /**
                     * 1th house and rent bar
                     */
                    xINSURANCE=new float[2];
                    if (house_insurance_coverage_value != null) {
                        xINSURANCE[0] = Float.parseFloat(String.valueOf(house_insurance_coverage_value));
                        colour[2] = ColorTemplate.rgb("#FFC107");
                        xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FFC107")));
                        xDatadetailsData.add("Insurance : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(house_insurance_coverage_value)))));
                    }
                    if (sub_house_content_insurance != null) {
                        xINSURANCE[1] = Float.parseFloat(String.valueOf(sub_house_content_insurance));
                        colour[3] = ColorTemplate.rgb("#FF0000");
                        xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FF0000")));
                        xDatadetailsData.add("Recommended : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(sub_house_content_insurance)))));
                    }


                }


                BARENTRYHORIZONTAL.add(new BarEntry(xHOUSECONTENT, 0));
                BARENTRYHORIZONTAL.add(new BarEntry(xINSURANCE, 1));
                BardatasetHorizontal = new BarDataSet(BARENTRYHORIZONTAL, "");

                BardatasetHorizontal.setColors(colour);
                BardatasetHorizontal.setValueFormatter(new ChartValueFormatter());
                BarData data;
                data = new BarData(getXAxisValues(), BardatasetHorizontal);
                property_chart.setData(data);
                property_chart.setDescription(" ");
                data.setDrawValues(false);
                Legend horizontalline = property_chart.getLegend();
                horizontalline.setEnabled(false);
                horizontalline.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                property_chart.setPinchZoom(false);
                property_chart.setDoubleTapToZoomEnabled(false);
                property_chart.setTouchEnabled(false);
                property_chart.invalidate();
                setDataForCheckbox(xDatadetailsData, xDatadetailscolor);
            } catch (Exception e) {
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
        if(own_or_rent.equalsIgnoreCase("true")){
        xAxis.add("House vs Rent");
        xAxis.add("Insurance");
        }
        else {
            xAxis.add("Rent");
            xAxis.add("Insurance");
        }
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
                    addFragmenttoStack(new PropertyInsuranceDetails());
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:

                try{
                    addFragmenttoStack(new ProperyInsuranceSummary());
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
