package com.purplepath.purplepath.cashflowmanagmentchart;

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
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.purplepath.purplepath.apputiles.ChartValueFormatter;
import com.purplepath.purplepath.apputiles.UtileKit;
import com.purplepath.purplepath.assetsanalysis.fragment.AssetsAnalysisFragment;
import com.purplepath.purplepath.fragments.BaseFragment;
import com.purplepath.purplepath.myinterface.OnActivityBackPressedListener;
import com.purplepath.purplepath.taxanalysis.TaxPlanDetailViewPager;
import com.purplepath.purplepath.taxanalysis.TaxPlanSummary;
import com.purplepath.purplepath.taxanalysis.modelsTaxCashFlow.User_tax;
import com.purplepath.purplepath.taxanalysis.modes.GetTaxPlanModels;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Suresh on 07/11/17.
 */

public class TaxAnalysisDrillDown  extends BaseFragment implements  View.OnClickListener {
    private Context mContext;
    private GetTaxPlanModels taxAnalysisChartModel;
    String eightyC ="80C",eightyCCD= "80CCD", eightyCCG = "80CCG",eightyGG = "80GG",eightyD = "80D",tra = "TRA";
    String total_limit_eightyC="0",total_limit_eightyCCD="0",total_limit_eightyCCG="0",
            total_limit_eightyGG="0",total_limit_eightyD="0",total_limit_eightyTRA="0";
    private CombinedChart barChart ;
    ArrayList<BarEntry> BARENTRY ;
    String eighty_C_Cash_flow ="0", eighty_GG_Cash_flow ="0",tra_cash_flow_chart ="0";
    private Typeface tf;
    private OnActivityBackPressedListener mCallBackListener;
    private User_tax value_postion;
    private LinearLayout parentView;
    private RelativeLayout mleftRelativeLayout, mcenterRelativeLayout, mRightRelativeLayout;
    private RelativeLayout mainLayout;
    private FloatingActionButton fab_id;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        try {
            mCallBackListener = (OnActivityBackPressedListener) (mContext);
            setHasOptionsMenu(true);

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
        View taxAnalysisView =  inflater.inflate(R.layout.frag_taxanalysis, container, false);
        barChart = taxAnalysisView.findViewById(R.id.chart);
        parentView= taxAnalysisView.findViewById(R.id.parentViewId);

        mainLayout= taxAnalysisView.findViewById(R.id.mainLayout);
        fab_id = taxAnalysisView.findViewById(R.id.fab_id);
        mleftRelativeLayout = taxAnalysisView.findViewById(R.id.relative_left_arrow);
        mcenterRelativeLayout = taxAnalysisView.findViewById(R.id.relative_center_home);
        mRightRelativeLayout = taxAnalysisView.findViewById(R.id.relative_right_arrow);
        mleftRelativeLayout.setOnClickListener(this);
        mcenterRelativeLayout.setOnClickListener(this);
        mRightRelativeLayout.setOnClickListener(this);
        fab_id.setOnClickListener(this);
        mCallBackListener.setActionBarTitle("Tax Plan");

        value_postion = (User_tax)getArguments().getSerializable("user_tax_position_cash_flow");

        BARENTRY = new ArrayList<>();
        LargeValueFormatter custom = new LargeValueFormatter();
        tf = Typeface.createFromAsset(getContext().getAssets(), "OpenSans-Regular.ttf");
        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(tf);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setGridLineWidth(0.3f);

        //chart background programatically fixing with tab
        ShapeDrawable sd = new ShapeDrawable();
        sd.setShape(new RectShape());
        sd.getPaint().setColor(Color.GRAY);
        sd.getPaint().setStrokeWidth(5f);
        sd.getPaint().setStyle(Paint.Style.STROKE);
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            mainLayout.setBackgroundDrawable(sd);
        } else {
            mainLayout.setBackground(sd);
        }

