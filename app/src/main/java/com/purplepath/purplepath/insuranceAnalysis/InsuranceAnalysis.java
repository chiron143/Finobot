package com.purplepath.purplepath.insuranceAnalysis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.ScrollView;
import android.widget.TextView;

import com.finobot.finobot.MyApplication;
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
import com.google.firebase.analytics.FirebaseAnalytics;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.insuranceAnalysis.models.InsuranceChartModel;
import com.purplepath.purplepath.insuranceSummary.InsuranceSummaryview;
import com.purplepath.purplepath.insuranceSummary.InsuranceTableView;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.retrofitservice.ServiceGenerator;
import com.purplepath.purplepath.retrofitservice.WebServiceCalls;
import com.purplepath.purplepath.taxanalysis.TaxPlanSummary;

import java.math.BigInteger;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Suresh on 04/01/17.
 */

public class InsuranceAnalysis extends BaseFragment implements View.OnClickListener {
    private Context mContext;
    private HorizontalBarChart charts;
    private LinearLayout parentView;
    private ScrollView scrolllinearlayout;
    private  TextView errorTextview, textViewUnitApperances;
    private ArrayList<BarEntry> BARENTRYHORIZONTAL ;
    private ArrayList<Float> unitCount  ;
    private BarDataSet  BardatasetHorizontal ;
    private InsuranceChartModel insuranceChartModel;
    private Float current_Insurance, current_saving,min_Surrival_cover,min_requried_cover,max_recommended_cover, short_asstes_liq,long_asstes_liq;
    private BigInteger current_expenses, outstandingdept, future_obligation;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private Typeface tf;
    private OnActivityBackPressedListener mCallBackListener;
    private FloatingActionButton fab_id;
    int largestString = 0;
    int index = 0;
    int calculate = 1;
    ArrayList<String> colors = new ArrayList<String>();
    private RelativeLayout mainLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            setHasOptionsMenu(true);
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
        }catch(ClassCastException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {}
        try{
            MyApplication.mFirebaseAnalytics = FirebaseAnalytics.getInstance(getContext());
        }catch (Exception e){}
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View insuranceView =  inflater.inflate(R.layout.fragment_insurance_analysis, container, false);



        return insuranceView;
    }

    @Override
    public void onViewCreated(View insuranceView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(insuranceView, savedInstanceState);
        charts = insuranceView.findViewById(R.id.chart);
        parentView= insuranceView.findViewById(R.id.parentViewId);
        scrolllinearlayout = insuranceView.findViewById(R.id.layout_Scroll);
        errorTextview = insuranceView.findViewById(R.id.empty_chart_display);
        textViewUnitApperances = insuranceView.findViewById(R.id.textViewUnitApperances);
        mleftRelativeLayout = insuranceView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = insuranceView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = insuranceView.findViewById(R.id.relative_right_arrow);
        mainLayout= insuranceView.findViewById(R.id.mainLayout);
        fab_id = insuranceView.findViewById(R.id.fab_id);

        mContext=getContext();

        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        fab_id.setOnClickListener(this);
        BARENTRYHORIZONTAL = new ArrayList<>();
        unitCount = new ArrayList<>();
        mCallBackListener.setActionBarTitle("Life Insurance");
        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        XAxis xl = charts.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);
        //  xl.setValueFormatter(new  CustomFormatter());


        //chart background programatically fixing with tab
        ShapeDrawable sd = new ShapeDrawable();
        sd.setShape(new RectShape());
        sd.getPaint().setColor(Color.GRAY);
        sd.getPaint().setStrokeWidth(5f);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        mainLayout.setBackground(sd);




        YAxis yl = charts.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawLabels(false);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.1f);
        yl.setStartAtZero(false);
        YAxis yr = charts.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(false);
        yr.setValueFormatter(new ChartValueFormatter());

        Legend l = charts.getLegend();
        l.setEnabled(false);
        l.setTextSize(8 * getResources().getDisplayMetrics().density);
        l.setFormSize(15f);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(15f);


        callInsuranceAnalysisService();// Services call here ;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("0");
        xAxis.add("1");
        xAxis.add("2");
        xAxis.add("3");

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
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    InsuranceTableView fragment =
                            addFragmenttoStack(new InsuranceTableView());
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
//                    fragmentTransaction.commitAllowingStateLoss();
                    // mCallBackListener.onActivityBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;

            case R.id.menu_summary:

                try{
//                    android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                    InsuranceSummaryview fragment =
                            addFragmenttoStack(new InsuranceSummaryview());
//                    fragmentTransaction.replace(R.id.fragment_container, fragment);
//                    fragmentTransaction.addToBackStack(fragment.getClass().getName());
//                    fragmentTransaction.commitAllowingStateLoss();
                    //  mCallBackListener.onActivityBackPressed();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return true;


        }
        return super.onOptionsItemSelected(menuItem);
    }



    public  void callInsuranceAnalysisService(){
        UtileKit.showSpinnerDialog(mContext,false);
        WebServiceCalls webServiceObj;
        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
        Call<InsuranceChartModel> call = webServiceObj.callinsurance_plan_Service(UtileKit.getPersistedPurplePathPref("user_id"));
        call.enqueue(new Callback<InsuranceChartModel>() {
            @Override
            public void onResponse(Call<InsuranceChartModel> call, Response<InsuranceChartModel> response) {
                try {
                    Log.i("Insurance Analysis","Insurance Analysis user id"+UtileKit.getPersistedPurplePathPref("user_id"));
                    UtileKit.dismisssSpinnerDialog();
                    float[]  xBARENTRYCURRENTEXPENSCES=null;
                    float[]  xBARENTRYOUTSTANDING=null;
                    float[]  xBARENTRYFUTUREOBLIGATION=null;
                    int [] colour=new int[14];
                    int [] xDatacolour=new int[10];
                    String[] xData=null;
                    float[] xDatadetails=null;
                    ArrayList<String> xDatadetailscolor = new ArrayList<String>();
                    ArrayList<String> xDatadetailsData = new ArrayList<String>();

                    insuranceChartModel = response.body();
                    if (insuranceChartModel.getStatus_code().equalsIgnoreCase(UtileKit.SUCCESSCODE)) {
                        if (null != insuranceChartModel.getData().getIns_plan()) {
                            scrolllinearlayout.setVisibility(View.VISIBLE);
                            current_Insurance = Math.abs(floatConvertion(UtileKit.validateNegativeWithZero(insuranceChartModel.getData().getIns_plan().getCurr_ins())));
                            current_saving = Math.abs(floatConvertion(UtileKit.validateNegativeWithZero(insuranceChartModel.getData().getIns_plan().getTot_asset())));
                            min_Surrival_cover = Math.abs(floatConvertion(UtileKit.validateNegativeWithZero(insuranceChartModel.getData().getIns_plan().getMin_sur_cov_req())));
                            min_requried_cover = Math.abs(floatConvertion(UtileKit.validateNegativeWithZero(insuranceChartModel.getData().getIns_plan().getReq_cov_req())));
                            max_recommended_cover = Math.abs(floatConvertion(UtileKit.validateNegativeWithZero(insuranceChartModel.getData().getIns_plan().getRecom_cov_req())));
                            long_asstes_liq = Math.abs(floatConvertion(UtileKit.validateNegativeWithZero(insuranceChartModel.getData().getIns_plan().getTot_asset_long())));
                            short_asstes_liq = Math.abs(floatConvertion(UtileKit.validateNegativeWithZero(insuranceChartModel.getData().getIns_plan().getTot_asset_liq())));

                            current_expenses = BigInteger.valueOf(Long.parseLong(insuranceChartModel.getData().getIns_plan().getMin_sur_cov_need()));
                            outstandingdept = BigInteger.valueOf(Long.parseLong(insuranceChartModel.getData().getIns_plan().getTot_liab()));
                            future_obligation = BigInteger.valueOf(Long.parseLong(insuranceChartModel.getData().getIns_plan().getFut_req()));
                            if (insuranceChartModel.getData().getIns_plan().getCurr_ins().equalsIgnoreCase("0") &&
                                    insuranceChartModel.getData().getIns_plan().getCurr_contr().equalsIgnoreCase("0") &&
                                    insuranceChartModel.getData().getIns_plan().getMin_sur_cov_req().equalsIgnoreCase("0")
                                    && insuranceChartModel.getData().getIns_plan().getReq_cov_req().equalsIgnoreCase("0")
                                    && insuranceChartModel.getData().getIns_plan().getRecom_cov_req().equalsIgnoreCase("0")) {
                                errorTextview.setVisibility(View.VISIBLE);
                                errorTextview.setText(HomePageActivity.errorMessageInChart);
                                UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
                                scrolllinearlayout.setVisibility(View.GONE);
                            } else {

                                xDatadetails = new float[3];
                                xData = new String[10];

                                if (current_expenses != null) {
                                    xDatadetails[0] = Float.parseFloat(String.valueOf(current_expenses));
                                    colour[0] = ColorTemplate.rgb("#53adfc");
//                                        xData[0] = "Towards Living Expenses : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(current_expenses))));
//                                        xDatacolour[0] = ColorTemplate.rgb("#53adfc");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#53adfc")));
                                    xDatadetailsData.add("Towards Living Expenses : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(current_expenses)))));
                                }

                                if (outstandingdept != null) {
                                    xDatadetails[1] = Float.parseFloat(String.valueOf(outstandingdept));
                                    colour[1] = ColorTemplate.rgb("#53fcf3");
//                                        xData[1] = "Towards Paying Back Liabilities : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(outstandingdept))));
//                                        xDatacolour[1] = ColorTemplate.rgb("#53fcf3");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#53fcf3")));
                                    xDatadetailsData.add("Towards Paying Back Liabilities : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(outstandingdept)))));
                                }

                                if (future_obligation != null) {
                                    xDatadetails[2] = Float.parseFloat(String.valueOf(future_obligation));
                                    colour[2] = ColorTemplate.rgb("#7d53fc");
//                                        xData[2] = "Towards Fulfilling Future Goals : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(future_obligation))));
//                                        xDatacolour[2] = ColorTemplate.rgb("#7d53fc");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#7d53fc")));
                                    xDatadetailsData.add("Towards Fulfilling Future Goals : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.parseFloat(String.valueOf(future_obligation)))));
                                }
                            }

//----------------------------------------------------
                            xBARENTRYCURRENTEXPENSCES = new float[3];
                            float addAsstLiq_CurIns, compare_current_expenses;
                            addAsstLiq_CurIns = short_asstes_liq + current_Insurance;
                            compare_current_expenses = Float.parseFloat(String.valueOf(current_expenses));
                            if (compare_current_expenses > addAsstLiq_CurIns) {
                                if (short_asstes_liq != null) {
                                    xBARENTRYCURRENTEXPENSCES[0] = Float.parseFloat(String.valueOf(Math.round(short_asstes_liq / calculate)));
//                                    xData[3] = "Short Term Assets : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(short_asstes_liq))));
//                                    xDatacolour[3] = ColorTemplate.rgb("#8BC34A");
                                    colour[3] = ColorTemplate.rgb("#8BC34A");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#8BC34A")));
                                    xDatadetailsData.add("Short Term Assets : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(short_asstes_liq)))));

                                }
                                if (current_Insurance != null) {
                                    xBARENTRYCURRENTEXPENSCES[1] = Float.parseFloat(String.valueOf(Math.round(current_Insurance / calculate)));
//                                    xData[4] = "Current Insurance Cover : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(current_Insurance))));
//                                    xDatacolour[4] = ColorTemplate.rgb("#FFC107");
                                    colour[4] = ColorTemplate.rgb("#FFC107");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FFC107")));
                                    xDatadetailsData.add("Current Insurance Cover : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(current_Insurance)))));

                                }

                                if (min_Surrival_cover != null) {
                                    xBARENTRYCURRENTEXPENSCES[2] = Float.parseFloat(String.valueOf(Math.round(min_Surrival_cover / calculate)));
//                                    xData[5] = "Survival Cover required : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(min_Surrival_cover))));
//                                    xDatacolour[5] = ColorTemplate.rgb("#5386fc");
                                    colour[5] = ColorTemplate.rgb("#5386fc");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#5386fc")));
                                    xDatadetailsData.add("Survival Cover required : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(min_Surrival_cover)))));

                                }
                            } else {

                                if (short_asstes_liq != null) {
                                    xBARENTRYCURRENTEXPENSCES[0] = Float.parseFloat(String.valueOf(Math.round(short_asstes_liq / calculate)));
//                                        xData[3] = "Short Term Assets : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(short_asstes_liq))));
//                                        xDatacolour[3] = ColorTemplate.rgb("#8BC34A");
                                    colour[3] = ColorTemplate.rgb("#8BC34A");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#8BC34A")));
                                    xDatadetailsData.add("Short Term Assets : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(short_asstes_liq)))));

                                }
                                if (current_Insurance != null) {
                                    xBARENTRYCURRENTEXPENSCES[1] = Float.parseFloat(String.valueOf(Math.round(current_Insurance / calculate)));
//                                        xData[4] = "Current Insurance Cover : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(current_Insurance))));
//                                        xDatacolour[4] = ColorTemplate.rgb("#FFC107");
                                    colour[4] = ColorTemplate.rgb("#FFC107");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#FFC107")));
                                    xDatadetailsData.add("Current Insurance Cover : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(current_Insurance)))));

                                }
                            }
//-----------------------------------
                            xBARENTRYOUTSTANDING = new float[4];

                            float addAsstLiq_CurInsbarTwo, compare_current_expensesbarTwo;
                            addAsstLiq_CurInsbarTwo = short_asstes_liq + current_Insurance + min_Surrival_cover;
                            compare_current_expensesbarTwo = Float.parseFloat(String.valueOf(current_expenses)) + Float.parseFloat(String.valueOf(outstandingdept));
                            Log.d("", "compare_current_expensesbarTwo :" + compare_current_expensesbarTwo);
                            if (compare_current_expensesbarTwo > addAsstLiq_CurInsbarTwo) {
                                if (short_asstes_liq != null) {
                                    xBARENTRYOUTSTANDING[0] = Float.parseFloat(String.valueOf(Math.round(short_asstes_liq / calculate)));
                                    colour[6] = ColorTemplate.rgb("#8BC34A");

                                }
                                if (long_asstes_liq != null) {
                                    xBARENTRYOUTSTANDING[1] = Float.parseFloat(String.valueOf(Math.round(long_asstes_liq / calculate)));
//                                    xData[6] = "Long Term Assets : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(long_asstes_liq))));
//                                    xDatacolour[6] = ColorTemplate.rgb("#938a42");
                                    colour[7] = ColorTemplate.rgb("#938a42");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#938a42")));
                                    xDatadetailsData.add("Long Term Assets : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(long_asstes_liq)))));

                                }
                                if (current_Insurance != null) {
                                    xBARENTRYOUTSTANDING[2] = Float.parseFloat(String.valueOf(Math.round(current_Insurance / calculate)));
                                    colour[8] = ColorTemplate.rgb("#FFC107");
                                }

                                if (min_requried_cover != null) {
                                    xBARENTRYOUTSTANDING[3] = Float.parseFloat(String.valueOf(Math.round(min_requried_cover / calculate)));
//                                    xData[7] = "Safety Cover Required : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(min_requried_cover))));
//                                    xDatacolour[7] = ColorTemplate.rgb("#c18911");
                                    colour[9] = ColorTemplate.rgb("#c18911");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#c18911")));
                                    xDatadetailsData.add("Safety Cover Required : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(min_requried_cover)))));

                                }
                            } else {
                                if (short_asstes_liq != null) {
                                    xBARENTRYOUTSTANDING[0] = Float.parseFloat(String.valueOf(Math.round(short_asstes_liq / calculate)));
                                    colour[6] = ColorTemplate.rgb("#8BC34A");

                                }
                                if (long_asstes_liq != null) {
                                    xBARENTRYOUTSTANDING[1] = Float.parseFloat(String.valueOf(Math.round(long_asstes_liq / calculate)));
//                                    xData[5] = "Long Term Assets : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(long_asstes_liq))));
//                                    xDatacolour[5] = ColorTemplate.rgb("#938a42");
                                    colour[7] = ColorTemplate.rgb("#938a42");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#938a42")));
                                    xDatadetailsData.add("Long Term Assets : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(long_asstes_liq)))));

                                }
                                if (current_Insurance != null) {
                                    xBARENTRYOUTSTANDING[2] = Float.parseFloat(String.valueOf(Math.round(current_Insurance / calculate)));
                                    colour[8] = ColorTemplate.rgb("#FFC107");
                                }

                            }


//---------------------------------------

                            xBARENTRYFUTUREOBLIGATION = new float[4];
                            float addAsstLiq_CurInsbarThree, compare_current_expensesbarThree;
                            addAsstLiq_CurInsbarThree = short_asstes_liq + current_Insurance + min_Surrival_cover;
                            compare_current_expensesbarThree = Float.parseFloat(String.valueOf(current_expenses)) + Float.parseFloat(String.valueOf(future_obligation) + Float.parseFloat(String.valueOf(outstandingdept)));
                            Log.d("", "compare_current_expensesbarThree :" + compare_current_expensesbarThree);
                            if (compare_current_expensesbarThree > addAsstLiq_CurInsbarThree) {
                                if (short_asstes_liq != null) {
                                    xBARENTRYFUTUREOBLIGATION[0] = Float.parseFloat(String.valueOf(Math.round(short_asstes_liq / calculate)));
                                    colour[10] = ColorTemplate.rgb("#8BC34A");
                                }
                                if (long_asstes_liq != null) {
                                    xBARENTRYFUTUREOBLIGATION[1] = Float.parseFloat(String.valueOf(Math.round(long_asstes_liq / calculate)));
                                    colour[11] = ColorTemplate.rgb("#938a42");
                                }
                                if (current_Insurance != null) {
                                    xBARENTRYFUTUREOBLIGATION[2] = Float.parseFloat(String.valueOf(Math.round(current_Insurance / calculate)));
                                    colour[12] = ColorTemplate.rgb("#FFC107");
                                }
                                if (max_recommended_cover != null) {
                                    xBARENTRYFUTUREOBLIGATION[3] = Float.parseFloat(String.valueOf(Math.round(max_recommended_cover / calculate)));
//                                    xData[8] = "Prosperous Cover Required : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(max_recommended_cover))));
//                                    xDatacolour[8] = ColorTemplate.rgb("#da53fc");
                                    colour[13] = ColorTemplate.rgb("#da53fc");
                                    xDatadetailscolor.add(String.valueOf(ColorTemplate.rgb("#da53fc")));
                                    xDatadetailsData.add("Prosperous Cover Required : " + UtileKit.concatdinateRupeeSymbol(UtileKit.formatedNumber(Float.valueOf(Math.round(max_recommended_cover)))));

                                }
                            } else {
                                if (short_asstes_liq != null) {
                                    xBARENTRYFUTUREOBLIGATION[0] = Float.parseFloat(String.valueOf(Math.round(short_asstes_liq / calculate)));
                                    colour[10] = ColorTemplate.rgb("#8BC34A");
                                }
                                if (long_asstes_liq != null) {
                                    xBARENTRYFUTUREOBLIGATION[1] = Float.parseFloat(String.valueOf(Math.round(long_asstes_liq / calculate)));
                                    colour[11] = ColorTemplate.rgb("#938a42");
                                }
                                if (current_Insurance != null) {
                                    xBARENTRYFUTUREOBLIGATION[2] = Float.parseFloat(String.valueOf(Math.round(current_Insurance / calculate)));
                                    colour[12] = ColorTemplate.rgb("#FFC107");
                                }

                            }

//-------------------------------------------


                            Log.i("InsuranceAnalysis", " InsuranceAnalysis xdata is " + xDatadetails[0] + " " + xDatadetails[1] + " " + xDatadetails[2]);
                            BARENTRYHORIZONTAL.add(new BarEntry(xDatadetails, 0));
                            BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYCURRENTEXPENSCES, 1));
                            BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYOUTSTANDING, 2));
                            BARENTRYHORIZONTAL.add(new BarEntry(xBARENTRYFUTUREOBLIGATION, 3));
                            BardatasetHorizontal = new BarDataSet(BARENTRYHORIZONTAL, "");
                            try {
                                float count_word = xDatadetails[0] + xDatadetails[1] + xDatadetails[2];
                                Log.i("InsuranceAnalysis", " InsuranceAnalysis count_word is " + count_word);
//                                    if (current_Insurance != 0.0 && current_saving != 0.0 && min_Surrival_cover != 0.0
//                                            && min_requried_cover != 0.0 && max_recommended_cover != 0.0 && count_word != 0.0) {
//                                        Log.i("InsuranceAnalysis", " InsuranceAnalysis xBARENTRYCURRENTEXPENSCES is " + xBARENTRYCURRENTEXPENSCES.length);
//                                        Log.i("InsuranceAnalysis", " InsuranceAnalysis xBARENTRYOUTSTANDING is " + xBARENTRYOUTSTANDING.length);
//                                        Log.i("InsuranceAnalysis", " InsuranceAnalysis xBARENTRYFUTUREOBLIGATION is " + xBARENTRYFUTUREOBLIGATION.length);
//                                        Log.i("InsuranceAnalysis", " InsuranceAnalysis BARENTRYHORIZONTAL is " + BARENTRYHORIZONTAL.size());
//
//                                    } else {
//                                        scrolllinearlayout.setVisibility(View.GONE);
//                                        errorTextview.setVisibility(View.VISIBLE);
//                                        errorTextview.setText(HomePageActivity.errorMessageInChart);
//                                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart, mContext);
//                                    }

                                BardatasetHorizontal.setColors(colour);
                                BardatasetHorizontal.setValueFormatter(new ChartValueFormatter());
                                BarData data;
                                data = new BarData(getXAxisValues(), BardatasetHorizontal);
                                charts.setData(data);
                                charts.setDescription(" ");
                                data.setDrawValues(false);
                                Legend horizontalline = charts.getLegend();
                                horizontalline.setEnabled(false);
                                horizontalline.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
                                charts.setPinchZoom(false);
                                charts.setDoubleTapToZoomEnabled(false);
                                charts.setTouchEnabled(false);
                                charts.invalidate();
                                setDataForCheckbox(xDatadetailsData, xDatadetailscolor);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            scrolllinearlayout.setVisibility(View.GONE);
                            errorTextview.setVisibility(View.VISIBLE);
                            errorTextview.setText(HomePageActivity.errorMessageInChart);
                            UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                        }
                    }
                    else{
                        scrolllinearlayout.setVisibility(View.GONE);
                        errorTextview.setVisibility(View.VISIBLE);
                        errorTextview.setText(HomePageActivity.errorMessageInChart);
                        UtileKit.intitializeAlertDialog(HomePageActivity.errorMessageInChart,mContext);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            public  int countWords(String str){
                if(str == null || str.isEmpty())
                    return 0;

                int count = 0;
                for(int e = 0; e < str.length(); e++){
                    if(str.charAt(e) != ' '){
                        count++;
                        while(str.charAt(e) != ' ' && e < str.length()-1){
                            e++;
                        }
                    }
                }
                return count;
            }
            @Override
            public void onFailure(Call<InsuranceChartModel> call, Throwable t) {
                //Log.e("CallBack", " failure is " + t);
                UtileKit.alertRetrofitExceptionDialog( mContext,t);
                UtileKit.dismisssSpinnerDialog();
            }
        });
    }

    private void setDataForCheckbox(ArrayList<String> xDataCheckbox, ArrayList<String> colour) {
//        try {
//            for (int i = 0; i < xDataCheckbox.length; i++) {
//                Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is full value" + xDataCheckbox[i] );
//                if(xDataCheckbox[i]!= null) {
//                    colors.add(xDataCheckbox[i]);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
        try {
            Log.i("InsuranceAnalysis", " InsuranceAnalysis setDataForCheckbox is " + xDataCheckbox.size() );

            for (int i = 0; i <  xDataCheckbox.size(); i++) {

                LinearLayout.LayoutParams parent_param_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                LinearLayout parent_layout = new LinearLayout(mContext);
                parent_layout.setWeightSum(2);
               // parent_param_layout.weight = 1F;

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
//                parms_legen_layout.setMargins(20, 10, 20, 10);
               // txt_unit.setPadding(20,0,0,0);
//                txt_unit.setGravity(View.TEXT_ALIGNMENT_CENTER);






               /* if(xDataCheckbox[i].startsWith("Maximum Recommended Cover")){

                    if(Integer.parseInt(String.valueOf(xDataCheckbox.length))<0){

                        txt_unit.setTextColor(Color.parseColor("#006400"));
                        txt_unit.setText( xDataCheckbox[i]);
                    }
                    else if(Integer.parseInt(String.valueOf(xDataCheckbox.length))>0){
                        txt_unit.setTextColor(Color.parseColor("#FF0000"));
                        txt_unit.setText( xDataCheckbox[i]);

                    }
                }else {
                    txt_unit.setText( xDataCheckbox[i]);
                }*/


                txt_unit.setText(xDataCheckbox.get(i));
                left_layout.addView(txt_unit);
//                i++;
//                if ( (xDataCheckbox.length) == i) {
//                    parent_layout.addView(left_layout);
//                    parentView.addView(parent_layout);
//                    break;
//                }
//
//                LinearLayout.LayoutParams parms_right_layout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                parms_right_layout.weight = 1F;
//                LinearLayout right_layout = new LinearLayout(mContext);
//                right_layout.setOrientation(LinearLayout.HORIZONTAL);
//                right_layout.setGravity(Gravity.LEFT);
//                parent_param_layout.setMargins(10, 0, 0, 10);
//                right_layout.setLayoutParams(parms_right_layout);
//
//
//                LinearLayout.LayoutParams parms_rightlegend_layout = new LinearLayout.LayoutParams(45, 45);
//                parms_rightlegend_layout.setMargins(0, 0, 20, 0);
//                LinearLayout right_legend_layout = new LinearLayout(mContext);
//                right_legend_layout.setLayoutParams(parms_rightlegend_layout);
//                right_legend_layout.setOrientation(LinearLayout.HORIZONTAL);
//                right_legend_layout.setBackgroundColor(colour[i]);
//                right_layout.addView(right_legend_layout);
//
//                TextView right_txt_unit = new TextView(mContext);
//                right_txt_unit.setText( xDataCheckbox[i]);
//                right_layout.addView(right_txt_unit);
//
//                parent_layout.addView(right_layout);
                parent_layout.addView(left_layout);
                parentView.addView(parent_layout);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private float floatConvertion(String val) {
        float convVal = 0;
        try {
            convVal = Float.parseFloat(val);
        } catch (NumberFormatException e) {

        }
        return convVal;
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
//                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // ExpensesDetailsFragment fragment = new ExpensesDetailsFragment();
                //ExpensesSelectionDetailsFragment fragment = new ExpensesSelectionDetailsFragment();
                //TaxCashFlowChart fragment = new TaxCashFlowChart();
//                TaxPlanSummary fragment=
                        addFragmenttoStack(new TaxPlanSummary());
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();

            }
            break;

        }
    }

//    private class CustomFormatter implements com.github.mikephil.charting.formatter.ValueFormatter, YAxisValueFormatter {
//
//        private DecimalFormat mFormat;
//
//        public CustomFormatter() {
//            mFormat = new DecimalFormat("#,##,###");
//        }
//
//        @Override
//        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//            return mFormat.format(Math.abs(value)) ;
//        }
//
//        @Override
//        public String getFormattedValue(float value, YAxis yAxis) {
//            return mFormat.format(Math.abs(value)) ;
//        }
//    }
}