        YAxis yl = barChart.getAxisLeft();
        yl.setTypeface(tf);
        yl.setDrawLabels(false);
        yl.setDrawAxisLine(false);
        yl.setDrawGridLines(false);
        yl.setGridLineWidth(0.1f);
        yl.setValueFormatter(new ChartValueFormatter());
//        yl.setAxisMinValue(0f); // this replaces setStartAtZero(true)
//        yl.setInverted(true);
        yl.setStartAtZero(false);
//        yl.setValueFormatter(custom);//this value is to display in thousands in to "k"
        YAxis yr = barChart.getAxisRight();
        yr.setTypeface(tf);
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);
        yr.setStartAtZero(false);
        yr.setValueFormatter(new ChartValueFormatter());


        Legend l = barChart.getLegend();
        l.setEnabled(false);
        l.setTextSize(8 * getResources().getDisplayMetrics().density);
        l.setFormSize(15f);
        l.setWordWrapEnabled(true);
        l.setXEntrySpace(15f);

         if(value_postion!= null){
             adddvalueinString(value_postion);
        }

        return taxAnalysisView;
    }

    private void forLoopComparisionStarts(User_tax value) {


        for(int i=0; i< value.getSections().size(); i++){
            if(tra.equalsIgnoreCase(value.getSections().get(i).getName().toString())){
                tra_cash_flow_chart =  String.valueOf(Math.round(Float.parseFloat(value.getSections().get(i).getVal().toString())));
                Log.i("Tax Analysis", "Tar value from cash Flow "+ tra_cash_flow_chart);
            }
            if(eightyC.equalsIgnoreCase(value.getSections().get(i).getName().toString())){
                eighty_C_Cash_flow =  String.valueOf(Math.round(Float.parseFloat(value.getSections().get(i).getVal().toString())));
                Log.i("Tax Analysis", "Tar value from cash Flow eighty_C_Cash_flow"+ eighty_C_Cash_flow);
            }
            if(eightyGG.equalsIgnoreCase(value.getSections().get(i).getName().toString())){
                eighty_GG_Cash_flow =  String.valueOf(Math.round(Float.parseFloat(value.getSections().get(i).getVal().toString())));
                Log.i("Tax Analysis", "Tar value from cash Flow eightyGG"+ eighty_GG_Cash_flow);
            }
        }

    }

//    private void callTaxAnalysisService() {
//        WebServiceCalls webServiceObj;
//        webServiceObj = ServiceGenerator.createService(WebServiceCalls.class);
//        Call<GetTaxPlanModels> call = webServiceObj.callinsurance_tax_Service(UtileKit.getPersistedPurplePathPref("user_id"));
//        call.enqueue(new Callback<GetTaxPlanModels>() {
//            @Override
//            public void onResponse(Call<GetTaxPlanModels> call, Response<GetTaxPlanModels> response) {
//                try {
//                    Log.i("Tax Analysis", "Tax Analysis user id" + UtileKit.getPersistedPurplePathPref("user_id"));
//                    UtileKit.dismisssSpinnerDialog();
//
//                    taxAnalysisChartModel = response.body();
//
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetTaxPlanModels> call, Throwable t) {
//                //Log.e("CallBack", " failure is " + t);
//                UtileKit.dismisssSpinnerDialog();
//                UtileKit.alertRetrofitExceptionDialog( mContext,t);
//            }
//        });
//
//    }

    private void adddvalueinString(User_tax taxAnalysisChartModel) {
            if (null != taxAnalysisChartModel.getSections().get(0)) {
                for(int i =0; i<taxAnalysisChartModel.getSections().size(); i++) {
                    if (eightyC.equalsIgnoreCase(taxAnalysisChartModel.getSections().get(i).getName())) {
                        total_limit_eightyC = String.valueOf(Math.round(Float.parseFloat(taxAnalysisChartModel.getSections().get(i).getVal())));
                    }
                    if (eightyCCD.equalsIgnoreCase(taxAnalysisChartModel.getSections().get(i).getName())) {
                        total_limit_eightyCCD = String.valueOf(Math.round(Float.parseFloat(taxAnalysisChartModel.getSections().get(i).getVal())));
                    }
                    if (eightyCCG.equalsIgnoreCase(taxAnalysisChartModel.getSections().get(i).getName())) {
                        total_limit_eightyCCG = String.valueOf(Math.round(Float.parseFloat(taxAnalysisChartModel.getSections().get(i).getVal())));
                    }
                    if (eightyGG.equalsIgnoreCase(taxAnalysisChartModel.getSections().get(i).getName())) {
                        total_limit_eightyGG = String.valueOf(Math.round(Float.parseFloat(taxAnalysisChartModel.getSections().get(i).getVal())));
                    }
                    if (eightyD.equalsIgnoreCase(taxAnalysisChartModel.getSections().get(i).getName())) {
                        total_limit_eightyD = String.valueOf(Math.round(Float.parseFloat(taxAnalysisChartModel.getSections().get(i).getVal())));
                    }
                    if (tra.equalsIgnoreCase(taxAnalysisChartModel.getSections().get(i).getName())) {
                        total_limit_eightyTRA = String.valueOf(Math.round(Float.parseFloat(taxAnalysisChartModel.getSections().get(i).getVal())));
                    }
                }
                }

                setCombinedChart();// Chart Creates here
               }


    private void setCombinedChart() {


        if(value_postion!= null) {
        forLoopComparisionStarts(value_postion); // comparision and add value in String
        }
        CombinedData data = new CombinedData(getXAxisValues());
//        data.setData(generateLineData());
        data.setData(generateBarData());
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setDrawValueAboveBar(false);
        barChart.setTouchEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setData(data);
        barChart.setDescription("");
        barChart.setExtraBottomOffset(5f);
        barChart.invalidate();

    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Entitlement");
        labels.add("Can avail");
        labels.add("To plan");

        return labels;
    }

    private BarData generateBarData() {
        float[] xDatafirstBar=null;
        int [] colour=new int[11];
        xDatafirstBar =  new float[5];
        String[] xData=null;
        xData= new String[5];

        int [] xDatacolour=new int[5];

        if(total_limit_eightyGG!=null){
            xDatafirstBar[0] = Float.parseFloat(String.valueOf(Math.abs(Integer.parseInt(total_limit_eightyGG))));
            colour[0]=   ColorTemplate.rgb("#53adfc");
            xData[0]= "80GG";
            xDatacolour[0] = ColorTemplate.rgb("#53adfc");
        }

        if(total_limit_eightyCCG!=null){
            xDatafirstBar[1] = Float.parseFloat(String.valueOf(Math.abs(Integer.parseInt(total_limit_eightyCCG))));
            colour[1]=   ColorTemplate.rgb("#53fcf3");
            xData[1]= "80CCG  ";
            xDatacolour[1] = ColorTemplate.rgb("#53fcf3");
        }

        if(total_limit_eightyCCD!=null){
            xDatafirstBar[2] = Float.parseFloat(String.valueOf(Math.abs(Integer.parseInt(total_limit_eightyCCD))));
            colour[2]=   ColorTemplate.rgb("#fc8553");
            xData[2]= "80CCD ";
            xDatacolour[2] = ColorTemplate.rgb("#fc8553");
        }
        if(total_limit_eightyC!=null){
            xDatafirstBar[3] = Float.parseFloat(String.valueOf(Math.abs(Integer.parseInt(total_limit_eightyC))));
            colour[3]=   ColorTemplate.rgb("#8BC34A");
            xData[3]= "80C ";
            xDatacolour[3] = ColorTemplate.rgb("#8BC34A");
        }

        if(total_limit_eightyTRA!=null){
            xDatafirstBar[4] = Float.parseFloat(String.valueOf(Math.abs(Integer.parseInt(total_limit_eightyTRA))));
            colour[4]=   ColorTemplate.rgb("#FFC107");
            xData[4]= "TRA ";
            xDatacolour[4] = ColorTemplate.rgb("#FFC107");
        }
//-------------------------------
        float[] xDataSecondBar=null;
        xDataSecondBar =  new float[3];


        if(UtileKit.validateObjectValues(eighty_GG_Cash_flow)){
            xDataSecondBar[0] = Float.parseFloat(eighty_GG_Cash_flow);
            colour[5]=   ColorTemplate.rgb("#53adfc");
        }
        if(UtileKit.validateObjectValues((eighty_C_Cash_flow))){
            xDataSecondBar[1] = Float.parseFloat(eighty_C_Cash_flow);
            colour[6]=   ColorTemplate.rgb("#8BC34A");
        }


        if(UtileKit.validateObjectValues((tra_cash_flow_chart))){
            xDataSecondBar[2] = Float.parseFloat((tra_cash_flow_chart));
            colour[7]=   ColorTemplate.rgb("#FFC107");
        }
//--------------------------------
        float[] xDatathirdBar=null;
        xDatathirdBar =  new float[3];

        float number_eighty_GG_Cash_flow = Float.parseFloat(eighty_GG_Cash_flow);
        float number_total_limit_eightyGG = Float.parseFloat(total_limit_eightyGG);
        int subtotal_limit_eightyGG =  (int)Math.abs(number_eighty_GG_Cash_flow) - (int)Math.abs(number_total_limit_eightyGG);
        if(UtileKit.validateObjectValues(subtotal_limit_eightyGG)){
            xDatathirdBar[0] = Float.parseFloat(String.valueOf(Math.abs(subtotal_limit_eightyGG)));
            colour[8]=   ColorTemplate.rgb("#53adfc");
        }

        float number_eighty_C_Cash_flow = Float.parseFloat(eighty_C_Cash_flow);
        float number_total_limit_eightyC = Float.parseFloat(total_limit_eightyC);
        int subtotal_limit_eightyC = (int)Math.abs(number_eighty_C_Cash_flow) - (int)Math.abs(number_total_limit_eightyC);
        if(UtileKit.validateObjectValues(subtotal_limit_eightyC)){
            xDatathirdBar[1] = Float.parseFloat(String.valueOf(Math.abs(subtotal_limit_eightyC)));
            colour[9]=   ColorTemplate.rgb("#8BC34A");
        }

        float number = Float.parseFloat(tra_cash_flow_chart);
        float number_total_limit_eightyTRA = Float.parseFloat(total_limit_eightyTRA);
        int subtotal_limit_eightyTRA = (int)Math.abs(number) - (int)Math.abs(number_total_limit_eightyTRA);

        if(UtileKit.validateObjectValues(subtotal_limit_eightyTRA)){
            xDatathirdBar[2] = Float.parseFloat(String.valueOf(Math.abs(subtotal_limit_eightyTRA)));
            colour[10]=   ColorTemplate.rgb("#FFC107");
        }

        BarData d = new BarData();
        BARENTRY.add(new BarEntry(xDatafirstBar, 0));
        BARENTRY.add(new BarEntry(xDataSecondBar, 1));
        BARENTRY.add(new BarEntry(xDatathirdBar, 2));
        // d.setValueFormatter(new ChartValueFormatter());
        BarDataSet set = new BarDataSet(BARENTRY, "");
        set.setColors(colour);
        set.setBarSpacePercent(20f);
        set.setValueFormatter(new ChartValueFormatter());
        d.addDataSet(set);
        setDataForCheckbox(xData,xDatacolour);// Used this method for create check box
        return d;
    }


    private void setDataForCheckbox(String[] xDataCheckbox, int [] colour) {
        try {
            Log.i("Tax Analysis", " Tax Analysis setDataForCheckbox is " + xDataCheckbox.length );


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
                parms_legen_layout.setMargins(20, 10, 20, 10);
                LinearLayout legend_layout = new LinearLayout(mContext);
                legend_layout.setLayoutParams(parms_legen_layout);
                legend_layout.setOrientation(LinearLayout.HORIZONTAL);
                legend_layout.setBackgroundColor(colour[i]);
                Log.i("Tax Analysis", " Tax Analysis setDataForCheckbox colour " + colour );
                left_layout.addView(legend_layout);

                TextView txt_unit = new TextView(mContext);
                LinearLayout.LayoutParams txtLeft_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                txt_unit.setLayoutParams(txtLeft_params);
                txt_unit.setGravity(Gravity.LEFT|Gravity.CENTER);
                txt_unit.setText( xDataCheckbox[i]);
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
                Log.i("Tax Analysis", " Tax Analysis setDataForCheckbox colour parms_right_layout" + colour );
                right_layout.addView(right_legend_layout);

                TextView right_txt_unit = new TextView(mContext);
                LinearLayout.LayoutParams txtRightParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                right_txt_unit.setLayoutParams(txtRightParams);
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
//                AssetsAnalysisFragment fragment =
                addFragmenttoStack(new AssetsAnalysisFragment());
//                fragmentTransaction.replace(R.id.fragment_container, fragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commitAllowingStateLoss();

            }
            break;
            case R.id.fab_id:
            {
//                addFragmenttoStack(new InsuranceDetailsFragment());
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

               // TaxPlanDetails taxCashFlowChart = new TaxPlanDetails();
               // showFragment(taxCashFlowChart);


                TaxPlanDetailViewPager taxPlanDetailViewPager=new TaxPlanDetailViewPager();
                showFragment(taxPlanDetailViewPager);

                break;
            case R.id.menu_summary:
                TaxPlanSummary taxPlanSummary = new TaxPlanSummary();
                showFragment(taxPlanSummary);
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






    private class CustomFormatter implements com.github.mikephil.charting.formatter.ValueFormatter, YAxisValueFormatter {

        private DecimalFormat mFormat;

        public CustomFormatter() {
            mFormat = new DecimalFormat("###");
        }

        // data
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(Math.abs(value)) ;
        }

        // YAxis
        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            return mFormat.format(Math.abs(value)) ;
        }


    }

}